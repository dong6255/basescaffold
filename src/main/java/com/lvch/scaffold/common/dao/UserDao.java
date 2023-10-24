package com.lvch.scaffold.common.dao;

import cn.hutool.core.collection.CollectionUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvch.scaffold.common.domain.entity.User;
import com.lvch.scaffold.common.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author April
 * @since 2023-03-19
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getByOpenId(String openId) {
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda().eq(User::getOpenId, openId);
        return getOne(wrapper);
    }



    public User getByName(String name) {
        return lambdaQuery().eq(User::getName, name).one();
    }


}
