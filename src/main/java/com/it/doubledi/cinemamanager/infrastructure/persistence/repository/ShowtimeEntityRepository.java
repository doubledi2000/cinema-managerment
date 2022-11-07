package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowtimeEntityRepository extends JpaRepository<ShowtimeEntity, String> {
    @Query("from ShowtimeEntity s where s.deleted = false and s.id in :ids")
    List<ShowtimeEntity> findAllByIds(List<String> ids);

    @Query("from ShowtimeEntity s where s.deleted = false and s.roomId = :roomId and s.premiereDate = :premiereDate")
    List<ShowtimeEntity> findByRoomIdAndPremiereDate(@Param("roomId") String roomId, @Param("premiereDate")LocalDate premiereDate);

    @Query("From ShowtimeEntity s where s.deleted = false " +
            " and (coalesce(:filmIds,null) is null or s.filmId in :filmIds) " +
            " and s.premiereDate = :premiereDate " +
            " and s.endAt > :startAt" +
            " order by s.createdAt desc")
    List<ShowtimeEntity> findShowtimeByParams(List<String> filmIds, LocalDate premiereDate, int startAt);
}
