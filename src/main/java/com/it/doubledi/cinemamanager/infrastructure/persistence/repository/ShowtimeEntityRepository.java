package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ShowtimeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.ShowtimeRepositoryCustom;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowtimeEntityRepository extends JpaRepository<ShowtimeEntity, String>, ShowtimeRepositoryCustom {
    @Query("from ShowtimeEntity s where s.deleted = false and s.id in :ids")
    List<ShowtimeEntity> findAllByIds(List<String> ids);

    @Query("from ShowtimeEntity s where s.deleted = false and s.roomId = :roomId and s.premiereDate = :premiereDate")
    List<ShowtimeEntity> findByRoomIdAndPremiereDate(@Param("roomId") String roomId, @Param("premiereDate") LocalDate premiereDate);

    @Query("From ShowtimeEntity s where s.deleted = false " +
            " and (coalesce(:filmIds,null) is null or s.filmId in :filmIds) " +
            " and (coalesce(:locationIds, null) is null or s.locationId in :locationIds )" +
            " and s.premiereDate = :premiereDate " +
            " and s.endAt > :startAt" +
            " order by s.createdAt desc")
    List<ShowtimeEntity> findShowtimeByParams(List<String> filmIds, LocalDate premiereDate, List<String> locationIds, int startAt);

    @Query("From ShowtimeEntity s where s.deleted = false and s.roomId in :roomIds and s.premiereDate = :premiereDate")
    List<ShowtimeEntity> findAllByRoomIds(List<String> roomIds, LocalDate premiereDate);


    @Query("From ShowtimeEntity s where s.deleted = false " +
            " and (s.premiereDate < :premiereDate or s.premiereDate = :premiereDate and s.endAt < :endAt) " +
            " and s.status not in :statuses")
    List<ShowtimeEntity> findAllToFinish(LocalDate premiereDate, int endAt, List<ShowtimeStatus> statuses);

    @Query("From ShowtimeEntity s where s.deleted = false and s.premiereDate <= :premiereDate and s.status = :status")
    List<ShowtimeEntity> findAllToGenerateTicket(LocalDate premiereDate, ShowtimeStatus status);

    @Query("From ShowtimeEntity s where s.deleted = false and s.filmId in :filmIds and s.status in :statuses")
    List<ShowtimeEntity> findAllByFilmIdsAndStatuses(List<String> filmIds, List<ShowtimeStatus> statuses);
}
