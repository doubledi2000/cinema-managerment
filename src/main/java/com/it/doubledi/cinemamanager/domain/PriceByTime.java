package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.PriceByTimeCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.PriceCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceByTime extends AuditableDomain implements Constant {
    private String id;
    private Integer startAt;
    private Integer endAt;
    private String priceConfigId;
    private Boolean deleted;

    private List<Price> prices;

    public PriceByTime(String priceConfigId, PriceByTimeCreateCmd cmd) {
        this.id = Objects.nonNull(cmd.getId()) ? cmd.getId() : IdUtils.nextId();
        this.startAt = cmd.getStartAt();
        this.endAt = cmd.getEndAt();
        this.priceConfigId = priceConfigId;
        this.deleted = Boolean.FALSE;
    }

    public PriceByTime(String priceConfigId) {
        this.id = IdUtils.nextId();
        this.startAt = MIN_START_AT;
        this.endAt = MAX_START_AT;
        this.priceConfigId = priceConfigId;
        this.deleted = Boolean.FALSE;
        this.addPrice(new Price(this.id, TicketType.NORMAL));
        this.addPrice(new Price(this.id, TicketType.VIP));
        this.addPrice(new Price(this.id, TicketType.SWEET));
    }


    public void update(PriceByTimeCreateCmd cmd) {
        this.startAt = cmd.getStartAt();
        this.endAt = cmd.getEndAt();

    }

    public void enrichPrices(List<Price> prices) {
        if (CollectionUtils.isEmpty(prices)) {
            this.prices = new ArrayList<>();
        } else {
            this.prices = prices;
        }
    }

    public void addPrice(Price price) {
        if (CollectionUtils.isEmpty(this.getPrices())) {
            this.prices = new ArrayList<>();
            prices.add(price);
        } else {
            prices.add(price);
        }
    }

    private void updatePrice(List<PriceCreateCmd> cmd) {
//        this.getPrices().forEach(Price::delete);
        for (Price price : this.getPrices()) {
            Optional<PriceCreateCmd> cmdTmp = cmd.stream().filter(p -> Objects.equals(p.getTicketType(), price.getTicketType())).findFirst();
            if (cmdTmp.isPresent()) {
                price.update(cmdTmp.get());
            } else {
                Price tmp = new Price(this.id, cmdTmp.get());
            }


        }
    }

}
