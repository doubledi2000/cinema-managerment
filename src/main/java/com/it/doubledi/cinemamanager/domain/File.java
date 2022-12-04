package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.FileCreateCmd;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File extends AuditableDomain {
    private String id;
    private String originalName;
    private String type;
    private Long size;
    private String path;
    private Boolean deleted;

    public File(FileCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.originalName = cmd.getOriginalName();
        this.type = cmd.getType();
        this.size = cmd.getSize();
        this.path = cmd.getPath();
        this.deleted = Boolean.FALSE;
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

    public void undelete() {
        this.deleted = Boolean.FALSE;
    }

}
