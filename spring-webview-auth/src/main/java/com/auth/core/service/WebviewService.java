package com.auth.core.service;

import com.auth.core.config.ServiceCacheConfig;
import com.auth.core.utils.FolderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebviewService {

    private final ServiceCacheConfig serviceCacheConfig;

    //    ******************************************************************************************************************

    public void setValueModel(Model model) {
        model.addAttribute("UrlPath", serviceCacheConfig.getUrlApiBackEnd());
    }

    public void saveFile(MultipartFile file) {
        try {
            log.info(FolderUtils.saveFile(file, serviceCacheConfig.getDestinationFolderSaveFile()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
