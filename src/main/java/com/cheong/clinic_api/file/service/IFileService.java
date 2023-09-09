package com.cheong.clinic_api.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileService {

    List<String> uploadFiles(List<MultipartFile> files) throws IOException;

    byte [] downloadInventoryReport();
}
