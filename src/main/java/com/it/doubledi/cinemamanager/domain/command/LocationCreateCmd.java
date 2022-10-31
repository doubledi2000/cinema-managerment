package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LocationCreateCmd {
    private String code;
    private String name;
    private String address;
}
