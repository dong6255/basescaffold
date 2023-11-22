package com.lvch.scaffold.common.service;



import com.lvch.scaffold.common.domain.entity.Message;
import com.lvch.scaffold.common.domain.vo.request.ChatMessagePageReq;
import com.lvch.scaffold.common.domain.vo.request.ChatMessageReq;
import com.lvch.scaffold.common.domain.vo.response.ChatMessageResp;
import com.lvch.scaffold.common.domain.vo.response.CursorPageBaseResp;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * Description: 消息处理类
 * Author: April
 * Date: 2023-03-26
 */
public interface ChatService {

    /**
     * 发送消息
     *
     * @param request
     */
    Long sendMsg(ChatMessageReq request, Long uid);

    /**
     * 根据消息获取消息前端展示的物料
     *
     * @param msgId
     * @param receiveUid 接受消息的uid，可null
     * @return
     */
    ChatMessageResp getMsgResp(Long msgId, Long receiveUid);

    /**
     * 根据消息获取消息前端展示的物料
     *
     * @param message
     * @param receiveUid 接受消息的uid，可null
     * @return
     */
    ChatMessageResp getMsgResp(Message message, Long receiveUid);



    /**
     * 获取消息列表
     *
     * @param request
     * @return
     */
    CursorPageBaseResp<ChatMessageResp> getMsgPage(ChatMessagePageReq request, @Nullable Long receiveUid);

}
