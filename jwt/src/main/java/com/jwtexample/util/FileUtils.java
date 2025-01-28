package com.jwtexample.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static byte[] getFileContent(String fileName) throws IOException {
        if (StringUtils.isEmpty(fileName)) {
            throw new IOException("File name is empty");
        }
        if (fileName.startsWith("classpath")) {
            File file = ResourceUtils.getFile(fileName);
            InputStream in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } else {
            try (FileReader fr = new FileReader(fileName)) {
                return IOUtils.toByteArray(fr, StandardCharsets.UTF_8);
            }
        }
    }

    public static String getFileContentAsString(String fileName) throws IOException {
        if (StringUtils.isEmpty(fileName)) {
            throw new IOException("File name is empty");
        }
        if (fileName.startsWith("classpath")) {
            File file = ResourceUtils.getFile(fileName);
            InputStream in = new FileInputStream(file);
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        } else {
            try (FileReader fr = new FileReader(fileName, StandardCharsets.UTF_8)) {
                return IOUtils.toString(fr);
            }
        }
    }
}
