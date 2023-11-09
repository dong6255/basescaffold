package com.lvch.scaffold.common.service;



import com.lvch.scaffold.common.domain.entity.User;
import com.lvch.scaffold.common.domain.vo.response.user.UserInfoResp;

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
     * 获取前端展示信息
     *
     * @param uid
     * @return
     */
    UserInfoResp getUserInfo(Long uid);

    /**
     * 用户注册，需要获得id
     *
     * @param user
     */
    void register(User user);

}
