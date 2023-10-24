package com.lvch.scaffold.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.lvch.scaffold.common.common.websocket.NettyUtil;
import com.lvch.scaffold.common.config.ThreadPoolConfig;
import com.lvch.scaffold.common.constant.RedisKey;
import com.lvch.scaffold.common.domain.dto.WSChannelExtraDTO;
import com.lvch.scaffold.common.domain.enums.WSBaseResp;
import com.lvch.scaffold.common.domain.vo.request.ws.WSAuthorize;
import com.lvch.scaffold.common.service.LoginService;
import com.lvch.scaffold.common.service.WebSocketService;
import com.lvch.scaffold.common.service.adapter.WSAdapter;
import com.lvch.scaffold.common.utils.RedisUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Description: websocket处理类
 * Author: April
 * Date: 2023-03-19 16:21
 */
@Component
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    private static final Duration EXPIRE_TIME = Duration.ofHours(1);
    private static final Long MAX_MUM_SIZE = 10000L;
    /**
     * 所有请求登录的code与channel关系
     */
    public static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .expireAfterWrite(EXPIRE_TIME)
            .maximumSize(MAX_MUM_SIZE)
            .build();
    /**
     * 所有已连接的websocket连接列表和一些额外参数
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();
    /**
     * 所有在线的用户和对应的socket
     */
    private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Channel>> ONLINE_UID_MAP = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Channel, WSChannelExtraDTO> getOnlineMap() {
        return ONLINE_WS_MAP;
    }

    /**
     * redis保存loginCode的key
     */
    private static final String LOGIN_CODE = "loginCode";
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    @Qualifier(ThreadPoolConfig.WS_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 处理用户登录请求，需要返回一张带code的二维码
     *
     * @param channel
     */
    @SneakyThrows
    @Override
    public void handleLoginReq(Channel channel) {
        //生成随机不重复的登录码,并将channel存在本地cache中
        Integer code = generateLoginCode(channel);
        //请求微信接口，获取登录码地址
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) EXPIRE_TIME.getSeconds());
        //返回给前端（channel必在本地）
        sendMsg(channel, WSAdapter.buildLoginResp(wxMpQrCodeTicket));
    }

    /**
     * 获取不重复的登录的code，微信要求最大不超过int的存储极限
     * 防止并发，可以给方法加上synchronize，也可以使用cas乐观锁
     *
     * @return
     */
    private Integer generateLoginCode(Channel channel) {
        int inc;
        do {
            //本地cache时间必须比redis key过期时间短，否则会出现并发问题
            inc = RedisUtils.integerInc(RedisKey.getKey(LOGIN_CODE), (int) EXPIRE_TIME.toMinutes(), TimeUnit.MINUTES);
        } while (WAIT_LOGIN_MAP.asMap().containsKey(inc));
        //储存一份在本地
        WAIT_LOGIN_MAP.put(inc, channel);
        return inc;
    }



    /**
     * 处理所有ws连接的事件
     *
     * @param channel
     */
    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WSChannelExtraDTO());
    }

    @Override
    public void removed(Channel channel) {
        WSChannelExtraDTO wsChannelExtraDTO = ONLINE_WS_MAP.get(channel);
        Optional<Long> uidOptional = Optional.ofNullable(wsChannelExtraDTO)
                .map(WSChannelExtraDTO::getUid);
        boolean offlineAll = offline(channel, uidOptional);
        if (uidOptional.isPresent() && offlineAll) {//已登录用户断连,并且全下线成功
            //todo 用户下线
        }
    }

    /**
     * 用户下线
     * return 是否全下线成功
     */
    private boolean offline(Channel channel, Optional<Long> uidOptional) {
        ONLINE_WS_MAP.remove(channel);
        //todo 用户下线
        return true;
    }

    //@Override
    //public void authorize(Channel channel, WSAuthorize wsAuthorize) {
    //    //校验token
    //    boolean verifySuccess = loginService.verify(wsAuthorize.getToken());
    //    if (verifySuccess) {//用户校验成功给用户登录
    //        User user = userDao.getById(loginService.getValidUid(wsAuthorize.getToken()));
    //        loginSuccess(channel, user, wsAuthorize.getToken());
    //    } else { //让前端的token失效
    //        sendMsg(channel, WSAdapter.buildInvalidateTokenResp());
    //    }
    //}
    //


    //@Override
    //public Boolean scanLoginSuccess(Integer loginCode, Long uid) {
    //    //确认连接在该机器
    //    Channel channel = WAIT_LOGIN_MAP.getIfPresent(loginCode);
    //    if (Objects.isNull(channel)) {
    //        return Boolean.FALSE;
    //    }
    //    User user = userDao.getById(uid);
    //    //移除code
    //    WAIT_LOGIN_MAP.invalidate(loginCode);
    //    //调用用户登录模块
    //    String token = loginService.login(uid);
    //    //用户登录
    //    loginSuccess(channel, user, token);
    //    return Boolean.TRUE;
    //}
    //
    //@Override
    //public Boolean scanSuccess(Integer loginCode) {
    //    Channel channel = WAIT_LOGIN_MAP.getIfPresent(loginCode);
    //    if (Objects.nonNull(channel)) {
    //        sendMsg(channel, WSAdapter.buildScanSuccessResp());
    //        return Boolean.TRUE;
    //    }
    //    return Boolean.FALSE;
    //}


    /**
     * 给本地channel发送消息
     *
     * @param channel
     * @param wsBaseResp
     */
    private void sendMsg(Channel channel, WSBaseResp<?> wsBaseResp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(wsBaseResp)));
    }

}
