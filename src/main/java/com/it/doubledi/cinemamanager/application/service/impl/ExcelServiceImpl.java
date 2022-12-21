package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.util.ExcelUtils;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateInBatchRequest;
import com.it.doubledi.cinemamanager.application.service.ExcelService;
import com.it.doubledi.cinemamanager.domain.Film;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.Showtime;
import com.it.doubledi.cinemamanager.domain.repository.ShowtimeRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ShowtimeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.LocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.FilmEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ShowtimeEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoomStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final FilmEntityRepository filmEntityRepository;
    private final FilmEntityMapper filmEntityMapper;
    private final RoomEntityRepository roomEntityRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final ShowtimeEntityRepository showtimeEntityRepository;
    private final ShowtimeRepository showtimeRepository;
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
            throw new ResponseException(BadRequestError.TEMPLATE_NOT_HAVE_FILM_SHEET);
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
            throw new ResponseException(BadRequestError.TEMPLATE_NOT_HAVE_ROOM_SHEET);
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

    @Override
    public void uploadShowtime(MultipartFile file) {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            throw new ResponseException(BadRequestError.READ_FILE_FAIL);
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row timeRow = sheet.getRow(0);
        Cell cell = timeRow.getCell(1);
        LocalDate premiereDate = Instant.ofEpochMilli(cell.getDateCellValue().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        List<ShowtimeCreateInBatchRequest> showtimeCreateInBatchRequests = this.getListShowtime(sheet);

        List<String> roomIds = showtimeCreateInBatchRequests.stream().map(ShowtimeCreateInBatchRequest::getRoomId).distinct().collect(Collectors.toList());
        List<String> filmIds = showtimeCreateInBatchRequests.stream().map(ShowtimeCreateInBatchRequest::getFilmId).distinct().collect(Collectors.toList());

        List<RoomEntity> roomEntities = this.roomEntityRepository.findByIds(roomIds);
        List<FilmEntity> filmEntities = this.filmEntityRepository.findByIds(filmIds);
        if (roomIds.size() > roomEntities.size()) {
            roomIds.removeAll(roomEntities.stream().map(RoomEntity::getId).collect(Collectors.toList()));
            throw new ResponseException("room with id " + roomIds + " not found ", NotFoundError.ROOM_NOT_FOUND);
        }

        if (filmIds.size() > filmEntities.size()) {
            filmIds.removeAll(filmEntities.stream().map(FilmEntity::getId).collect(Collectors.toList()));
            throw new ResponseException("Film with id " + filmIds + " not found ", NotFoundError.FILM_NOT_FOUND);
        }

        List<ShowtimeEntity> showtimeEntities = this.showtimeEntityRepository.findAllByRoomIds(roomIds, premiereDate);
        if (!CollectionUtils.isEmpty(showtimeEntities)) {
            List<String> roomIdsConflict = showtimeEntities.stream().map(ShowtimeEntity::getRoomId).distinct().collect(Collectors.toList());
            throw new ResponseException("Create showtime conflict with room ids " + roomIdsConflict, BadRequestError.FILM_SCHEDULED_CONFLICT);
        }

        List<Showtime> showtimes = new ArrayList<>();
        List<Film> films = this.filmEntityMapper.toDomain(filmEntities);
        List<Room> rooms = this.roomEntityMapper.toDomain(roomEntities);
        showtimeCreateInBatchRequests.forEach(s -> {
            Optional<Film> filmOptional = films.stream().filter(f -> Objects.equals(f.getId(), s.getFilmId())).findFirst();
            Film film = null;
            if (filmOptional.isPresent()) {
                film = filmOptional.get();
            }
            Optional<Room> roomOptional = rooms.stream().filter(r -> Objects.equals(r.getId(), s.getRoomId())).findFirst();
            Room room = null;
            if (roomOptional.isPresent()) {
                room = roomOptional.get();
            }
            if (Objects.nonNull(film) && Objects.nonNull(room)) {
                Showtime showtime = Showtime.builder()
                        .id(IdUtils.nextId())
                        .premiereDate(premiereDate)
                        .roomId(room.getId())
                        .filmId(film.getId())
                        .status(ShowtimeStatus.WAIT_GEN_TICKET)
                        .startAt(s.getStartAt())
                        .endAt(s.getStartAt() + film.getDuration())
                        .deleted(Boolean.FALSE)
                        .autoGenerateTicket(Boolean.FALSE)
                        .locationId(room.getLocationId())
                        .build();
                showtimes.add(showtime);
            }
        });
        Map<String, List<Showtime>> map = showtimes.stream()
                .collect(Collectors.groupingBy(Showtime::getRoomId));

        List<String> roomIdsConflict = new ArrayList<>();
        for (String key : map.keySet()) {
            List<Showtime> showtimesTmp = map.get(key).stream().sorted(Comparator.comparing(Showtime::getStartAt)).collect(Collectors.toList());
            int endAtTmp = showtimesTmp.get(0).getEndAt();
            for (int i = 1; i < showtimesTmp.size(); i++) {
                Showtime showtimeTmp = showtimesTmp.get(i);
                if (showtimeTmp.getStartAt() <= endAtTmp) {
                    roomIdsConflict.add(showtimeTmp.getRoomId());
                }
                endAtTmp = showtimeTmp.getEndAt();
            }
        }
        if (!CollectionUtils.isEmpty(roomIdsConflict)) {
            throw new ResponseException("Scheduled conflict with room id " + roomIdsConflict, BadRequestError.FILM_SCHEDULED_CONFLICT);
        }

        this.showtimeRepository.saveALl(showtimes);

    }

    private List<ShowtimeCreateInBatchRequest> getListShowtime(Sheet sheet) {
        int rowIndex = 0;
        List<ShowtimeCreateInBatchRequest> showtimeCreateInBatchRequests = new ArrayList<>();
        for (Row row : sheet) {
            if (rowIndex < 2) {
                rowIndex++;
                continue;
            }

            String roomId = null;
            String filmId = null;
            int startAt = 0;
            for (Cell cell : row) {
                String value = ExcelUtils.readCellContent(cell);
                try {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            break;
                        case 1:
                            roomId = value;
                            break;
                        case 2:
                            filmId = value;
                            break;
                        case 3:
                            startAt = (int) Float.parseFloat(value);
                            break;
                    }
                } catch (Exception e) {
                    throw new ResponseException(BadRequestError.INPUT_INVALID);
                }
            }
            showtimeCreateInBatchRequests.add(ShowtimeCreateInBatchRequest.builder()
                    .roomId(roomId)
                    .filmId(filmId)
                    .startAt(startAt)
                    .build());

        }
        return showtimeCreateInBatchRequests;
    }
}
