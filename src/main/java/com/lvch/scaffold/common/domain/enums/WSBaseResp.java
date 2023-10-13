package com.lvch.scaffold.common.domain.enums;

import lombok.Data;

/**
 * Description: ws的基本返回信息体
 * Author: April
 * Date: 2023-03-19
 */
@Data
public class WSBaseResp<T> {
    /**
     * ws推送给前端的消息
     *
     * @see WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
