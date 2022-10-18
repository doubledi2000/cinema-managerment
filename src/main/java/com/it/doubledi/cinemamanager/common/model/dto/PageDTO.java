package com.it.doubledi.cinemamanager.common.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Data
public class PageDTO<T> implements Serializable {

    private PageableDTO page = new PageableDTO();

    private List<T> data;

    public PageDTO() {
    }

    public PageDTO(List<T> data, int pageIndex, int pageSize, long total) {
        this.data = data;
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotal(total);
    }

    public <U> PageDTO(Page<U> pageInput, Function<List<U>, List<T>> mapper) {
        Pageable pageable = pageInput.getPageable();
        page.setPageIndex(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
        page.setTotal(pageInput.getTotalElements());
        List<T> content = mapper.apply(pageInput.getContent());
        if (content != null) {
            this.data = content;
        }
    }

    public static <T> PageDTO<T> of(List<T> data, int pageIndex, int pageSize, long total) {
        return new PageDTO<>(data, pageIndex, pageSize, total);
    }

    public static <T> PageDTO<T> empty() {
        return new PageDTO<>(new ArrayList<>(), 1, 30, 0);
    }

    public List<T> getData(){
        return this.data;
    }

    public PageableDTO getPage(){
        return this.page;
    }
    @Data
    @Getter
    @Setter
    public static class PageableDTO implements Serializable {
        private int pageIndex = 0;
        private int pageSize = 0;
        private long total = 0;

        public int getPageIndex() {
            return pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public long getTotal() {
            return total;
        }

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