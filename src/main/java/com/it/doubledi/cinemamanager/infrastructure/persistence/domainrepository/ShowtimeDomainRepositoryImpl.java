package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.application.dto.response.RowShowtimeResponse;
import com.it.doubledi.cinemamanager.domain.Film;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.Showtime;
import com.it.doubledi.cinemamanager.domain.Ticket;
import com.it.doubledi.cinemamanager.domain.repository.ShowtimeRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ShowtimeEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.TicketEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.FilmEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ShowtimeEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.TicketEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShowtimeDomainRepositoryImpl extends AbstractDomainRepository<Showtime, ShowtimeEntity, String> implements ShowtimeRepository {
    private final RoomEntityRepository roomEntityRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final FilmEntityRepository filmEntityRepository;
    private final FilmEntityMapper filmEntityMapper;
    private final TicketEntityRepository ticketEntityRepository;
    private final TicketEntityMapper ticketEntityMapper;

    public ShowtimeDomainRepositoryImpl(ShowtimeEntityRepository showtimeEntityRepository,
                                        ShowtimeEntityMapper showtimeEntityMapper,
                                        RoomEntityRepository roomEntityRepository,
                                        RoomEntityMapper roomEntityMapper,
                                        FilmEntityRepository filmEntityRepository,
                                        FilmEntityMapper filmEntityMapper,
                                        TicketEntityRepository ticketEntityRepository,
                                        TicketEntityMapper ticketEntityMapper) {
        super(showtimeEntityRepository, showtimeEntityMapper);
        this.roomEntityRepository = roomEntityRepository;
        this.roomEntityMapper = roomEntityMapper;
        this.filmEntityRepository = filmEntityRepository;
        this.filmEntityMapper = filmEntityMapper;
        this.ticketEntityRepository = ticketEntityRepository;
        this.ticketEntityMapper = ticketEntityMapper;
    }

    @Override
    public Showtime getById(String id) {
        return this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.SHOWTIME_NOT_FOUND));
    }

    @Override
    @Transactional
    public Showtime save(Showtime domain) {
        if (!CollectionUtils.isEmpty(domain.getRows())) {
            List<Ticket> tickets = new ArrayList<>();
            for (RowShowtimeResponse row : domain.getRows()) {
                if (!CollectionUtils.isEmpty(row.getTickets())) {
                    tickets.addAll(row.getTickets());
                }
            }
            List<TicketEntity> ticketEntities = this.ticketEntityMapper.toEntity(tickets);
            this.ticketEntityRepository.saveAll(ticketEntities);
        }
        return super.save(domain);
    }

    @Override
    public List<Showtime> enrichList(List<Showtime> showtimes) {
        List<String> filmIds = showtimes.stream().map(Showtime::getFilmId).collect(Collectors.toList());
        List<String> roomIds = showtimes.stream().map(Showtime::getRoomId).collect(Collectors.toList());
        List<FilmEntity> filmEntities = this.filmEntityRepository.findByIds(filmIds);
        List<Film> films = this.filmEntityMapper.toDomain(filmEntities);
        List<RoomEntity> roomEntities = this.roomEntityRepository.findByIds(roomIds);
        List<Room> rooms = this.roomEntityMapper.toDomain(roomEntities);
        List<TicketEntity> ticketEntities = this.ticketEntityRepository.findAllByShowtimeIds(showtimes.stream().map(Showtime::getId).collect(Collectors.toList()));
        List<Ticket> tickets = this.ticketEntityMapper.toDomain(ticketEntities);

        for (Showtime showtime : showtimes) {
            films.stream().filter(f -> Objects.equals(f.getId(), showtime.getFilmId())).findFirst().ifPresent(showtime::enrichFilm);
            rooms.stream().filter(r -> Objects.equals(r.getId(), showtime.getRoomId())).findFirst().ifPresent(showtime::enrichRoom);
            List<RowShowtimeResponse> rows = new ArrayList<>();
            if (Objects.equals(showtime.getStatus(), ShowtimeStatus.ON_SALE) || Objects.equals(showtime.getStatus(), ShowtimeStatus.WAIT_ON_SALE)) {
                for (int i = 0; i <= showtime.getRoom().getMaxRow(); i++) {
                    int finalI = i;
                    List<Ticket> ticketTmp = tickets.stream()
                            .filter(t -> Objects.equals(t.getRowNumber(), finalI))
                            .sorted(Comparator.comparing(Ticket::getSerialOfChair))
                            .collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(ticketTmp)) {
                        RowShowtimeResponse rowShowtimeResponseTmp = RowShowtimeResponse.builder()
                                .rowNumber(ticketTmp.get(0).getRowNumber())
                                .rowName(ticketTmp.get(0).getRowName())
                                .tickets(ticketTmp)
                                .build();
                        rows.add(rowShowtimeResponseTmp);
                    }
                }
                showtime.enrichRowShowtimeResponse(rows);
            }
        }
        return showtimes;
    }
}
