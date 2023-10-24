package com.lvch.scaffold.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lvch.scaffold.common.dao.UserDao;
import com.lvch.scaffold.common.domain.entity.User;
import com.lvch.scaffold.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 用户基础操作类
 * Author: April
 * Date: 2023-03-19
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void register(User user) {

        //todo 这里的用户表还没和登录账户表关联在一起，需要修改
        userDao.save(user);
    }

}
