package com.healontrip.service;

import com.healontrip.dto.FileDto;
import com.healontrip.entity.FileEntity;

import java.io.IOException;

public interface FileService {
    Long saveFile(FileDto fileDto) throws IOException;
    Long updateFile(FileDto fileDtoOld, FileDto fileDtoNew) throws IOException;
    void deleteFile(FileDto fileDto) throws IOException;
    void deleteFileInFolder(FileDto fileDto) throws IOException;
    FileEntity findById(Long id);
    String getFileSrc(Long id);
    FileEntity findByCode(String code);
    FileDto EntitytoDto(FileEntity fileEntity);
    FileEntity DtoToEntity(FileDto fileDto);
}
