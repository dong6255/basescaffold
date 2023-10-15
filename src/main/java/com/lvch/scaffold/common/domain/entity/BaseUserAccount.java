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
 * 用户表
 * </p>
 *
 * @author April
 * @since 2023-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_user_account")
public class BaseUserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sid", type = IdType.AUTO)
    private Long sid;

    /**
     * 用户账号ID，业务主键，默认生成规则：UUID
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 账户状态（暂时没用）
     */
    @TableField("account_status")
    private Integer accountStatus;

    @TableField("secret_key")
    private String secretKey;

    /**
     * 账号类型 0普通账号 1-访客账号
     */
    @TableField("account_type")
    private Integer accountType;

    /**
     * 删除标志 0 - 正常 1 - 删除
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
