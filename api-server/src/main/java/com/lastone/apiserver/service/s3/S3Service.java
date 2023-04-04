package com.lastone.apiserver.service.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    public String upload(MultipartFile multipartFile) throws IOException;

    public void delete(String fileName);
}
