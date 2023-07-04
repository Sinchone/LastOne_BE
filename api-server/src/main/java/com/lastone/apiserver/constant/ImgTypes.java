package com.lastone.apiserver.constant;

import com.lastone.apiserver.exception.global.ImgTypeNotSupportedException;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

@Slf4j
public enum ImgTypes {

    JPG("jpg"),

    JPEG("jpeg"),

    PNG("png"),

    GIF("gif"),

    BMP("bmp");

    private final String name;

    ImgTypes(String type) {
        this.name = type;
    }

    public static void isSupport(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        boolean isSupported = Arrays.stream(ImgTypes.values()).anyMatch(imgTypes -> extension.equalsIgnoreCase(imgTypes.name));
        if (!isSupported) {
            throw new ImgTypeNotSupportedException();
        }
    }
}