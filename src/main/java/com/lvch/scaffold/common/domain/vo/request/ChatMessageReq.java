package com.lvch.scaffold.common.domain.vo.request;

import com.lvch.scaffold.common.domain.vo.request.msg.TextMsgReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageReq {
    @NotNull
    @ApiModelProperty("房间id")
    private Long roomId;

    @ApiModelProperty("消息类型")
    @NotNull
    private Integer msgType;

    @ApiModelProperty("消息内容，类型不同传值不同")
    @NotNull
    private TextMsgReq body;

}
