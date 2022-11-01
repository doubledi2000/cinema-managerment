package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProducerCreateCmd {
    private String code;
    private String name;
    private String description;
    private String representative;
    private String nationally;
}
