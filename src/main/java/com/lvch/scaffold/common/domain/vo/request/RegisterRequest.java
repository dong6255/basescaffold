
/**
 * @Title: RegisterRequest.java
 * @Package com.pateo.qingcloud.base.vo.business
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hongliangzhu@pateo.com.cn
 * @date 2017年3月3日
 * @version V1.0
 */

package com.lvch.scaffold.common.domain.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * TITLE 用户注册数据
 *
 * @author April
 * @date 2023-10-23 20:14
 * @description
 */
@ApiModel(value = "用户注册数据")
public class RegisterRequest {

    @NotBlank
    @ApiModelProperty(value = "登陆账户/手机号")
    private String account;

    @ApiModelProperty(value = "账户登录类型")
    private Integer loginType;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getLoginType() {
        if (loginType == null) {
            return 1;
        }
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
