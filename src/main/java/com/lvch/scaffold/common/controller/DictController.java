package com.lvch.scaffold.common.controller;

import com.lvch.scaffold.common.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chunhelv
 * @date 2023-08-09
 * @apiNote
 */
@Api(tags = "首页模块")
@RestController
@Slf4j
@RequestMapping("welcome")
public class DictController {

    @Autowired
    private LoginService loginService;

    @ApiImplicitParam(name = "name",value = "姓名",required = true)
    @ApiOperation(value = "向客人问好")
    @GetMapping("/sayHi")
    public ResponseEntity<String> sayHi(@RequestParam(value = "name")String name){
        log.info("hi, {}", name);
        return ResponseEntity.ok("Hi:"+name);
    }

    @ApiImplicitParam(name = "uid",value = "用户id",required = true)
    @ApiOperation(value = "测试获取token")
    @GetMapping("/test/getToken")
    public ResponseEntity<String> getToken(@RequestParam(value = "uid")Long uid){
        String token = loginService.login(uid);
        return ResponseEntity.ok(token);
    }

    @ApiImplicitParam(name = "token",value = "token",required = true)
    @ApiOperation(value = "测试验证token并返回uid")
    @GetMapping("/test/getValidUid")
    public ResponseEntity<Long> getValidUid(@RequestParam(value = "token")String token){
        Long uid = loginService.getValidUid(token);
        return ResponseEntity.ok(uid);
    }

}
