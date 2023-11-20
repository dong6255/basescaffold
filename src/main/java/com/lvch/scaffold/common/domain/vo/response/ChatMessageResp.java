package com.lvch.scaffold.common.domain.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description: 消息
 * Author: April
 * Date: 2023-03-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResp {

    @ApiModelProperty("发送者信息")
    private UserInfo fromUser;
    @ApiModelProperty("消息详情")
    private Message message;

    @Data
    public static class UserInfo {
        @ApiModelProperty("用户id")
        private Long uid;
    }

    @Data
    public static class Message {
        @ApiModelProperty("消息id")
        private Long id;
        @ApiModelProperty("房间id")
        private Long roomId;
        @ApiModelProperty("消息发送时间")
        private Date sendTime;
        @ApiModelProperty("消息类型 1正常文本 2.撤回消息")
        private Integer type;
        @ApiModelProperty("消息内容不同的消息类型，内容体不同")
        private Object body;
        //@ApiModelProperty("消息标记")
        //private MessageMark messageMark;
    }

}
