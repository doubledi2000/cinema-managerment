package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.dto.request.InvoiceCreateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.BookingService;
import com.it.doubledi.cinemamanager.domain.Drink;
import com.it.doubledi.cinemamanager.domain.Invoice;
import com.it.doubledi.cinemamanager.domain.Showtime;
import com.it.doubledi.cinemamanager.domain.command.InvoiceCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ItemCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.InvoiceRepository;
import com.it.doubledi.cinemamanager.domain.repository.ShowtimeRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.DrinkEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.DrinkEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.DrinkEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ShowtimeRepository showtimeRepository;
    private final AutoMapper autoMapper;
    private final DrinkEntityRepository drinkEntityRepository;
    private final DrinkEntityMapper drinkEntityMapper;
    private final InvoiceRepository invoiceRepository;

    @Override
    @Transactional
        public Invoice booking(InvoiceCreateRequest request) {
        UserAuthentication userAuthority = SecurityUtils.authentication();
        InvoiceCreateCmd cmd = this.autoMapper.from(request);
        Invoice invoice = new Invoice(userAuthority.getUserId());
        if (StringUtils.hasLength(cmd.getShowtimeId())) {
            Showtime showtime = this.showtimeRepository.getById(cmd.getShowtimeId());
            showtime.getRows().forEach(r -> {
                r.getTickets().forEach(t -> {
                    if (Objects.equals(t.getStatus(), TicketStatus.SELECTED)
                            && Objects.equals(t.getUserSoldId(), userAuthority.getUserId())) {
                        t.sold();
                        invoice.addItem(t);
                    }
                });
            });
            this.showtimeRepository.save(showtime);
        }

        if (!CollectionUtils.isEmpty(cmd.getItems())) {
            List<String> drinkIds = cmd.getItems().stream().map(ItemCreateCmd::getItemId).collect(Collectors.toList());
            List<DrinkEntity> drinkEntities = this.drinkEntityRepository.findAllByIds(cmd.getLocationId(), drinkIds);
            List<Drink> drinks = this.drinkEntityMapper.toDomain(drinkEntities);
            drinks.forEach(d -> {
                int quantity = cmd.getItems().stream().filter(item -> Objects.equals(item.getItemId(), d.getId())).map(ItemCreateCmd::getQuantity).reduce(0, Integer::sum);
                invoice.addItem(d, quantity);
            });
        }
        invoice.calculatorTotal();
        this.invoiceRepository.save(invoice);
        return invoice;
    }

    @Override
    public Invoice getById(String id) {
        return this.invoiceRepository.getById(id);
    }
}
