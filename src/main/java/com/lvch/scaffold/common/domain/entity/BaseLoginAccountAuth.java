package com.lvch.scaffold.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户登录鉴权表
 * </p>
 *
 * @author April
 * @since 2023-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_login_account_auth")
public class BaseLoginAccountAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sid", type = IdType.AUTO)
    private Long sid;

    /**
     * 用户uid
     */
    @TableField("user_account_sid")
    private Long userAccountSid;

    /**
     * 登录账户
     */
    @TableField("login_id")
    private String loginId;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 账户登录类型：1-自定义账号+密码；2-手机号+密码；3-手机号+验证码；4-邮箱密码；5-二维码；6-微信openId；
     */
    @TableField("login_type")
    private Integer loginType;

    /**
     * 账户状态（暂时没用）
     */
    @TableField("login_status")
    private Integer loginStatus;

    /**
     * 删除标志	            0 - 正常	            1 - 删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;

    /**
     * 创建人	            登录帐号
     */
    @TableField("created_by")
    private String createdBy;

    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 修改人	            登录的帐号
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField("update_date")
    private LocalDateTime updateDate;

    @TableField("create_by")
    private String createBy;

    @TableField("current_version")
    private String currentVersion;


}
