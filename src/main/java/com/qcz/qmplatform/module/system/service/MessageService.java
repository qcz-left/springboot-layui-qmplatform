package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.system.domain.Message;
import com.qcz.qmplatform.module.system.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统消息 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2021-03-26
 */
@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {

}
