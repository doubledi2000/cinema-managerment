package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.util.ExcelUtils;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.service.ExcelService;
import com.it.doubledi.cinemamanager.domain.Film;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.LocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.FilmEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoomStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final FilmEntityRepository filmEntityRepository;
    private final FilmEntityMapper filmEntityMapper;
    private final RoomEntityRepository roomEntityRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final String SHOWTIME_TEMPLATE_PATH = "/template/showtime_template.xlsx";
    private final int SHOWTIME_SHEET = 0;
    private final int FILM_SHEET = 1;
    private final int ROOM_SHEET = 2;
    private final int FILM_START_ROW = 1;
    private final int ROOM_START_ROW = 1;
    private final LocationEntityRepository locationEntityRepository;
    private final LocationEntityMapper locationEntityMapper;
    private final String TEMPLATE_SHOWTIME_FILE_NAME = "showtime_template";

    @Override
    public void downloadShowtimeTemplate(HttpServletResponse response) {
        List<FilmEntity> filmEntities = filmEntityRepository.findFilmByStatuses(List.of(FilmStatus.APPROVED));
        List<Film> films = this.filmEntityMapper.toDomain(filmEntities);

        UserAuthentication authentication = SecurityUtils.authentication();
        List<String> locationIds = new ArrayList<>();
        if (authentication.isRoot() || Objects.equals(authentication.getUserLevel(), UserLevel.CENTER)) {
            locationIds = null;
            log.info("Load all branch");
        } else if (CollectionUtils.isEmpty(authentication.getLocationIds())) {
            log.info("User has no location");
            throw new ResponseException(BadRequestError.USER_HAS_NO_LOCATION);
        }
        List<RoomEntity> roomEntities = this.roomEntityRepository.findByLocationIdsAndStatus(locationIds, RoomStatus.ACTIVE);
        List<Room> rooms = this.roomEntityMapper.toDomain(roomEntities);
        List<LocationEntity> locationEntities = this.locationEntityRepository
                .findByIds(rooms.stream().map(Room::getLocationId).distinct().collect(Collectors.toList()));

        List<Location> locations = this.locationEntityMapper.toDomain(locationEntities);
        rooms.forEach(r -> {
            Optional<Location> locationOptional = locations.stream().filter(l -> Objects.equals(l.getId(), r.getLocationId())).findFirst();
            locationOptional.ifPresent(r::enrichLocation);
        });

        XSSFWorkbook workbook;

        try {
            InputStream data = Objects.requireNonNull(getClass().getResource(SHOWTIME_TEMPLATE_PATH)).openStream();
            workbook = new XSSFWorkbook(data);
        } catch (IOException e) {
            throw new ResponseException(BadRequestError.OPEN_SHOWTIME_TEMPLATE_FAIL);
        }
        Sheet filmSheet = workbook.getSheetAt(FILM_SHEET);
        if (Objects.isNull(filmSheet)) {
            throw new ResponseException(BadRequestError.FILM_SHEET_TEMPLATE_REQUIRED);
        }
        CellStyle cellStyle = ExcelUtils.createValueCellStyle(workbook);
        for (int i = 0; i < films.size(); i++) {
            Film film = films.get(i);
            Row currentRow = filmSheet.createRow(FILM_START_ROW + i);
            ExcelUtils.createCell(currentRow, 0, cellStyle, String.valueOf(i + 1));
            ExcelUtils.createCell(currentRow, 1, cellStyle, film.getId());
            ExcelUtils.createCell(currentRow, 2, cellStyle, film.getCode());
            ExcelUtils.createCell(currentRow, 3, cellStyle, film.getName());
            ExcelUtils.createCell(currentRow, 4, cellStyle, film.getDuration() + "'");
            ExcelUtils.createCell(currentRow, 5, cellStyle, film.getDirectors());
        }

        Sheet roomSheet = workbook.getSheetAt(ROOM_SHEET);
        if (Objects.isNull(roomSheet)) {
            throw new ResponseException(BadRequestError.ROOM_SHEET_TEMPLATE_REQUIRED);
        }

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            Row currentRow = roomSheet.createRow(ROOM_START_ROW + i);
            ExcelUtils.createCell(currentRow, 0, cellStyle, String.valueOf(i + 1));
            ExcelUtils.createCell(currentRow, 1, cellStyle, room.getId());
            ExcelUtils.createCell(currentRow, 2, cellStyle, room.getCode());
            ExcelUtils.createCell(currentRow, 3, cellStyle, room.getName());
            if (Objects.nonNull(room.getLocation())) {
                ExcelUtils.createCell(currentRow, 4, cellStyle, room.getLocation().getName());
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            String filename = TEMPLATE_SHOWTIME_FILE_NAME + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx";
            workbook.write(out);
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", " attachment; filename=" + filename);
            IOUtils.copy(new ByteArrayInputStream(out.toByteArray()), response.getOutputStream());
            response.flushBuffer();
            out.close();
            workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseException(BadRequestError.DOWNLOAD_SHOWTIME_TEMPLATE_FAIL);
        }
    }
}
