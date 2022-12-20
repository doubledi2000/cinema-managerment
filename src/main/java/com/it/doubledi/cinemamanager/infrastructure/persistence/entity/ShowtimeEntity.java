package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "showtime", indexes = {
        @Index(name = "room_id_showtime_idx", columnList = "room_id"),
        @Index(name = "film_id_showtime_idx", columnList = "film_id")
})

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "premiere_date")
    private LocalDate premiereDate;

    @Column(name = "start_at")
    private Integer startAt;

    @Column(name = "end_at")
    private Integer endAt;

    @Column(name = "room_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String roomId;

    @Column(name = "film_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String filmId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ShowtimeStatus status;

    @Column(name = "auto_generate_ticket")
    private Boolean autoGenerateTicket;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "version")
    @Version
    private Long version;

}
