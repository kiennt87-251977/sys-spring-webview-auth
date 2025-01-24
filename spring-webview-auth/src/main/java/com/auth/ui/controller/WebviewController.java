package com.auth.ui.controller;

import com.auth.core.service.WebviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebviewController {

    private final WebviewService service;

    @GetMapping("/login")
    public String login(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        log.info("Session login open");
        service.setValueModel(model);
        return "login";
    }


    @GetMapping("/index")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        log.info("Session index open");
        service.setValueModel(model);
        return "index";
    }


    @GetMapping("/menu")
    public String menu(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        log.info("Session menu open");
        service.setValueModel(model);
        return "menu";
    }


    @GetMapping("/webview_acc_chart")
    public String accChart(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        log.info("Session acc_chart open");
        service.setValueModel(model);
        return "acc_chart";
    }


    @GetMapping("/webview_acc_table")
    public String accTable(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        log.info("Session acc_table open");
        service.setValueModel(model);
        return "acc_table";
    }


    @GetMapping("/webview_pdf_view")
    public String pdfView(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        log.info("Session pdf_view open");
        service.setValueModel(model);
        return "pdf_view";
    }

    @GetMapping("/webview_file_view")
    public String fileView(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        log.info("Session file_view open");
        service.setValueModel(model);
        return "file_view";
    }


//    @PostMapping(value = "/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public String uploadFileAll(@RequestParam("filename") MultipartFile file
//            , Model model
//    ) {
//        log.info("Session uploadFile all open");
//        service.saveFile(file);
//        service.setValueModel(model);
//        return "file_view";
//    }

}
