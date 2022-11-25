//package com.it.doubledi.cinemamanager._common.web.config;
//
//import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.*;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
////@Configuration
//public class MyCustomFilter implements Filter {
//    private static final Map<String, String> exceptionMap = new HashMap<>();
//
//    public void init(FilterConfig config) throws ServletException {
//        Filter.super.init(config);
//        exceptionMap.put("/requestURL", "/redirectURL");
//        exceptionMap.put("/someOtherrequestURL", "/someOtherredirectURL");
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        try {
//            chain.doFilter(request, response);
//        } catch (Exception e) {
//
//        }
//        //loggetRequestURI
//
//    }
//}
