package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.persistence.support.SeqRepository;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.response.RowShowtimeResponse;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.ShowtimeService;
import com.it.doubledi.cinemamanager.domain.*;
import com.it.doubledi.cinemamanager.domain.command.ShowtimeCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.FilmRepository;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.domain.repository.ShowtimeRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceByTimeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceByTimeEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.FilmEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceByTimeEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Showtime create(ShowtimeCreateRequest request) {
        ShowtimeCreateCmd cmd = this.autoMapper.from(request);

        RoomEntity rooMEntity = this.roomEntityRepository.findById(cmd.getRoomId()).orElseThrow(() -> new ResponseException(NotFoundError.ROOM_NOT_FOUND));
        FilmEntity filmEntity = this.filmEntityRepository.findFilmById(cmd.getFilmId()).orElseThrow(() -> new ResponseException(NotFoundError.FILM_NOT_FOUND));

        Showtime showtime = new Showtime(cmd);
        if (Objects.nonNull(filmEntity.getDuration())) {
            showtime.calEndAt(filmEntity.getDuration());
        } else {
            throw new ResponseException(BadRequestError.DURATION_OF_FILM_REQUIRED);
        }

        return this.showtimeRepository.save(showtime);
    }

    @Override
    public Showtime getById(String id) {
        return showtimeRepository.getById(id);
    }

    @Override
    public PageDTO<Showtime> search(ShowtimeSearchRequest request) {
        return null;
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
}
