package com.lvch.scaffold.common.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Pair;

import com.lvch.scaffold.common.dao.MessageDao;
import com.lvch.scaffold.common.dao.UserDao;
import com.lvch.scaffold.common.domain.entity.Message;
import com.lvch.scaffold.common.domain.entity.Room;
import com.lvch.scaffold.common.domain.vo.request.ChatMessagePageReq;
import com.lvch.scaffold.common.domain.vo.request.ChatMessageReq;
import com.lvch.scaffold.common.domain.vo.response.ChatMessageResp;
import com.lvch.scaffold.common.domain.vo.response.CursorPageBaseResp;
import com.lvch.scaffold.common.event.MessageSendEvent;
import com.lvch.scaffold.common.service.ChatService;
import com.lvch.scaffold.common.service.adapter.MessageAdapter;
import com.lvch.scaffold.common.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description: 消息处理类
 * Author: April
 * Date: 2023-03-26
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    public static final long ROOM_GROUP_ID = 1L;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 发送消息
     */
    @Override
    @Transactional
    public Long sendMsg(ChatMessageReq request, Long uid) {
        //check(request, uid);
        Message insert = MessageAdapter.buildMsgSave(request, uid);
        messageDao.save(insert);
        //发布消息发送事件
        applicationEventPublisher.publishEvent(new MessageSendEvent(this, insert.getId()));
        return insert.getId();
    }


    @Override
    public ChatMessageResp getMsgResp(Long msgId, Long receiveUid) {
        Message msg = messageDao.getById(msgId);
        return getMsgResp(msg, receiveUid);
    }

    @Override
    public ChatMessageResp getMsgResp(Message message, Long receiveUid) {
        return CollUtil.getFirst(getMsgRespBatch(Collections.singletonList(message), receiveUid));
    }

    @Override
    public CursorPageBaseResp<ChatMessageResp> getMsgPage(ChatMessagePageReq request, Long receiveUid) {
        //用最后一条消息id，来限制被踢出的人能看见的最大一条消息
        //Long lastMsgId = getLastMsgId(request.getRoomId(), receiveUid);
        CursorPageBaseResp<Message> cursorPage = messageDao.getCursorPage(request.getRoomId(), request, null);
        if (cursorPage.isEmpty()) {
            return CursorPageBaseResp.empty();
        }
        return CursorPageBaseResp.init(cursorPage, getMsgRespBatch(cursorPage.getList(), receiveUid));
    }

    //private Long getLastMsgId(Long roomId, Long receiveUid) {
    //    Room room = roomCache.get(roomId);
    //    AssertUtil.isNotEmpty(room, "房间号有误");
    //    if (room.isHotRoom()) {
    //        return null;
    //    }
    //    AssertUtil.isNotEmpty(receiveUid, "请先登录");
    //    Contact contact = contactDao.get(receiveUid, roomId);
    //    return contact.getLastMsgId();
    //}

    public List<ChatMessageResp> getMsgRespBatch(List<Message> messages, Long receiveUid) {
        if (CollectionUtil.isEmpty(messages)) {
            return new ArrayList<>();
        }
        //查询消息标志
        //List<MessageMark> msgMark = messageMarkDao.getValidMarkByMsgIdBatch(messages.stream().map(Message::getId).collect(Collectors.toList()));
        return MessageAdapter.buildMsgResp(messages, new ArrayList<>(), receiveUid);
    }



}
