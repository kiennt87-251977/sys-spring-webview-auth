package com.auth.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class ServiceCacheConfig {

    @Value("${url.api.path}")
    protected String UrlApiPath;

    @Value("${url.api.backend}")
    protected String UrlApiBackEnd;


    @Value("${service.file.save.path}")
    protected String destinationFolderSaveFile;

}
