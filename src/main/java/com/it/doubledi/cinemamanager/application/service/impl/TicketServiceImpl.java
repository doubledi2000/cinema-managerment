//package com.it.doubledi.cinemamanager.application.service.impl;
//
//import com.it.doubledi.cinemamanager._common.util.IdUtils;
//import com.it.doubledi.cinemamanager.application.service.TicketService;
//import com.it.doubledi.cinemamanager.domain.Chair;
//import com.it.doubledi.cinemamanager.domain.Room;
//import com.it.doubledi.cinemamanager.domain.Row;
//import com.it.doubledi.cinemamanager.domain.Ticket;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//
//public class TicketServiceImpl implements TicketService {
//    @Override
//    public List<Ticket> generateTickets(String showtimeId, Room room) {
//        List<Ticket> tickets = new ArrayList<>();
//        if(CollectionUtils.isEmpty(room.getRows())){
//            return new ArrayList<>();
//        }
//        for (Row row : room.getRows()) {
//            if(CollectionUtils.isEmpty(row.getChairs())) {
//                continue;
//            }
//            for (Chair chair : row.getChairs()) {
//                Ticket ticket = Ticket.builder()
//                        .id(IdUtils.nextId())
//                        .name(row.getName() + chair.getSerialOfChair())
//                        .chairId(chair.getId())
//                        .showtimeId(showtimeId)
//                        .price()
//                        .build();
//            }
//
//        }
//        return tickets;
//    }
//}
