package com.cheong.clinic_api.file.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GoogleCloudFileService implements IFileService{

    private final Storage storage;

    @Value("${gcp.storage.bucket-name}")
    private String bucketName;

    public GoogleCloudFileService(Storage storage){
        this.storage = storage;
    }
    @Override
    public List<String> uploadFiles(List<MultipartFile> files) {

        List<String> pathToUploadedFiles = new ArrayList<>();

        for(MultipartFile file : files){
            BlobId blobId = BlobId.of(bucketName, "images/"+Objects.requireNonNull(file.getOriginalFilename()));
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            //Blob blob = storage.create(blobInfo,file.getBytes());
            //pathToUploadedFiles.add(blob.getGeneratedId());
        }

        return pathToUploadedFiles;
    }

    @Override
    public byte[] downloadInventoryReport() {
        return new byte[0];
    }
}
