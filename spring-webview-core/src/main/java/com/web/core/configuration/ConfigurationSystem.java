package com.web.core.configuration;




import com.web.core.configuration.utils.ReadPropertiesFiles;

import java.util.Properties;

public class ConfigurationSystem {
    private static String configfileDefault = "config.properties";
    private static String configfileETC = "config_service.properties";
    private static String configfileAllServiceETC = "config_all_service.properties";


    public static String pathEtcFolder;
    public static String pathEtcAllServicesFolder;

    public static String regexPhoneNumb;


    static {
        setConfig();
    }

    public static void setConfig() {
//        setConfigFileDefautl();
//        setConfigFileETC();
//        setConfigFileETCAllService();
    }

    private static void setConfigFileDefautl() {
        Properties prop = ReadPropertiesFiles.readFileResource(configfileDefault);

        pathEtcFolder = prop.getProperty("path_etc_folder").trim();
        pathEtcAllServicesFolder = prop.getProperty("path_etc_all_service_folder").trim();
    }

    private static void setConfigFileETC() {
        Properties prop = ReadPropertiesFiles.readFileResourceETC(pathEtcFolder + configfileETC);

//        urlApiQueryDB = prop.getProperty("url.api.query.db").trim();
//        regexPhoneNumb = prop.getProperty("phone.number.regex").trim();
    }

    private static void setConfigFileETCAllService() {
        Properties prop = ReadPropertiesFiles.readFileResourceETC(pathEtcAllServicesFolder + configfileAllServiceETC);

//        urlApiQueryDB = prop.getProperty("url.api.query.db").trim();
//        regexPhoneNumb = prop.getProperty("phone.number.regex").trim();
    }


}
