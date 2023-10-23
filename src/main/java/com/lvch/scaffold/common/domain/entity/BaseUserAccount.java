package com.lvch.scaffold.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author April
 * @since 2023-10-13
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_user_account")
public class BaseUserAccount extends BaseEntity implements Serializable {

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


}
