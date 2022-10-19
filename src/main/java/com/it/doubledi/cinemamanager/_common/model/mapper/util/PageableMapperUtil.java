package com.it.doubledi.cinemamanager._common.model.mapper.util;

import com.it.doubledi.cinemamanager._common.model.dto.request.PagingRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PageableMapperUtil {
    private static final String SORT_BY_CREATED_AT = "createdAt";

    public static Pageable toPageable(PagingRequest criteria) {
        List<Sort.Order> orders = new ArrayList<>();

        if (StringUtils.hasLength(criteria.getSortBy())) {
            String sortStr = criteria.getSortBy().trim();
            if (StringUtils.hasLength(sortStr)) {
                if (sortStr.contains(".")) {
                    String[] sortParams = sortStr.split("\\.");
                    if (sortStr.endsWith(PagingRequest.DESC_SYMBOL) && StringUtils.hasLength(sortParams[0])) {
                        orders.add(Sort.Order.desc(sortParams[0]));
                    } else if (sortStr.endsWith(PagingRequest.ACS_SYMBOL) && StringUtils.hasLength(sortParams[0])) {
                        orders.add(Sort.Order.asc(sortParams[0]));
                    }
                }else {
                    orders.add(Sort.Order.asc(sortStr));
                }
            }
        }
        if(CollectionUtils.isEmpty(orders)) {
            orders.add(Sort.Order.desc(SORT_BY_CREATED_AT));
        }
        return PageRequest.of(criteria.getPageIndex() - 1, criteria.getPageSize(), Sort.by(orders));
    }
}
