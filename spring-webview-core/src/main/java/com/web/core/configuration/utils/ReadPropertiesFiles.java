package com.web.core.configuration.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesFiles {

    public static Properties readFileResource(String nameFilePath) {
        try (InputStream input = ReadPropertiesFiles.class.getClassLoader().getResourceAsStream(nameFilePath)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Properties readFileResourceETC(String nameFilePath) {
        try {
            Properties prop = new Properties();
            InputStream inputStream = new FileInputStream(new File(nameFilePath));
            prop.load(inputStream);
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
