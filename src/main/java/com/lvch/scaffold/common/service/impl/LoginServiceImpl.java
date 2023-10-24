package com.lvch.scaffold.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lvch.scaffold.common.constant.RedisKey;
import com.lvch.scaffold.common.dao.BaseLoginAccountAuthDao;
import com.lvch.scaffold.common.dao.BaseUserAccountDao;
import com.lvch.scaffold.common.dao.BaseUserProfileDao;
import com.lvch.scaffold.common.domain.entity.BaseLoginAccountAuth;
import com.lvch.scaffold.common.domain.entity.BaseUserAccount;
import com.lvch.scaffold.common.domain.entity.BaseUserProfile;
import com.lvch.scaffold.common.domain.vo.request.RegisterRequest;
import com.lvch.scaffold.common.domain.vo.response.ApiResult;
import com.lvch.scaffold.common.service.IBaseLoginAccountAuthService;
import com.lvch.scaffold.common.utils.AssertUtil;
import com.lvch.scaffold.common.utils.JwtUtils;
import com.lvch.scaffold.common.utils.RedisUtils;
import com.lvch.scaffold.common.service.LoginService;
import com.lvch.scaffold.common.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Description: 登录相关处理类
 * Author: April
 * Date: 2023-03-19
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BaseLoginAccountAuthDao loginAccountAuthDao;

    @Autowired
    private BaseUserProfileDao userProfileDao;

    @Autowired
    private BaseUserAccountDao userAccountDao;
    @Autowired
    private IBaseLoginAccountAuthService loginAccountAuthService;
    //token过期时间
    private static final Integer TOKEN_EXPIRE_DAYS = 5;
    //token续期时间
    private static final Integer TOKEN_RENEWAL_DAYS = 2;

    /**
     * 校验token是不是有效
     *
     * @param token
     * @return
     */
    @Override
    public boolean verify(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return false;
        }
        String key = RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
        String realToken = RedisUtils.getStr(key);
        return Objects.equals(token, realToken);//有可能token失效了，需要校验是不是和最新token一致
    }

    @Async
    @Override
    public void renewalTokenIfNecessary(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return;
        }
        String key = RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
        long expireDays = RedisUtils.getExpire(key, TimeUnit.DAYS);
        if (expireDays == -2) {//不存在的key
            return;
        }
        if (expireDays < TOKEN_RENEWAL_DAYS) {//小于一天的token帮忙续期
            RedisUtils.expire(key, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    @Override
    public String login(Long uid) {
        String key = RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
        String token = RedisUtils.getStr(key);
        if (StrUtil.isNotBlank(token)) {
            return token;
        }
        //获取用户token
        token = jwtUtils.createToken(uid);
        RedisUtils.set(key, token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);//token过期用redis中心化控制，初期采用5天过期，剩1天自动续期的方案。后续可以用双token实现
        return token;
    }

    @Override
    public Long getValidUid(String token) {
        boolean verify = verify(token);
        return verify ? jwtUtils.getUidOrNull(token) : null;
    }

    @Override
    public ApiResult<Object> register(RegisterRequest registerRequest) {
        String userAccountId = UUID.randomUUID().toString().replace("-", "");
        try {
            // 1.判断登录ID（登录账号）是否存在
            String loginId = registerRequest.getAccount();
            AssertUtil.equal(loginAccountAuthDao.findCountByLoginId(loginId), 0, "账号已经被使用,请重新输入!！");

            // 2.添加账户数据
            BaseUserAccount userAccountDTO = BaseUserAccount.builder().accountId(userAccountId).build();
            boolean addUserAccountFlag = userAccountDao.save(userAccountDTO);
            AssertUtil.isTrue(addUserAccountFlag, "新增失败");
            Long userAccountSid = userAccountDTO.getSid();
            //
            //// 3.添加用户基础信息
            //BaseUserProfile userProfileDTO = BaseUserProfile.builder().mobilePhone(loginId).userAccountSid(userAccountSid).userName(loginId).build();
            //boolean addUserProfileFlag = userProfileDao.save(userProfileDTO);
            //AssertUtil.isTrue(addUserProfileFlag, "新增失败");
            ////Long userAccountSid = userAccountDTO.getSid();
            //
            // 4.添加用户登录鉴权信息
            BaseLoginAccountAuth loginAccountAuthDTO = BaseLoginAccountAuth.builder().userAccountSid(userAccountSid).loginId(loginId).loginType(registerRequest.getLoginType()).loginStatus(0).password(registerRequest.getPassword()).build();
            boolean addLoginAccountAuthFlag = loginAccountAuthDao.save(loginAccountAuthDTO);
            AssertUtil.isTrue(addLoginAccountAuthFlag, "新增失败");

        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(500, "系统异常");
        }
        return ApiResult.success();
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
