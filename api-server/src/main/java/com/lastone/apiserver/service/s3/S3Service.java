package com.lastone.apiserver.service.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    List<String> upload(List<MultipartFile> multipartFiles) throws IOException;

    String upload(MultipartFile multipartFile) throws IOException;

    void delete(String fileName);
}
