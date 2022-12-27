package com.it.doubledi.cinemamanager._common.web.config;

import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private final String ANONYMOUS_ACCOUNT = "anonymous";

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUser().orElse(ANONYMOUS_ACCOUNT));
    }
}
