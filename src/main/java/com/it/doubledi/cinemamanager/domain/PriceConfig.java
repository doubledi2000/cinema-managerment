package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.ConfigPriceCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.LocationPriceConfigCmd;
import com.it.doubledi.cinemamanager.domain.command.PriceByTimeCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.SpecialBy;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
//@SuperBuilder
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
        List<PriceByTime> priceByTimeList = new ArrayList<>();
        if (CollectionUtils.isEmpty(getPriceByTimes())) {
            for (PriceByTimeCreateCmd priceByTimeCreateRequest : cmd.getPriceByTimeCreateRequests()) {
                PriceByTime tmp = new PriceByTime(this.id, priceByTimeCreateRequest);
                priceByTimeList.add(tmp);
            }
        } else {
            this.priceByTimes.forEach(PriceByTime::delete);
            List<PriceByTimeCreateCmd> priceByTimeMap = cmd.getPriceByTimeCreateRequests().stream()
                    .filter(f -> !Objects.equals(f.getTicketType(), TicketType.BOGY))
                    .collect(Collectors.groupingBy(PriceByTimeCreateCmd::getTicketType))
                    .values()
                    .stream()
                    .flatMap(group -> group.stream().limit(1))
                    .collect(Collectors.toList());

            for (PriceByTimeCreateCmd priceByTimeCreateCmd : priceByTimeMap) {
                Optional<PriceByTime> priceByTimeTmp = this.priceByTimes.stream()
                        .filter(p -> Objects.equals(p.getTicketType(), priceByTimeCreateCmd.getTicketType()))
                        .findFirst();

                if(priceByTimeTmp.isPresent()) {
                }

            }
        }
        return priceByTimeList;
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
