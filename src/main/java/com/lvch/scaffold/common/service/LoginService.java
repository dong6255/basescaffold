package com.lvch.scaffold.common.service;

import com.lvch.scaffold.common.domain.vo.request.RegisterRequest;
import com.lvch.scaffold.common.domain.vo.response.ApiResult;

/**
 * Description: 登录相关处理类
 * Author: April
 * Date: 2023-03-19
 */
public interface LoginService {


    /**
     * 校验token是不是有效
     *
     * @param token
     * @return
     */
    boolean verify(String token);

    /**
     * 刷新token有效期
     *
     * @param token
     */
    void renewalTokenIfNecessary(String token);

    /**
     * 登录成功，获取token
     *
     * @param uid
     * @return 返回token
     */
    String login(Long uid);

    /**
     * 如果token有效，返回uid
     *
     * @param token
     * @return
     */
    Long getValidUid(String token);

    ApiResult<Object> register(RegisterRequest registerRequest);

}
