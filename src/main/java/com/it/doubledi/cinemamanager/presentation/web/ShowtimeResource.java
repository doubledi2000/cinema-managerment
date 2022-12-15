package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeConfigSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.response.ShowtimeResponse;
import com.it.doubledi.cinemamanager.domain.Showtime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
public interface ShowtimeResource {

    @PostMapping("/showtimes")
    Response<Showtime> createShowtime(@RequestBody @Valid ShowtimeCreateRequest request);

    @PostMapping("/showtimes/{id}/generate-tickets")
    Response<Boolean> generateTicket(@PathVariable("id") String id);

    @GetMapping("/showtimes/{id}")
    Response<Showtime> findById(@PathVariable("id") String id);

    @GetMapping("/showtimes")
    Response<List<ShowtimeResponse>> search(ShowtimeSearchRequest request);

    @GetMapping("/showtimes/config")
    PagingResponse<Showtime> getShowtimeConfig(ShowtimeConfigSearchRequest request);

    @GetMapping("/showtimes/download-template")
    void downloadShowtimeTemplate(HttpServletResponse response);

    @PostMapping("/showtimes/upload-showtimes")
    Response<Boolean> uploadShowtime(@RequestBody MultipartFile file);
}
