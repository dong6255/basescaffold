package com.lvch.scaffold.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunhelv
 * @date 2023-08-09
 * @apiNote
 */
@RestController
//@Slf4j
public class ExportReportController {


    @GetMapping("/test")
    public String appExportReport() {
        return "success!";
    }

    @GetMapping("/testtttt")
    public String test() {
        return "===============success!============";
    }

}
