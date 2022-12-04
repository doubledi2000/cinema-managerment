package com.it.doubledi.cinemamanager.domain.repository;

import com.it.doubledi.cinemamanager._common.web.DomainRepository;
import com.it.doubledi.cinemamanager.domain.File;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends DomainRepository<File, String> {
}
