package com.lvch.scaffold.common.event.listener;


import com.lvch.scaffold.common.dao.MessageDao;
import com.lvch.scaffold.common.dao.RoomDao;
import com.lvch.scaffold.common.domain.dto.PushMessageDTO;
import com.lvch.scaffold.common.domain.entity.Message;
import com.lvch.scaffold.common.domain.entity.Room;
import com.lvch.scaffold.common.domain.enums.HotFlagEnum;
import com.lvch.scaffold.common.domain.enums.WSBaseResp;
import com.lvch.scaffold.common.domain.enums.WSPushTypeEnum;
import com.lvch.scaffold.common.domain.vo.response.ChatMessageResp;
import com.lvch.scaffold.common.event.MessageSendEvent;
import com.lvch.scaffold.common.service.ChatService;
import com.lvch.scaffold.common.service.WebSocketService;
import com.lvch.scaffold.common.service.adapter.WSAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;

/**
 * 消息发送监听器
 *
 */
@Slf4j
@Component
public class MessageSendListener {
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageDao messageDao;


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEvent.class, fallbackExecution = true)
    public void messageRoute(MessageSendEvent event) {
        Long msgId = event.getMsgId();
        //mqProducer.sendSecureMsg(MQConstant.SEND_MSG_TOPIC, new MsgSendMessageDTO(msgId), msgId);
        Message message = messageDao.getById(msgId);
        ChatMessageResp msgResp = chatService.getMsgResp(message, null);
        onMessage(WSAdapter.buildMsgSend(msgResp));
    }

    public void onMessage(WSBaseResp<ChatMessageResp> message) {
        WSPushTypeEnum wsPushTypeEnum = WSPushTypeEnum.of(2);
        switch (wsPushTypeEnum) {
            //case USER:
            //    message.getUidList().forEach(uid -> {
            //        webSocketService.sendToUid(message.getWsBaseMsg(), uid);
            //    });
            //    break;
            case ALL:
                webSocketService.sendToAllOnline(message, null);
                break;
            default:break;
        }
    }

    public boolean isHotRoom(Room room) {
        return Objects.equals(HotFlagEnum.YES.getType(), room.getHotFlag());
    }

}
