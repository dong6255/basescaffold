package com.lvch.scaffold.common.dao;

import com.lvch.scaffold.common.domain.entity.BaseUserProfile;
import com.lvch.scaffold.common.mapper.BaseUserProfileMapper;
import com.lvch.scaffold.common.service.IBaseUserProfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户详细信息表 服务实现类
 * </p>
 *
 * @author April
 * @since 2023-10-13
 */
@Service
public class BaseUserProfileDao extends ServiceImpl<BaseUserProfileMapper, BaseUserProfile>{

}
