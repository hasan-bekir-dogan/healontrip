package com.healontrip.service.impl;

import com.healontrip.dto.FileDto;
import com.healontrip.entity.FileEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.FileRepository;
import com.healontrip.service.FileService;
import com.healontrip.util.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Long saveFile(FileDto fileDto) throws IOException {
        MultipartFile file = fileDto.getFile();

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedTime = currentTime.format(formatter);

        FileEntity fileEntity = new FileEntity();
        String longSrc = fileDto.getSource();

        fileEntity.setAlt(fileDto.getAlt());
        fileEntity.setCode(fileDto.getCode());
        fileEntity.setExtension(fileDto.getExtension());
        fileEntity.setSource(fileDto.getSource());

        fileRepository.save(fileEntity);

        String name = formattedTime + fileEntity.getId().toString();
        String fullExtendedName = name + fileDto.getExtension();

        fileEntity.setName(name);

        FileUploadUtil.saveFile(longSrc, fullExtendedName, file);

        fileRepository.save(fileEntity);

        return fileEntity.getId();
    }

    @Override
    public Long updateFile(FileDto fileDtoOld, FileDto fileDtoNew) throws IOException {
        FileEntity fileEntity = findById(fileDtoOld.getId());
        Long fileId;

        if (fileEntity.getCode() != null) { // default image
            fileId = saveFile(fileDtoNew);
        }
        else { // no default image
            MultipartFile file = fileDtoNew.getFile();

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formattedTime = currentTime.format(formatter);

            String longSrc = fileDtoNew.getSource();

            String name = formattedTime + fileEntity.getId().toString();
            String fullExtendedName = name + fileDtoNew.getExtension();

            fileEntity.setName(name);

            FileUploadUtil.saveFile(longSrc, fullExtendedName, file);

            fileRepository.save(fileEntity);

            fileId = fileEntity.getId();

            deleteFileInFolder(fileDtoOld);
        }

        return fileId;
    }

    @Override
    public void deleteFile(FileDto fileDto) throws IOException {
        String longSrc = fileDto.getSource();
        String fullExtendedName = fileDto.getName() + fileDto.getExtension();

        FileUploadUtil.deleteFile(longSrc, fullExtendedName);

        FileEntity fileEntity = DtoToEntity(fileDto);

        fileRepository.delete(fileEntity);
    }

    @Override
    public void deleteFileInFolder(FileDto fileDto) throws IOException {
        String longSrc = fileDto.getSource();
        String fullExtendedName = fileDto.getName() + fileDto.getExtension();

        FileUploadUtil.deleteFile(longSrc, fullExtendedName);
    }

    @Override
    public FileEntity findById(Long id) {
        return fileRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("File not found with id: " + id));
    }

    @Override
    public String getFileSrc(Long id) {
        FileEntity fileEntity = fileRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("File not found with id: " + id));

        String src = fileEntity.getSource() + fileEntity.getName() + fileEntity.getExtension();

        return src;
    }

    @Override
    public FileEntity findByCode(String code){
        return fileRepository.findByCode(code);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Model Mapper
    // Entity => DTO
    @Override
    public FileDto EntitytoDto(FileEntity fileEntity) {
        return modelMapper.map(fileEntity, FileDto.class);
    }

    // DTO => Entity
    @Override
    public FileEntity DtoToEntity(FileDto fileDto) {
        return modelMapper.map(fileDto, FileEntity.class);
    }
}
