package com.lvch.scaffold.common.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.lvch.scaffold.common.domain.entity.Message;
import com.lvch.scaffold.common.domain.enums.MessageStatusEnum;
import com.lvch.scaffold.common.domain.vo.request.ChatMessageReq;
import com.lvch.scaffold.common.domain.vo.response.ChatMessageResp;
import com.lvch.scaffold.common.domain.vo.response.TextMsgResp;
import com.lvch.scaffold.common.service.cache.MsgCache;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Description: 消息适配器
 * Author:April
 * Date: 2023-03-26
 */
public class MessageAdapter {
    public static final int CAN_CALLBACK_GAP_COUNT = 100;
    @Autowired
    private MsgCache msgCache;
    public static Message buildMsgSave(ChatMessageReq request, Long uid) {

        return Message.builder()
                .fromUid(uid)
                .roomId(request.getRoomId())
                .type(request.getMsgType())
                .content(request.getBody().getContent())
                .status(MessageStatusEnum.NORMAL.getStatus())
                .build();

    }

    public static List<ChatMessageResp> buildMsgResp(List<Message> messages, List<Object> msgMark, Long receiveUid) {
        //Map<Long, List<MessageMark>> markMap = msgMark.stream().collect(Collectors.groupingBy(MessageMark::getMsgId));
        return messages.stream().map(a -> {
            ChatMessageResp resp = new ChatMessageResp();
            resp.setFromUser(buildFromUser(a.getFromUid()));
            resp.setMessage(buildMessage(a,  new ArrayList<>(), receiveUid));
            return resp;
        })
                .sorted(Comparator.comparing(a -> a.getMessage().getSendTime()))//帮前端排好序，更方便它展示
                .collect(Collectors.toList());
    }

    private static ChatMessageResp.Message buildMessage(Message message, List<Object> marks, Long receiveUid) {
        ChatMessageResp.Message messageVO = new ChatMessageResp.Message();
        BeanUtil.copyProperties(message, messageVO);
        messageVO.setSendTime(message.getCreateTime());
        TextMsgResp resp = new TextMsgResp();
        resp.setContent(message.getContent());
        resp.setUrlContentMap(null);
        resp.setAtUidList(null);
        messageVO.setBody(resp);
        //消息标记
        return messageVO;
    }


    private static ChatMessageResp.UserInfo buildFromUser(Long fromUid) {
        ChatMessageResp.UserInfo userInfo = new ChatMessageResp.UserInfo();
        userInfo.setUid(fromUid);
        return userInfo;
    }

}
