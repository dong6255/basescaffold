package com.lvch.scaffold.common.dao;

import com.lvch.scaffold.common.domain.entity.BaseUserAccount;
import com.lvch.scaffold.common.mapper.BaseUserAccountMapper;
import com.lvch.scaffold.common.service.IBaseUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author April
 * @since 2023-10-13
 */
@Service
public class BaseUserAccountDao extends ServiceImpl<BaseUserAccountMapper, BaseUserAccount>{

}
