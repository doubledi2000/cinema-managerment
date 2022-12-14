package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeConfigSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.response.ShowtimeResponse;
import com.it.doubledi.cinemamanager.application.service.ExcelService;
import com.it.doubledi.cinemamanager.application.service.ShowtimeService;
import com.it.doubledi.cinemamanager.domain.Showtime;
import com.it.doubledi.cinemamanager.presentation.web.ShowtimeResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
public class ShowtimeResourceImpl implements ShowtimeResource {

    private final ShowtimeService showtimeService;
    private final ExcelService excelService;

    @Override
    public Response<Showtime> createShowtime(ShowtimeCreateRequest request) {
        return Response.of(showtimeService.createMulti(request));
    }

    @Override
    public Response<Boolean> generateTicket(String id) {
        showtimeService.generateTicket(id, true);
        return Response.ok();
    }

    @Override
    public Response<Boolean> cancel(String id) {
        this.showtimeService.cancelShowtime(id);
        return Response.ok();
    }

    @Override
    public Response<Showtime> findById(String id) {
        return Response.of(showtimeService.getById(id));
    }

    @Override
    public Response<List<ShowtimeResponse>> search(ShowtimeSearchRequest request) {
        return Response.of(showtimeService.search(request));
    }

    @Override
    public PagingResponse<Showtime> getShowtimeConfig(ShowtimeConfigSearchRequest request) {
        return PagingResponse.of(this.showtimeService.getShowtimeConfig(request));
    }

    @Override
    public void downloadShowtimeTemplate(HttpServletResponse response) {
        this.excelService.downloadShowtimeTemplate(response);
    }

    @Override
    public Response<Boolean> uploadShowtime(MultipartFile file) {
        this.excelService.uploadShowtime(file);
        return Response.ok();
    }
}
