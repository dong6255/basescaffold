
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
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TITLE 用户修改密码数据
 *
 * @author Zcb
 * @date 2023-10-27 15:17
 * @description
 */
@ApiModel(value = "用户修改密码数据")
@Data
public class ChangePasswordRequest {

    @NotBlank
    @ApiModelProperty(value = "登陆账户/手机号")
    private String account;

    @NotBlank
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @NotBlank
    @ApiModelProperty(value = "新密码")
    private String newPassword;


}
