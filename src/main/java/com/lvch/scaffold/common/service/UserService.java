package com.lvch.scaffold.common.service;



import com.lvch.scaffold.common.domain.entity.User;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author April
 * @since 2023-03-19
 */
public interface UserService {

    /**
     * 用户注册，需要获得id
     *
     * @param user
     */
    void register(User user);

}
