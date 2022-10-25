package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
//@SuperBuilder
public class Producer extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String description;
    private String deleted;
    private String representative;
    private String nationally;

}
