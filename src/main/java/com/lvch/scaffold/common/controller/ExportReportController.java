package com.lvch.scaffold.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunhelv
 * @date 2023-08-09
 * @apiNote
 */
@RestController
@Slf4j
public class ExportReportController {


    @GetMapping("/test")
    public String appExportReport() {
        log.error("xxxxxxxxxxx");

        return "success!";
    }

    @GetMapping("/testtttt")
    public String test() {
        log.info("ccccccccccc");

        return "===============success!============";
    }

}
