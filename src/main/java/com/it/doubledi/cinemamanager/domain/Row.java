package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Row  extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private Integer rowNumber;
    private Boolean deleted;
    private String roomId;

    private Room room;
    private List<Chair> chairs;

    public void enrichChairs(List<Chair> chairs) {
        if(!CollectionUtils.isEmpty(chairs)) {
            this.chairs = chairs;
        }else {
            this.chairs = new ArrayList<>();
        }
    }

    public void delete(){

    }

}
