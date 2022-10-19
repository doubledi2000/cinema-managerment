package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@Builder
public class LocationPriceConfigRequest extends Request {

    private String locationId;

    private List<@Valid ConfigPriceCreateRequest> configPriceCreateRequests;
}
