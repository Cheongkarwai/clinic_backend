package com.cheong.clinic_api.file.datafetcher;

import com.cheong.clinic_api.file.service.IFileService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@DgsComponent
public class FileDataFetcher {

//    @DgsData(parentType = "mutation",field = "uploadFile")
//    public boolean uploadFile(DataFetchingEnvironment dfe){
//
//    }

    private final IFileService fileService;

    public FileDataFetcher(@Qualifier("googleCloudFileService") IFileService fileService){
        this.fileService = fileService;
    }

    @DgsMutation
    public byte[] downloadInventoryReport(){
        return fileService.downloadInventoryReport();
    }

    @DgsMutation
    public List<String> uploadFiles(DataFetchingEnvironment dfe) throws IOException {
        List<MultipartFile> files = dfe.getArgument("files");

        return fileService.uploadFiles(files);

    }
}
