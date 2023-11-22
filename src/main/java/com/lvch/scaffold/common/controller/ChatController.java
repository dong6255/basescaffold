package com.lvch.scaffold.common.controller;



import com.lvch.scaffold.common.domain.vo.request.ChatMessagePageReq;
import com.lvch.scaffold.common.domain.vo.request.ChatMessageReq;
import com.lvch.scaffold.common.domain.vo.response.ApiResult;
import com.lvch.scaffold.common.domain.vo.response.ChatMessageResp;
import com.lvch.scaffold.common.domain.vo.response.CursorPageBaseResp;
import com.lvch.scaffold.common.service.ChatService;
import com.lvch.scaffold.common.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 群聊相关接口
 * </p>
 *
 * @author April
 * @since 2023-03-19
 */
@RestController
@RequestMapping("/capi/chat")
@Api(tags = "聊天室相关接口")
@Slf4j
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/public/msg/page")
    @ApiOperation("消息列表")
    public ApiResult<CursorPageBaseResp<ChatMessageResp>> getMsgPage(@Valid ChatMessagePageReq request) {
        CursorPageBaseResp<ChatMessageResp> msgPage = chatService.getMsgPage(request, RequestHolder.get().getUid());
        return ApiResult.success(msgPage);
    }

    @PostMapping("/msg")
    @ApiOperation("发送消息")
    public ApiResult<ChatMessageResp> sendMsg(@Valid @RequestBody ChatMessageReq request) {
        Long msgId = chatService.sendMsg(request, RequestHolder.get().getUid());
        //返回完整消息格式，方便前端展示
        return ApiResult.success(chatService.getMsgResp(msgId, RequestHolder.get().getUid()));
    }

}

