package com.lvch.scaffold.common.service.cache;


import com.lvch.scaffold.common.dao.MessageDao;
import com.lvch.scaffold.common.dao.UserDao;
import com.lvch.scaffold.common.domain.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Description: 消息相关缓存
 * Author: April
 * Date: 2023-03-27
 */
@Component
public class MsgCache {

    @Autowired
    private MessageDao messageDao;

    @Cacheable(cacheNames = "msg", key = "'msg'+#msgId")
    public Message getMsg(Long msgId) {
        return messageDao.getById(msgId);
    }

    @CacheEvict(cacheNames = "msg", key = "'msg'+#msgId")
    public Message evictMsg(Long msgId) {
        return null;
    }
}
