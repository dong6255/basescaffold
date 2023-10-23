package com.lvch.scaffold.common.service;

import com.lvch.scaffold.common.domain.entity.BaseLoginAccountAuth;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户登录鉴权表 服务类
 * </p>
 *
 * @author April
 * @since 2023-10-13
 */
public interface IBaseLoginAccountAuthService{

    void findByLoginId(String loginId);

}
