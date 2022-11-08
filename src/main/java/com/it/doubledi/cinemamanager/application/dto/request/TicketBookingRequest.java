package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketBookingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketBookingRequest {
    private String showtimeId;
    private String ticketId;
    private String userId;
    private TicketBookingType type;

    public TicketBookingRequest(String str) {
        str = str.replace("{", "");
        str = str.replace("}", "");
        str = str.replace("\"","");
        String[] parseArr = str.split(",");
        String showtimeIdStr = parseArr[0];
        String ticketIdStr = parseArr[1];
        String userIdStr = parseArr[2];
        String typeStr = parseArr[3];
        this.showtimeId = showtimeIdStr.split(":")[1].trim();
        this.ticketId = ticketIdStr.split(":")[1].trim();
        this.userId = userIdStr.split(":")[1].trim();
        this.type = TicketBookingType.valueOf(typeStr.split(":")[1].trim());
    }
}
