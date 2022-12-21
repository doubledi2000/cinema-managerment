package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.persistence.support.SeqRepository;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeConfigSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.response.RowShowtimeResponse;
import com.it.doubledi.cinemamanager.application.dto.response.ShowtimeResponse;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.ShowtimeService;
import com.it.doubledi.cinemamanager.domain.*;
import com.it.doubledi.cinemamanager.domain.command.FilmScheduleCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ShowtimeCreateCmd;
import com.it.doubledi.cinemamanager.domain.query.ShowtimeConfigSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.FilmRepository;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.domain.repository.ShowtimeRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.*;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ShowtimeServiceImpl implements ShowtimeService {
    private final RoomRepository roomRepository;
    private final FilmRepository filmRepository;
    private final AutoMapper autoMapper;
    private final AutoMapperQuery autoMapperQuery;
    private final RoomEntityRepository roomEntityRepository;
    private final FilmEntityRepository filmEntityRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeqRepository seqRepository;
    private final PriceByTimeEntityRepository priceByTimeEntityRepository;
    private final PriceByTimeEntityMapper priceByTimeEntityMapper;
    private final PriceEntityRepository priceEntityRepository;
    private final PriceEntityMapper priceEntityMapper;
    private final ShowtimeEntityRepository showtimeEntityRepository;
    private final FilmTypeEntityRepository filmTypeEntityRepository;
    private final FilmEntityMapper filmEntityMapper;
    private final ShowtimeEntityMapper showtimeEntityMapper;
    private final RoomEntityMapper roomEntityMapper;
    private final LocationEntityMapper locationEntityMapper;
    private final LocationEntityRepository locationEntityRepository;

    @Override
    public Showtime create(ShowtimeCreateRequest request) {
//        ShowtimeCreateCmd cmd = this.autoMapper.from(request);
//
//        RoomEntity rooMEntity = this.roomEntityRepository.findById(cmd.getRoomId()).orElseThrow(() -> new ResponseException(NotFoundError.ROOM_NOT_FOUND));
//        FilmEntity filmEntity = this.filmEntityRepository.findFilmById(cmd.getFilmId()).orElseThrow(() -> new ResponseException(NotFoundError.FILM_NOT_FOUND));
//
//        Showtime showtime = new Showtime(cmd);
//        if (Objects.nonNull(filmEntity.getDuration())) {
//            showtime.calEndAt(filmEntity.getDuration());
//        } else {
//            throw new ResponseException(BadRequestError.DURATION_OF_FILM_REQUIRED);
//        }
//
//        return this.showtimeRepository.save(showtime);
        return null;
    }

    @Override
    public Showtime getById(String id) {
        Showtime showtime = showtimeRepository.getById(id);
        SecurityUtils.checkPermissionOfLocation(showtime.getLocationId());
        return showtime;
    }

    @Override
    public List<ShowtimeResponse> search(ShowtimeSearchRequest request) {
        UserAuthentication userAuthentication = SecurityUtils.authentication();
        List<String> locationIds = null;
        if (userAuthentication.isRoot() || UserLevel.CENTER.equals(userAuthentication.getUserLevel())) {
            log.info("User have all location");
        } else if (!CollectionUtils.isEmpty(userAuthentication.getLocationIds())) {
            locationIds = userAuthentication.getLocationIds();
        } else {
            log.info("User have no location");
            return new ArrayList<>();
        }

        if (Objects.isNull(request.getPremierDate())) {
            request.setPremierDate(LocalDate.now());
        }
        List<String> filmIds = null;
        if (!CollectionUtils.isEmpty(request.getFilmIds())) {
            filmIds = request.getFilmIds();
        }

        if (!CollectionUtils.isEmpty(request.getTypeOfFilmIds())) {
            List<FilmTypeEntity> filmTypeEntities = this.filmTypeEntityRepository.findByTypeIds(request.getTypeOfFilmIds());
            if (!CollectionUtils.isEmpty(filmTypeEntities)) {
                if (!CollectionUtils.isEmpty(filmIds)) {
                    filmIds.retainAll(filmTypeEntities.stream().map(FilmTypeEntity::getFilmId).collect(Collectors.toList()));
                } else {
                    filmIds = filmTypeEntities.stream().map(FilmTypeEntity::getFilmId).collect(Collectors.toList());
                }
            }
        }

        List<ShowtimeEntity> showtimeEntities = this.showtimeEntityRepository.findShowtimeByParams(filmIds, request.getPremierDate(), locationIds, request.getStartTime());
        List<String> filmInListIds = showtimeEntities.stream().map(ShowtimeEntity::getFilmId).collect(Collectors.toList());
        List<FilmEntity> filmEntities = this.filmEntityRepository.findByIds(filmInListIds);
        List<Film> films = this.filmEntityMapper.toDomain(filmEntities);
        List<ShowtimeResponse> showtimeResponses = new ArrayList<>();
        List<Showtime> showtimes = this.showtimeEntityMapper.toDomain(showtimeEntities);
        for (Film film : films) {
            List<Showtime> showtimeTmps = showtimes.stream()
                    .filter(s -> Objects.equals(s.getFilmId(), film.getId()))
                    .sorted(Comparator.comparing(Showtime::getStartAt))
                    .collect(Collectors.toList());
            ShowtimeResponse showtimeResponse = ShowtimeResponse.builder()
                    .film(film)
                    .filmId(film.getId())
                    .premiereDate(request.getPremierDate())
                    .details(showtimeTmps)
                    .build();
            showtimeResponses.add(showtimeResponse);
        }
        return showtimeResponses;
    }

    @Override
    public PageDTO<Showtime> autoComplete(ShowtimeSearchRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void generateTicket(String id) {
        Showtime showtime = this.showtimeRepository.getById(id);
        SecurityUtils.checkPermissionOfLocation(showtime.getLocationId());
        if (!Objects.equals(showtime.getStatus(), ShowtimeStatus.WAIT_GEN_TICKET)) {
            throw new ResponseException(BadRequestError.FILM_ALREADY_GEN_TICKET);
        }
        Optional<PriceByTimeEntity> priceByTimeEntityOptional = this.priceByTimeEntityRepository
                .getPriceByTimeBySpecificTime(showtime.getRoom().getLocationId(), showtime.getPremiereDate().getDayOfWeek().getValue() - 1, showtime.getStartAt());
        PriceByTime priceByTime = null;
        if (priceByTimeEntityOptional.isPresent()) {
            priceByTime = this.priceByTimeEntityMapper.toDomain(priceByTimeEntityOptional.get());
        }
        if (Objects.nonNull(priceByTime)) {
            List<PriceEntity> priceEntities = this.priceEntityRepository.getAllByPriceByTimeIds(List.of(priceByTime.getId()));
            if (!CollectionUtils.isEmpty(priceEntities)) {
                List<Price> prices = this.priceEntityMapper.toDomain(priceEntities);
                priceByTime.enrichPrices(prices);
            }
        }
        showtime.enrichRoom(this.roomRepository.getById(showtime.getRoomId()));
        List<RowShowtimeResponse> rows = generateTickets(showtime.getId(), showtime.getRoom(), showtime.getFilm(), priceByTime);
        showtime.enrichRowShowtimeResponse(rows);
        showtime.genTicket();
        this.showtimeRepository.save(showtime);
    }

    @Override
    public List<Showtime> createMulti(ShowtimeCreateRequest request) {
        ShowtimeCreateCmd cmd = this.autoMapper.from(request);
        if (CollectionUtils.isEmpty(cmd.getFilms())) {
            return new ArrayList<>();
        }
        RoomEntity roomEntity = this.roomEntityRepository.findById(cmd.getRoomId()).orElseThrow(() -> new ResponseException(NotFoundError.ROOM_NOT_FOUND));
        SecurityUtils.checkPermissionOfLocation(roomEntity.getLocationId());
        List<ShowtimeEntity> showtimeEntities = this.showtimeEntityRepository.findByRoomIdAndPremiereDate(cmd.getRoomId(), cmd.getPremierDate());
        if (!CollectionUtils.isEmpty(showtimeEntities)) {
            throw new ResponseException(BadRequestError.ROOM_ALREADY_SCHEDULED);
        }
        List<String> filmIds = cmd.getFilms().stream().map(FilmScheduleCreateCmd::getFilmId).collect(Collectors.toList());
        List<FilmEntity> filmEntities = this.filmEntityRepository.findByIds(filmIds);
        if (CollectionUtils.isEmpty(filmEntities)) {
            throw new ResponseException(BadRequestError.NO_FILM_IN_SHOWTIME_CREATE_LIST);
        }
        List<Showtime> showtimes = this.getShowtimeFromCmd(cmd, filmEntities).stream().sorted(Comparator.comparing(Showtime::getStartAt)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(showtimes)) {
            throw new ResponseException(BadRequestError.NO_FILM_IN_SHOWTIME_CREATE_LIST);
        }
        Integer endTimeTmp = -1;
        for (Showtime showtime : showtimes) {
            if (endTimeTmp >= showtime.getStartAt()) {
                throw new ResponseException(BadRequestError.FILM_SCHEDULED_CONFLICT);
            }
            endTimeTmp = showtime.getEndAt();
        }
        this.showtimeRepository.saveALl(showtimes);
        return showtimes;
    }

    @Override
    public PageDTO<Showtime> getShowtimeConfig(ShowtimeConfigSearchRequest request) {
        ShowtimeConfigSearchQuery query = this.autoMapperQuery.toQuery(request);
        Long count = this.showtimeEntityRepository.count(query);
        if (count == 0) {
            return PageDTO.empty();
        }
        List<ShowtimeEntity> showtimeEntities = this.showtimeEntityRepository.search(query);
        List<Showtime> showtimes = this.showtimeEntityMapper.toDomain(showtimeEntities);
        this.enrichShowtime(showtimes);
        return PageDTO.of(showtimes, query.getPageIndex(), query.getPageSize(), count);
    }

    private void enrichShowtime(List<Showtime> showtimes) {
        List<String> roomIds = showtimes.stream().map(Showtime::getRoomId).distinct().collect(Collectors.toList());
        List<String> filmIds = showtimes.stream().map(Showtime::getFilmId).distinct().collect(Collectors.toList());
        List<String> locationIds = showtimes.stream().map(Showtime::getLocationId).distinct().collect(Collectors.toList());
        List<RoomEntity> roomEntities = this.roomEntityRepository.findByIds(roomIds);
        List<Room> rooms = this.roomEntityMapper.toDomain(roomEntities);
        List<Film> films = this.filmEntityMapper.toDomain(this.filmEntityRepository.findByIds(filmIds));
        List<Location> locations = this.locationEntityMapper.toDomain(this.locationEntityRepository.findByIds(locationIds));
        showtimes.forEach(s -> {
            Optional<Room> roomOptional = rooms.stream().filter(r -> Objects.equals(r.getId(), s.getRoomId())).findFirst();
            roomOptional.ifPresent(s::enrichRoom);
            Optional<Film> filmOptional = films.stream().filter(f -> Objects.equals(f.getId(), s.getFilmId())).findFirst();
            filmOptional.ifPresent(s::enrichFilm);
            Optional<Location> locationOptional = locations.stream().filter(l -> Objects.equals(l.getId(), s.getLocationId())).findFirst();
            locationOptional.ifPresent(s::enrichLocation);
        });
    }

    private List<RowShowtimeResponse> generateTickets(String showtimeId, Room room, Film film, PriceByTime priceByTime) {
        List<RowShowtimeResponse> rowShowtimeResponses = new ArrayList<>();
        if (CollectionUtils.isEmpty(room.getRows())) {
            return new ArrayList<>();
        }
        for (Row row : room.getRows()) {
            List<Ticket> tickets = new ArrayList<>();
            if (CollectionUtils.isEmpty(row.getChairs())) {
                continue;
            }
            for (Chair chair : row.getChairs()) {
                Float price = Constant.TICKET_DEFAULT_PRICE;
                if (Objects.nonNull(priceByTime) && !CollectionUtils.isEmpty(priceByTime.getPrices())) {
                    Optional<Price> priceTmp = priceByTime.getPrices().stream().filter(p -> Objects.equals(p.getChairType(), chair.getChairType())).findFirst();
                    if (priceTmp.isPresent() && Objects.nonNull(priceTmp.get().getPrice())) {
                        price = priceTmp.get().getPrice();
                    }
                }
                Ticket ticket = Ticket.builder()
                        .id(IdUtils.nextId())
                        .code(seqRepository.generateChairCode())
                        .name(row.getName() + chair.getSerialOfChair())
                        .chairId(chair.getId())
                        .showtimeId(showtimeId)
                        .price((double) price)
                        .type(chair.getChairType())
                        .filmId(film.getId())
                        .roomId(room.getId())
                        .rowId(row.getId())
                        .rowNumber(row.getRowNumber())
                        .rowName(row.getName())
                        .serialOfChair(chair.getSerialOfChair())
                        .deleted(Boolean.FALSE)
                        .status(Objects.equals(chair.getChairType(), ChairType.BOGY) ? TicketStatus.BOGY : TicketStatus.AVAILABLE)
                        .build();
                tickets.add(ticket);
            }
            RowShowtimeResponse rowShowtimeResponse = RowShowtimeResponse.builder()
                    .rowName(row.getName())
                    .rowNumber(row.getRowNumber())
                    .tickets(tickets)
                    .build();
            rowShowtimeResponses.add(rowShowtimeResponse);
        }
        return rowShowtimeResponses;
    }

    private List<Showtime> getShowtimeFromCmd(ShowtimeCreateCmd cmd, List<FilmEntity> filmEntities) {
        List<Showtime> showtimes = new ArrayList<>();
        List<String> filmIdsNotFound = new ArrayList<>();
        for (FilmScheduleCreateCmd film : cmd.getFilms()) {
            Optional<FilmEntity> filmEntity = filmEntities.stream().filter(f -> Objects.equals(f.getId(), film.getFilmId())).findFirst();
            if (filmEntity.isPresent()) {
                Showtime showtime = new Showtime(cmd, film, filmEntity.get());
                showtimes.add(showtime);
            } else {
                filmIdsNotFound.add(film.getFilmId());
            }
        }
        if (!CollectionUtils.isEmpty(filmIdsNotFound)) {
            throw new ResponseException("Film with ids [" + filmIdsNotFound + "] not found ", NotFoundError.FILM_NOT_FOUND);
        }
        return showtimes;
    }


    //task
    @Scheduled(cron = "0 0 * * * *")
    public void finishShowtime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        int time = localDateTime.getHour() * 60 + localDateTime.getMinute();
        List<ShowtimeEntity> showtimeEntities = this.showtimeEntityRepository.findAllToFinish(localDate, time, List.of(ShowtimeStatus.FINISH, ShowtimeStatus.CANCELED));
        if (CollectionUtils.isEmpty(showtimeEntities)) {
            log.info("No showtime to finish");
            return;
        }

        List<Showtime> showtimes = this.showtimeEntityMapper.toDomain(showtimeEntities);
        this.showtimeRepository.enrichList(showtimes);
        showtimes.forEach(Showtime::finish);
//        this.showtimeRepository.saveALl()
    }

    @Scheduled(cron = "0 0 * * * *")
    public void syncGenerateTicket() {
        LocalDate localDate = LocalDate.now().plusDays(3);
        List<ShowtimeEntity> showtimeEntities = this.showtimeEntityRepository.findAllToGenerateTicket(localDate, ShowtimeStatus.WAIT_GEN_TICKET);
        if (CollectionUtils.isEmpty(showtimeEntities)) {
            log.info("No showtime to generate ticket");
            return;
        }
        showtimeEntities.forEach(s -> {
            this.generateTicket(s.getId());
        });
    }


}
