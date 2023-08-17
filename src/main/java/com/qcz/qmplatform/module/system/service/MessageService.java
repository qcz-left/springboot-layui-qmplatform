package com.qcz.qmplatform.module.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.system.assist.MessageReceiver;
import com.qcz.qmplatform.module.system.domain.Message;
import com.qcz.qmplatform.module.system.mapper.MessageMapper;
import com.qcz.qmplatform.module.system.vo.MessageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    UserService userService;

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
        LambdaUpdateWrapper<Message> updateWrapper = Wrappers.lambdaUpdate(Message.class)
                .in(Message::getMessageId, messageIds)
                .set(Message::getReaded, 1);
        return this.update(updateWrapper);
    }

    /**
     * 新增一条系统信息，会先判断该系统信息是否已经产生，如果已经产生则只改变上次更新时间
     *
     * @param message 系统信息
     */
    public void saveOne(Message message) {
        Timestamp currTimestamp = DateUtils.getCurrTimestamp();
        message.setLastUpdateTime(currTimestamp);

        LambdaQueryWrapper<Message> queryWrapper = Wrappers.lambdaQuery(Message.class)
                .eq(Message::getType, message.getType())
                .eq(Message::getReaded, message.getReaded())
                .eq(Message::getInstance, message.getInstance())
                .eq(Message::getReceiver, message.getReceiver());

        Message tmpMessage = getOne(queryWrapper);
        if (tmpMessage == null) {
            if (StringUtils.isBlank(message.getMessageId())) {
                message.setMessageId(IdUtils.getUUID());
            }
            message.setCreateTime(currTimestamp);
            save(message);
        } else {
            message.setMessageId(tmpMessage.getMessageId());
            updateById(message);
        }
    }

    /**
     * 创建系统信息
     *
     * @param message         系统信息
     * @param messageReceiver 发送对象
     */
    public void createMessage(Message message, MessageReceiver messageReceiver) {
        List<String> receiverIds = new ArrayList<>();
        if (messageReceiver == MessageReceiver.ALL) {
            receiverIds = CollectionUtil.getFieldValues(userService.getUserList(null, null, false), "id", String.class);
        } else if (messageReceiver == MessageReceiver.ADMIN) {
            receiverIds = CollectionUtil.getFieldValues(userService.queryAllAdmin(), "id", String.class);
        }
        for (String receiverId : receiverIds) {
            message.setMessageId(IdUtils.getUUID());
            message.setReceiver(receiverId);
            saveOne(message);
        }
    }

    public static void main(String[] args) {
        /*Map<String, Object> map = new HashMap<>();
        map.put("month", "2020-02");
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("branch", "第一分行");
        item1.put("terminalNum", "20");
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("a", "1");
        item1.put("testMap", testMap);
        Map<String, Object> item2 = new HashMap<>();
        item2.put("branch", "第一分行");
        item2.put("terminalNum", "35");
        Map<String, Object> testMap2 = new HashMap<>();
        testMap2.put("a", "2");
        item2.put("testMap", testMap2);
        Map<String, Object> item3 = new HashMap<>();
        item3.put("branch", "第三分行");
        item3.put("terminalNum", "40");
        Map<String, Object> testMap3 = new HashMap<>();
        testMap3.put("a", "3");
        item3.put("testMap", testMap3);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        map.put("data", list);
//        System.out.println(result);

        Map map1 = JSONUtil.toBean("{\n" +
                "\t\"month\": \"2020-02\",\n" +
                "\t\"data\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"branch\": \"第一分行\",\n" +
                "\t\t\t\"terminalNum\": \"20\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"branch\": \"第二分行\",\n" +
                "\t\t\t\"terminalNum\": \"35\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}", Map.class);
        List<Map<String, Object>> result = new ArrayList<>();
        reBuildMapToList(BeanUtil.beanToMap(map1), MapUtil.newHashMap(), result);

        System.out.println(CollectionUtil.groupByField(list, "branch"));*/

        System.out.println(new Date(System.currentTimeMillis() - 365 * 24 * 3600 * 1000L));
    }

    private static void reBuildMapToList(Map<String, Object> row, Map<String, Object> common, List<Map<String, Object>> result) {
        Map<String, Object> newRow = new HashMap<>(common);
        for (String rowKey : row.keySet()) {
            Object rowValue = row.get(rowKey);
            if (rowValue instanceof String || rowValue instanceof Integer) {
                newRow.put(rowKey, rowValue);
            }
        }

        Map<String, Object> newCommon = new HashMap<>(newRow);
        // 是否还有 List 或 Map 需要转换
        boolean isEnd = true;
        for (String rowKey : row.keySet()) {
            Object rowValue = row.get(rowKey);
            if (rowValue instanceof List) {
                isEnd = false;
                for (Object o : ((List) rowValue)) {
                    reBuildMapToList((Map) o, newCommon, result);
                }
            } else if (rowValue instanceof Map) {
                isEnd = false;
                reBuildMapToList((Map) rowValue, newCommon, result);
            }
        }

        if (isEnd) {
            result.add(newRow);
        }
    }

}
