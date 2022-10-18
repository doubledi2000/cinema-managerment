package com.it.doubledi.cinemamanager.common.model.dto.response;

import com.it.doubledi.cinemamanager.common.model.dto.PageDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

@Data
public class PagingResponse<T> extends Response<List<T>> {
    private PageableResponse page = new PageableResponse();

    public PagingResponse() {
    }

    public PagingResponse(List<T> data, int pageIndex, int pageSize, long total) {
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotal(total);
        this.data = data;
        success();
    }

    public PagingResponse(PageDTO<T> pageInput) {
        this.data = pageInput.getData();
        page.setPageIndex(pageInput.getPage().getPageIndex());
        page.setPageSize(pageInput.getPage().getPageSize());
        page.setTotal(pageInput.getPage().getTotal());
        success();
    }

    public <U> PagingResponse(PageDTO<U> pageInput, Function<List<U>, List<T>> mapper) {
        PageDTO.PageableDTO pageable = pageInput.getPage();
        page.setPageIndex(pageable.getPageIndex());
        page.setPageSize(pageable.getPageSize());
        page.setTotal(pageable.getTotal());
        List<T> content = mapper.apply(pageInput.getData());
        if (content != null) {
            this.data = content;
        }
    }

    public static <T> PagingResponse<T> of(List<T> data, int pageIndex, int pageSize, long total) {
        return new PagingResponse<>(data, pageIndex, pageSize, total);
    }

    public static <T> PagingResponse<T> of(PageDTO<T> pageInput) {
        return new PagingResponse<>(pageInput);
    }

    public static <T> PagingResponse<T> failPaging(RuntimeException exception) {
        PagingResponse<T> response = new PagingResponse<>();
        response.setSuccess(false);
        response.setException(exception);
        return response;
    }

    @Data
    public static class PageableResponse implements Serializable {
        private int pageIndex;
        private int pageSize;
        private long total;
        public void setPageIndex(int pageIndex){
            this.pageIndex = pageIndex;
        }

        public void setPageSize(int pageSize){
            this.pageSize = pageSize;
        }

        public void setTotal(long total) {
            this.total = total;
        }
    }
}
