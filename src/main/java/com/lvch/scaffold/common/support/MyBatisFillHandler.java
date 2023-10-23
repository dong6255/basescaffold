package com.lvch.scaffold.common.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.StrictFill;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;


/**
 * TITLE 自动填充
 *
 * @author April
 * @date 2023-10-23 20:57
 * @description MybatisPlus自定义填充公共字段 ,即没有传的字段自动填充
 */
@Component
public class MyBatisFillHandler implements MetaObjectHandler {

    /**
     * 新增时自动填充字段
     *
     * @param metaObject 源对象
     * @author April
     * @date 2023-10-23 20:57
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createdBy", String.class, "admin");
        this.strictInsertFill(metaObject, "createDate", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updatedBy", String.class, "admin");
        this.strictInsertFill(metaObject, "updateDate", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "delFlag", Integer.class, 0);
        this.strictInsertFill(metaObject, "version", Integer.class, 1);

    }

    /**
     * 更新时自动填充字段
     *
     * @param metaObject 源对象
     * @author April
     * @date 2023-10-23 20:57
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updatedBy", String.class, "admin");
        this.strictUpdateFill(metaObject, "updatedDate", LocalDateTime.class, now);
    }

    /**
     * 重写填充方法，字段有值时也填充
     *
     * @param insertFill
     * @param tableInfo
     * @param metaObject
     * @param strictFills
     * @author April
     * @date 2023-10-23 20:57
     */
    @Override
    public MetaObjectHandler strictFill(boolean insertFill, TableInfo tableInfo, MetaObject metaObject, List<StrictFill<?, ?>> strictFills) {
        boolean strictFlag = (insertFill && tableInfo.isWithInsertFill()) || (!insertFill && tableInfo.isWithUpdateFill());
        if (strictFlag) {
            strictFills.forEach((i) -> {
                String fieldName = i.getFieldName();
                Class<?> fieldType = i.getFieldType();
                Stream<TableFieldInfo> stream = tableInfo.getFieldList().stream()
                        .filter((j) -> j.getProperty().equals(fieldName) && fieldType.equals(j.getPropertyType()) && (insertFill && j.isWithInsertFill() || !insertFill && j.isWithUpdateFill()));
                stream.findFirst().ifPresent((j) -> {
                    if (!insertFill && j.isWithUpdateFill()) {
                        metaObject.setValue(fieldName, null);
                    }
                    this.strictFillStrategy(metaObject, fieldName, i.getFieldVal());
                });
            });
        }
        return this;
    }
}