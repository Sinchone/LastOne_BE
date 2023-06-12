package com.lastone.apiserver.constant;

import com.lastone.apiserver.exception.global.ImgTypeNotSupportedException;
import java.util.Arrays;

public enum ImgTypes {

    TYPE1("jpg"),

    TYPE2("jpeg"),

    TYPE3("png"),

    TYPE4("gif"),

    TYPE5("bmp");

    private final String type;

    ImgTypes(String type) {
        this.type = type;
    }

    public static void isSupport(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        boolean isSupported = Arrays.stream(ImgTypes.values()).anyMatch(type -> extension.equalsIgnoreCase(type.name()));
        if (!isSupported) {
            throw new ImgTypeNotSupportedException();
        }
    }
}