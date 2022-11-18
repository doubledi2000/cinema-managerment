package com.it.doubledi.cinemamanager._common.web.security;

import com.it.doubledi.cinemamanager._common.model.UserAuthority;

public interface AuthorityService {
    UserAuthority getUserAuthority(String userId);
}
