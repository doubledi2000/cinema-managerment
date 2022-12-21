package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.PriceByTimeCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.PriceConfigCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.SpecialBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceConfig extends AuditableDomain {
    private String id;
    private PriceConfigType type;
    private int dayOfWeek;
    private String drinkId;
    private Boolean special;
    private SpecialBy specialBy;
    private PriceConfigStatus status;
    private Boolean deleted;
    private String locationId;
    private Long version;
    private List<PriceByTime> priceByTimes;

    public PriceConfig(String locationId, PriceConfigCreateCmd cmd, PriceConfigType type) {
        if (Objects.equals(type, PriceConfigType.TICKET)) {
            this.id = Objects.nonNull(cmd.getId()) ? cmd.getId() : IdUtils.nextId();
            this.type = PriceConfigType.TICKET;
            this.dayOfWeek = cmd.getDayOfWeek();
            this.status = PriceConfigStatus.ACTIVE;
            this.locationId = locationId;
            this.deleted = Boolean.FALSE;
        }
    }

    public void enrichPriceByTime(List<PriceByTime> priceByTimes) {
        if (CollectionUtils.isEmpty(priceByTimes)) {
            this.priceByTimes = new ArrayList<>();
        } else {
            this.priceByTimes = priceByTimes;
        }
    }

    public List<PriceByTime> getPriceByTimeFromRequest(PriceConfigCreateCmd cmd) {
        List<PriceByTime> priceByTimes = new ArrayList<>();
        for (PriceByTimeCreateCmd priceByTimeCreateRequest : cmd.getPriceByTimes()) {
            PriceByTime tmp = new PriceByTime(this.id, priceByTimeCreateRequest);
            priceByTimes.add(tmp);
        }
        return priceByTimes;
    }

    public void update(PriceConfigCreateCmd cmd) {
        for (PriceByTime priceByTime : this.getPriceByTimes()) {
            Optional<PriceByTimeCreateCmd> priceByTimeOptional = cmd.getPriceByTimes().stream()
                    .filter(p -> Objects.equals(p.getId(), priceByTime.getId()))
                    .findFirst();

        }
    }

    public PriceConfig(String locationId, int value, Boolean special) {
        this.id = IdUtils.nextId();
        this.locationId = locationId;
        this.dayOfWeek = value;
        this.deleted = Boolean.FALSE;
        this.special = special;
        this.status = PriceConfigStatus.ACTIVE;
        PriceByTime priceByTime = new PriceByTime(this.id);
        this.enrichPriceByTime(List.of(priceByTime));
    }

    public void delete(){
        this.priceByTimes.forEach(PriceByTime::delete);
    }

    public void updatePriceList(PriceConfig priceConfigUpdate){
        for (PriceByTime priceByTimeUpdate : priceConfigUpdate.priceByTimes) {
            Optional<PriceByTime> priceByTimeOptional = this.priceByTimes.stream()
                    .filter(p-> Objects.equals(p.getId(), priceByTimeUpdate.getId()))
                    .findFirst();

            if(priceByTimeOptional.isPresent()) {
                priceByTimeOptional.get().update(priceByTimeUpdate);
            }else {
                PriceByTime priceByTime = new PriceByTime(this.id, priceByTimeUpdate);
                this.addPriceByTime(priceByTime);
            }
        }
    }

    public void addPriceByTime(PriceByTime priceByTime){
        if(CollectionUtils.isEmpty(priceByTimes)){
            this.priceByTimes = new ArrayList<>();
        }
        this.priceByTimes.add(priceByTime);
    }

//
//    public List<PriceConfig> getPriceConfigFromCmd(LocationPriceConfigCmd cmd) {
//        if (CollectionUtils.isEmpty(cmd.getPriceByTimes())) {
//            return new ArrayList<>();
//        }
//        List<PriceConfig> priceConfigs = new ArrayList<>();
//        for (PriceConfigCreateCmd configPriceCreateRequest : cmd.getPriceByTimes()) {
//            PriceConfig priceConfig = new PriceConfig(, configPriceCreateRequest, PriceConfigType.TICKET);
//            List<PriceByTime> priceByTimes = priceConfig.getPriceByTimeFromRequest(configPriceCreateRequest);
//            priceConfig.enrichPriceByTime(priceByTimes);
//            priceConfigs.add(priceConfig);
//        }
//        return priceConfigs;
//    }
}
