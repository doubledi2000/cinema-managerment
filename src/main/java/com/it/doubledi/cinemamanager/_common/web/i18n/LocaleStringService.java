package com.it.doubledi.cinemamanager._common.web.i18n;

import java.util.Locale;

public interface LocaleStringService {

    Locale getCurrentLocale();

    String getMessages(String messageCode, String defaultMessage, Object... params);
}
