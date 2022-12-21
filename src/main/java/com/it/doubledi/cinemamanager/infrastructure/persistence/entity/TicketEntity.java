package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ticket", indexes = {
        @Index(name = "ticket_showtime_id_idx", columnList = "showtime_id"),
        @Index(name = "ticket_row_id_idx", columnList = "row_id"),
        @Index(name = "ticket_room_id_idx", columnList = "room_id"),
        @Index(name = "ticket_film_id_idx", columnList = "film_id")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Column(name = "chair_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String chairId;

    @Column(name = "showtime_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String showtimeId;

    @Column(name = "price")
    private Float price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ChairType type;

    @Column(name = "film_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String filmId;

    @Column(name = "room_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String roomId;

    @Column(name = "row_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String rowId;

    @Column(name = "row_number")
    private Integer rowNumber;

    @Column(name = "row_name")
    private String rowName;

    @Column(name = "serial_of_chair")
    private Integer serialOfChair;

    @Column(name = "user_sold_id")
    private String userSoldId;

    @Column(name = "deleted")
    private Boolean deleted;
}
