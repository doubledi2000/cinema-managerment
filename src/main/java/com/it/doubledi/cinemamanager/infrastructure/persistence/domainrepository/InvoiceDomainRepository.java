package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Invoice;
import com.it.doubledi.cinemamanager.domain.Item;
import com.it.doubledi.cinemamanager.domain.repository.InvoiceRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.InvoiceEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ItemEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ItemEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ItemEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class InvoiceDomainRepository extends AbstractDomainRepository<Invoice, InvoiceEntity, String> implements InvoiceRepository {

    private final ItemEntityRepository itemEntityRepository;
    private final ItemEntityMapper itemEntityMapper;

    public InvoiceDomainRepository(JpaRepository<InvoiceEntity, String> jpaRepository,
                                   EntityMapper<Invoice, InvoiceEntity> entityMapper,
                                   ItemEntityRepository itemEntityRepository,
                                   ItemEntityMapper itemEntityMapper) {
        super(jpaRepository, entityMapper);
        this.itemEntityRepository = itemEntityRepository;
        this.itemEntityMapper = itemEntityMapper;
    }

    @Override
    @Transactional
    public List<Invoice> saveALl(List<Invoice> domains) {
        List<Item> items = new ArrayList<>();
        domains.forEach(d -> {
            if (!CollectionUtils.isEmpty(d.getItems())) {
                items.addAll(d.getItems());
            }
        });
        List<ItemEntity> itemEntities = this.itemEntityMapper.toEntity(items);
        this.itemEntityRepository.saveAll(itemEntities);
        return super.saveALl(domains);
    }

    @Override
    protected List<Invoice> enrichList(List<Invoice> invoices) {
        List<String> invoiceIds = invoices.stream().map(Invoice::getId).collect(Collectors.toList());
        List<ItemEntity> itemEntities = this.itemEntityRepository.findAllByInvoiceIds(invoiceIds);
        List<Item> items = this.itemEntityMapper.toDomain(itemEntities);
        invoices.forEach(i -> {
            List<Item> itemTmps = items.stream().filter(it -> Objects.equals(i.getId(), it.getInvoiceId())).collect(Collectors.toList());
            i.enrichItem(itemTmps);
        });

        return super.enrichList(invoices);
    }

    @Override
    public Invoice getById(String id) {
        return this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.INVOICE_NOT_FOUND));
    }
}
