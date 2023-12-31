package com.lvch.scaffold.common.controller;

import com.lvch.scaffold.common.domain.vo.request.ChangePasswordRequest;
import com.lvch.scaffold.common.domain.vo.request.RegisterRequest;
import com.lvch.scaffold.common.domain.vo.response.ApiResult;
import com.lvch.scaffold.common.service.LoginService;
import com.lvch.scaffold.common.utils.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chunhelv
 * @date 2023-08-09
 * @apiNote
 */
@Api(tags = "首页模块")
@RestController
@Slf4j
@RequestMapping("welcome/test")
public class WelcomeController {

    @Autowired
    private LoginService loginService;

    @ApiImplicitParam(name = "name", value = "姓名", required = true)
    @ApiOperation(value = "向客人问好")
    @GetMapping("/public/sayHi")
    public ApiResult<String> sayHi(@RequestParam(value = "name") String name) {
        log.info("hi, {}", name);
        return ApiResult.success("Hi:" + name);
    }

    @ApiImplicitParam(name = "uid", value = "用户id", required = true)
    @ApiOperation(value = "测试获取token")
    @GetMapping("/public/getToken")
    public ApiResult<String> getToken(@RequestParam(value = "uid") Long uid) {
        String token = loginService.login(uid);
        return ApiResult.success(token);
    }

    @ApiImplicitParam(name = "token", value = "token", required = true)
    @ApiOperation(value = "测试验证token并返回uid")
    @GetMapping("/public/getValidUid")
    public ApiResult<Long> getValidUid(@RequestParam(value = "token") String token) {
        Long uid = loginService.getValidUid(token);
        AssertUtil.isNotEmpty(uid, "你谁啊？");
        return ApiResult.success(uid);
    }

    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", httpMethod = "POST", response = ApiResult.class, notes = "用户注册")
    public ApiResult<Object> userRegister(
            @ApiParam(required = true, name = "registerRequest", value = "用户注册数据") @RequestBody RegisterRequest req,
            HttpServletRequest request) {
        return loginService.register(req);
    }

    @RequestMapping(value = "/userChangePassword", method = RequestMethod.POST)
    @ApiOperation(value = "用户修改密码", httpMethod = "POST", response = ApiResult.class, notes = "用户修改密码")
    public ApiResult<Object> userChangePassword(
            @ApiParam(required = true, name = "changePasswordRequest", value = "用户修改密码数据") @RequestBody ChangePasswordRequest req,
            HttpServletRequest request) {
        return loginService.changePassword(req);
    }

}
