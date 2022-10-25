package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.ConfigPriceCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.LocationPriceConfigCmd;
import com.it.doubledi.cinemamanager.domain.command.PriceByTimeCreateCmd;
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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceConfig extends AuditableDomain {
    private String id;
    private PriceConfigType type;
    private DayOfWeek dayOfWeek;
    private String drinkId;
    private Boolean special;
    private SpecialBy specialBy;
    private PriceConfigStatus status;
    private Boolean deleted;
    private String locationId;
    private List<PriceByTime> priceByTimes;

    public PriceConfig(String locationId, ConfigPriceCreateCmd cmd, PriceConfigType type) {
        if (Objects.equals(type, PriceConfigType.TICKET)) {
            this.id = Objects.nonNull(cmd.getId()) ? cmd.getId() : IdUtils.nextId();
            this.type = PriceConfigType.TICKET;
            this.dayOfWeek = cmd.getDayOfWeek();
            this.status = PriceConfigStatus.ACTIVE;
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

    public List<PriceByTime> getPriceByTimeFromRequest(ConfigPriceCreateCmd cmd) {
        List<PriceByTime> priceByTimes = new ArrayList<>();
        for (PriceByTimeCreateCmd priceByTimeCreateRequest : cmd.getPriceByTimeCreateRequests()) {
            PriceByTime tmp = new PriceByTime(this.id, priceByTimeCreateRequest);
            priceByTimes.add(tmp);
        }
        return priceByTimes;
    }

    public List<PriceConfig> getPriceConfigFromCmd(LocationPriceConfigCmd cmd) {
        if (CollectionUtils.isEmpty(cmd.getConfigPriceCreateRequests())) {
            return new ArrayList<>();
        }
        List<PriceConfig> priceConfigs = new ArrayList<>();
        for (ConfigPriceCreateCmd configPriceCreateRequest : cmd.getConfigPriceCreateRequests()) {
            PriceConfig priceConfig = new PriceConfig(cmd.getLocationId(), configPriceCreateRequest, PriceConfigType.TICKET);
            List<PriceByTime> priceByTimes = priceConfig.getPriceByTimeFromRequest(configPriceCreateRequest);
            priceConfig.enrichPriceByTime(priceByTimes);
            priceConfigs.add(priceConfig);
        }
        return priceConfigs;
    }
}
