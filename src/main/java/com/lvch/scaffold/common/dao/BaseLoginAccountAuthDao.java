package com.lvch.scaffold.common.dao;

import com.lvch.scaffold.common.domain.entity.BaseLoginAccountAuth;
import com.lvch.scaffold.common.mapper.BaseLoginAccountAuthMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录鉴权表 服务实现类
 * </p>
 *
 * @author April
 * @since 2023-10-13
 */
@Service
public class BaseLoginAccountAuthDao extends ServiceImpl<BaseLoginAccountAuthMapper, BaseLoginAccountAuth> {

    public Integer findCountByLoginId(String loginId) {
        return lambdaQuery()
                .eq(BaseLoginAccountAuth::getLoginId, loginId)
                .count();
    }

    public BaseLoginAccountAuth findByLoginId(String loginId) {
        return lambdaQuery()
                .eq(BaseLoginAccountAuth::getLoginId, loginId)
                .one();
    }

    public Boolean updatePassword(String newPassword, String loginId) {
        return lambdaUpdate()
                .set(BaseLoginAccountAuth::getPassword, newPassword)
                .eq(BaseLoginAccountAuth::getLoginId, loginId)
                .update();
    }

}
