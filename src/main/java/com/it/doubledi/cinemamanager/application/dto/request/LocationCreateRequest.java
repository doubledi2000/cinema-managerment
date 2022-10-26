package com.it.doubledi.cinemamanager.application.dto.request;


import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationCreateRequest extends Request {
    private String code;
    private String name;
}
