package com.it.doubledi.cinemamanager.application.dto.request;


import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCreateRequest extends Request {
    private String code;
    private String name;
    private String address;
}
