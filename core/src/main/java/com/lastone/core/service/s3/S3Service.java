package com.lastone.core.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String upload(MultipartFile multipartFile) throws IOException {
        validateFileExist(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();
        String extension = "";
        if (originalFilename != null) {
            int startPoint = originalFilename.lastIndexOf('.');
            extension = originalFilename.substring(startPoint);
        }
        String s3FileName = UUID.randomUUID() + extension;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) { //  try() 안에 넣음으로 inputstream close를 따로 해주지 않아도 된다.
            objectMetadata.setContentLength(inputStream.available());
            amazonS3.putObject(new PutObjectRequest(bucket, s3FileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadException(); // todo custom 예외
        }
        return s3FileName;
    }

    public void delete(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
    }

    private void validateFileExist(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException(); // todo 예외 생성 EmptyFile
        }
    }
}
