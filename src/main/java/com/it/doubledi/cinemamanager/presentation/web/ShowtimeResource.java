package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeConfigSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.response.ShowtimeResponse;
import com.it.doubledi.cinemamanager.domain.Showtime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
public interface ShowtimeResource {

    @PostMapping("/showtimes")
    @PreAuthorize("hasPermission(null, 'showtime:create')")
    Response<Showtime> createShowtime(@RequestBody @Valid ShowtimeCreateRequest request);

    @PostMapping("/showtimes/{id}/generate-tickets")
    @PreAuthorize("hasPermission(null, 'showtime:update') or hasPermission(null, 'showtime:create')")
    Response<Boolean> generateTicket(@PathVariable("id") String id);

    @PostMapping("/showtimes/{id}/cancel")
    @PreAuthorize("hasPermission(null, 'showtime:update') or hasPermission(null, 'showtime:create')")
    Response<Boolean> cancel(@PathVariable("id") String id);

    @GetMapping("/showtimes/{id}")
    @PreAuthorize("hasPermission(null, 'showtime:view')")
    Response<Showtime> findById(@PathVariable("id") String id);

    @GetMapping("/showtimes")
    @PreAuthorize("hasPermission(null, 'showtime:view')")
    Response<List<ShowtimeResponse>> search(ShowtimeSearchRequest request);

    @GetMapping("/showtimes/config")
    @PreAuthorize("hasPermission(null, 'showtime:view')")
    PagingResponse<Showtime> getShowtimeConfig(ShowtimeConfigSearchRequest request);

    @GetMapping("/showtimes/download-template")
    @PreAuthorize("hasPermission(null, 'showtime:create')")
    void downloadShowtimeTemplate(HttpServletResponse response);

    @PostMapping("/showtimes/upload-showtimes")
    @PreAuthorize("hasPermission(null, 'showtime:create')")
    Response<Boolean> uploadShowtime(@RequestBody MultipartFile file);
}
