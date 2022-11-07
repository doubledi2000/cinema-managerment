package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.persistence.support.SeqRepository;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.response.RowShowtimeResponse;
import com.it.doubledi.cinemamanager.application.dto.response.ShowtimeResponse;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.ShowtimeService;
import com.it.doubledi.cinemamanager.domain.*;
import com.it.doubledi.cinemamanager.domain.command.FilmScheduleCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ShowtimeCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.FilmRepository;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.domain.repository.ShowtimeRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceByTimeEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ShowtimeEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.*;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {
    private final RoomRepository roomRepository;
    private final FilmRepository filmRepository;
    private final AutoMapper autoMapper;
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
        return showtimeRepository.getById(id);
    }

    @Override
    public List<ShowtimeResponse> search(ShowtimeSearchRequest request) {
        if(Objects.isNull(request.getPremierDate())) {
            request.setPremierDate(LocalDate.now());
        }
        List<String> filmIds = null;
        if(!CollectionUtils.isEmpty(request.getFilmIds())) {
            filmIds = request.getFilmIds();
        }

        if(!CollectionUtils.isEmpty(request.getTypeOfFilmIds())) {
            List<FilmTypeEntity> filmTypeEntities = this.filmTypeEntityRepository.findByTypeIds(request.getTypeOfFilmIds());
            if(!CollectionUtils.isEmpty(filmTypeEntities)) {
                if(!CollectionUtils.isEmpty(filmIds)) {
                    filmIds.retainAll(filmTypeEntities.stream().map(FilmTypeEntity::getFilmId).collect(Collectors.toList()));
                }else {
                    filmIds = filmTypeEntities.stream().map(FilmTypeEntity::getFilmId).collect(Collectors.toList());
                }
            }
        }

        List<ShowtimeEntity> showtimeEntities = this.showtimeEntityRepository.findShowtimeByParams(filmIds, request.getPremierDate(), request.getStartTime());
        List<String> filmInListIds = showtimeEntities.stream().map(ShowtimeEntity::getFilmId).collect(Collectors.toList());
        List<FilmEntity> filmEntities = this.filmEntityRepository.findByIds(filmInListIds);
        List<Film> films = this.filmEntityMapper.toDomain(filmEntities);
        List<ShowtimeResponse> showtimeResponses= new ArrayList<>();
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
        if (!Objects.equals(showtime.getStatus(), ShowtimeStatus.WAIT_GEN_TICKET)) {
            throw new ResponseException(BadRequestError.FILM_ALREADY_GEN_TICKET);
        }
        Optional<PriceByTimeEntity> priceByTimeEntityOptional = this.priceByTimeEntityRepository.getPriceByTimeBySpecificTime(showtime.getRoom().getLocationId(), showtime.getPremiereDate().getDayOfWeek().getValue(), showtime.getStartAt());
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
            if(endTimeTmp >= showtime.getStartAt()) {
                throw new ResponseException(BadRequestError.FILM_SCHEDULED_CONFLICT);
            }
            endTimeTmp = showtime.getEndAt();
        }
        this.showtimeRepository.saveALl(showtimes);
        return showtimes;
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
                        .price(price)
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
}
