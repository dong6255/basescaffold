package com.lvch.scaffold.common.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvch.scaffold.common.domain.entity.Black;
import com.lvch.scaffold.common.mapper.BlackMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">abin</a>
 * @since 2023-05-21
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> {

}
