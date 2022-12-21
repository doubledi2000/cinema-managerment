package com.it.doubledi.cinemamanager._common.web.i18n.impl;

import com.it.doubledi.cinemamanager._common.web.i18n.LocaleStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Locale;

@Service
@Slf4j
public class LocaleStringServiceImpl implements LocaleStringService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public Locale getCurrentLocale() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return Locale.getDefault();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return request.getLocale();

    }

    @Override
    public String getMessages(String messageCode, String defaultMessage, Object... params) {
        Locale locale = this.getCurrentLocale();
        try {
            return messageSource.getMessage(messageCode, params, locale);
        } catch (Exception e) {
            log.warn("Could not find message {} for locale {}. Using default message ", messageCode, locale);
            try {
                return MessageFormat.format(defaultMessage, params);
            } catch (Exception e1) {
                return defaultMessage;
            }
        }
    }
}
