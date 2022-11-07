package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmProducer extends AuditableDomain {
    private String id;
    private String filmId;
    private String producerId;
    private Boolean deleted;
    private String producerName;
    private String producerCode;

    public FilmProducer(String filmId, String producerId) {
        this.id = IdUtils.nextId();
        this.filmId = filmId;
        this.producerId = producerId;
        this.deleted = Boolean.FALSE;
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

    public void undelete() {
        this.deleted = Boolean.TRUE;
    }

    public void enrichProducer(Producer producer) {
        this.producerName = producer.getName();
        this.producerCode = producer.getCode();
    }
}
