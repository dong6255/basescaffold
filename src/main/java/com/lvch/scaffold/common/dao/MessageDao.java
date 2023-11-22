package com.lvch.scaffold.common.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvch.scaffold.common.domain.entity.Message;
import com.lvch.scaffold.common.domain.enums.MessageStatusEnum;
import com.lvch.scaffold.common.domain.vo.request.CursorPageBaseReq;
import com.lvch.scaffold.common.domain.vo.response.CursorPageBaseResp;
import com.lvch.scaffold.common.mapper.MessageMapper;
import com.lvch.scaffold.common.utils.CursorUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author April
 * @since 2023-03-25
 */
@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

    public CursorPageBaseResp<Message> getCursorPage(Long roomId, CursorPageBaseReq request, Long lastMsgId) {
        return CursorUtils.getCursorPageByMysql(this, request, wrapper -> {
            wrapper.eq(Message::getRoomId, roomId);
            wrapper.eq(Message::getStatus, MessageStatusEnum.NORMAL.getStatus());
            wrapper.le(Objects.nonNull(lastMsgId), Message::getId, lastMsgId);
        }, Message::getId);
    }

    /**
     * 乐观更新消息类型
     */
    public boolean riseOptimistic(Long id, Integer oldType, Integer newType) {
        return lambdaUpdate()
                .eq(Message::getId, id)
                .eq(Message::getType, oldType)
                .set(Message::getType, newType)
                .update();
    }

    public Integer getGapCount(Long roomId, Long fromId, Long toId) {
        return lambdaQuery()
                .eq(Message::getRoomId, roomId)
                .gt(Message::getId, fromId)
                .le(Message::getId, toId)
                .count();
    }

    public void invalidByUid(Long uid) {
        lambdaUpdate()
                .eq(Message::getFromUid, uid)
                .set(Message::getStatus, MessageStatusEnum.DELETE.getStatus())
                .update();
    }

    public Integer getUnReadCount(Long roomId, Date readTime) {
        return lambdaQuery()
                .eq(Message::getRoomId, roomId)
                .gt(Objects.nonNull(readTime), Message::getCreateTime, readTime)
                .count();
    }

    /**
     * 根据房间ID逻辑删除消息
     *
     * @param roomId 房间ID
     * @return 是否删除成功
     */
    public Boolean removeByRoomId(Long roomId) {
        LambdaUpdateWrapper<Message> wrapper = new UpdateWrapper<Message>().lambda()
                .eq(Message::getRoomId, roomId)
                .set(Message::getStatus, MessageStatusEnum.DELETE.getStatus());
        return this.update(wrapper);
    }
}
