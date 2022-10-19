package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeOfFilmUpdateCmd {
    private String name;
    private String description;
}
