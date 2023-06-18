package com.healontrip.entity;

import com.healontrip.dto.FileCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name = "files")
public class FileEntity extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private FileCode code;

    @Column(name = "alt", nullable = false, columnDefinition = "varchar(255) default 'File'")
    private String alt;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "name")
    private String name;

    @Column(name = "extension", nullable = false)
    private String extension;

    public FileEntity(FileCode code, String alt, String source, String name, String extension) {
        this.code = code;
        this.alt = alt;
        this.source = source;
        this.name = name;
        this.extension = extension;
    }
}
