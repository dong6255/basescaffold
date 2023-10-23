package com.lvch.scaffold.common.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author chunhelv
 * @date 2023-10-23
 * @apiNote
 */
@SuperBuilder
@Data
public class BaseEntity {

    /**
     * 删除标志	            0 - 正常	            1 - 删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private Integer delFlag;

    /**
     * 版本号
     */
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 创建人	            登录帐号
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改人	            登录的帐号
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT)
    private String updatedBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.INSERT)
    private LocalDateTime updateDate;


}
