package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Row extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private Integer rowNumber;
    private Boolean deleted;
    private String roomId;

    private Room room;
    private List<Chair> chairs;

    public void enrichChairs(List<Chair> chairs) {
        if (!CollectionUtils.isEmpty(chairs)) {
            this.chairs = chairs;
        } else {
            this.chairs = new ArrayList<>();
        }
    }

    public void duplicate(String roomId, String code) {
        this.id = IdUtils.nextId();
        this.code = code;
        this.name += Constant.COPY_SUFFIX;
        this.roomId = roomId;
    }

    public void delete() {

    }

    public void addChair(Chair chair) {
        if (CollectionUtils.isEmpty(this.chairs)) {
            chairs = new ArrayList<>();
        }
        chairs.add(chair);
    }
}
