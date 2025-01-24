package com.auth.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Slf4j
public class FolderUtils {
    private static final String PATH_SEPARATOR = "/";
    private static final String WINDOWS_PATH_SEPARATOR = "\\";


    private static String normalizePath(String individualPath) {
        if (individualPath == null || StringUtils.isBlank(individualPath)) {
            return "";
        }

        String res = individualPath.toLowerCase().trim();
        String doubleSep = String.format("%s%s", PATH_SEPARATOR, PATH_SEPARATOR);
        while (res.contains(WINDOWS_PATH_SEPARATOR)) {
            res = res.replace(WINDOWS_PATH_SEPARATOR, PATH_SEPARATOR);
        }
        while (res.contains(doubleSep)) {
            res = res.replace(doubleSep, PATH_SEPARATOR);
        }

        if (res.endsWith(PATH_SEPARATOR)) {
            res = res.substring(0, res.length() - 1);
        }
        if (res.startsWith(PATH_SEPARATOR)) {
            res = res.substring(1);
        }

        return res;
    }


    public static String saveFile(MultipartFile file, String destinationFolder) throws Exception {
        destinationFolder = FolderUtils.normalizePath(destinationFolder);
        if (!"".equals(destinationFolder)
                && !"".equals(file.getOriginalFilename())
        ) {
            Path destFolderPath = Paths.get(destinationFolder);
            if (!Files.exists(destFolderPath)) {
                try {
                    Files.createDirectories(destFolderPath);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    throw e;
                }
            }

            try {
                String pathFile = destinationFolder + PATH_SEPARATOR
                        + DateUtilsV8.convertDateToString(new Date(), DateUtilsV8.regex41) + "_" + file.getOriginalFilename();
                OutputStream out = new FileOutputStream(new File(pathFile));
                out.write(file.getBytes());
                out.close();
                return pathFile;
            } catch (IOException e) {
                throw e;
            }
        } else {
            throw new Exception("Path file or name file is null !!!");
        }

    }
}
