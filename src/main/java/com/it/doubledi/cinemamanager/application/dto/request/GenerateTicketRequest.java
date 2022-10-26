package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
public class GenerateTicketRequest extends Request {
    @NotEmpty(message = "SHOWTIME_IDS_REQUIRED")
    private List<String> showtimeIds;
}
