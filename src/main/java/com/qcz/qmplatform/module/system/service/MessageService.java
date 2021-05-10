package com.qcz.qmplatform.module.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.system.domain.Message;
import com.qcz.qmplatform.module.system.mapper.MessageMapper;
import com.qcz.qmplatform.module.system.vo.MessageVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<MessageVO> getList(Message message) {
        return baseMapper.selectList(message);
    }

    /**
     * 查询接收人的未读的通知数
     *
     * @param receiver 接收人id
     */
    public Map<String, Long> selectNoReadCount(String receiver) {
        return selectNoReadCount(CollectionUtil.newArrayList(receiver)).get(receiver);
    }

    /**
     * 根据接收人ID集合查询未读
     *
     * @param receivers 接收人ID集合
     * @return userId -> type count
     */
    public Map<String, Map<String, Long>> selectNoReadCount(List<String> receivers) {
        Map<String, Map<String, Long>> res = new HashMap<>();
        for (String receiver : receivers) {
            Map<String, Long> def = new HashMap<>();
            def.put("all", 0L);
            res.put(receiver, def);
        }

        List<Map<String, Object>> typeCounts = baseMapper.selectTypeCountByUserIds(0, receivers);
        for (Map<String, Object> typeCount : typeCounts) {
            String receiver = (String) typeCount.get("receiver");
            Long count = (Long) typeCount.get("count");

            Map<String, Long> receiverTypeCount = res.get(receiver);
            receiverTypeCount.put(String.valueOf(typeCount.get("type")), count);
            receiverTypeCount.put("all", receiverTypeCount.get("all") + count);
        }
        return res;
    }

    /**
     * 设置消息已读
     *
     * @param messageIds 消息id
     */
    public boolean setHasRead(String[] messageIds) {
        UpdateWrapper<Message> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("message_id", messageIds);
        updateWrapper.set("read", 1);
        return this.update(updateWrapper);
    }

}
