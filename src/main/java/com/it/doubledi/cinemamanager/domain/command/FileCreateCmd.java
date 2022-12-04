package com.it.doubledi.cinemamanager.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class FileCreateCmd {
    private String originalName;
    private String type;
    private Long size;
    private String path;
}
