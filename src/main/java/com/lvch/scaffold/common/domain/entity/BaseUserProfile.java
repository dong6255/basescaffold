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
 * 用户详细信息表
 * </p>
 *
 * @author April
 * @since 2023-10-13
 */

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_user_profile")
public class BaseUserProfile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID  即SID
     */
    @TableId(value = "sid", type = IdType.AUTO)
    private Long sid;

    /**
     * 用户账户sid
     */
    @TableField("user_account_sid")
    private Long userAccountSid;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户级别
     */
    @TableField("user_level")
    private Integer userLevel;

    /**
     * 性别
     */
    @TableField("sexuality")
    private Integer sexuality;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 证件类型
     */
    @TableField("id_type")
    private Integer idType;

    /**
     * 邮件
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     */
    @TableField("mobile_phone")
    private String mobilePhone;

    /**
     * 证件号码
     */
    @TableField("id_no")
    private String idNo;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 生日
     */
    @TableField("birthday")
    private String birthday;


}
