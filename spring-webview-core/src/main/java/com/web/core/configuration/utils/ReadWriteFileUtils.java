package com.web.core.configuration.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class ReadWriteFileUtils {

    public static void main(String[] args) {

    }


    public static boolean writeObjectFailToFile(String pathFileName, LinkedBlockingQueue listObjectFaile) {
        boolean res = false;
        try {
            FileWriter myWriter = new FileWriter(pathFileName);
            while (listObjectFaile.size() > 0) {
                Map<String, String> listObjectExecute = (Map<String, String>) listObjectFaile.poll();
                for (String key : listObjectExecute.keySet()) {
                    myWriter.write("Error execute business to Data base : " + key + " - {} " + listObjectExecute.get(key)
                            + "\n "
                    );
                }
            }

            myWriter.close();
            res = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}
