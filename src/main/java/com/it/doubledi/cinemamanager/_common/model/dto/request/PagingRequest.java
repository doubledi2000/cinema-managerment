package com.it.doubledi.cinemamanager._common.model.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingRequest extends Request {
    public static final String ACS_SYMBOL = "asc";
    public static final String DESC_SYMBOL = "desc";
    public static final Integer MAX_PAGE_INDEX = 10000;
    public static final Integer MAX_PAGE_SIZE = 1000;

    @Min(value = 1, message = "Page index must be greater than 0")
    @Max(value = 10000, message = "page index must be less than or equal 10000")
    protected int pageIndex = 1;

    @Min(value = 1, message = "Page size must be greater than 0")
    @Max(value = 1000, message = "Page size must be less than or equal 1000")
    protected int pageSize = 30;

    protected String sortBy;

}
