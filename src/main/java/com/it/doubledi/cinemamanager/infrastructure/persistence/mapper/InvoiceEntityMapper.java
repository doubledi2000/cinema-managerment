package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Invoice;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.InvoiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceEntityMapper extends EntityMapper<Invoice, InvoiceEntity> {
}
