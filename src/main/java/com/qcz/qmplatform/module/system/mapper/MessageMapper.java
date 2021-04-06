package com.qcz.qmplatform.module.system.mapper;

import com.qcz.qmplatform.module.system.domain.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.system.vo.MessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统消息 Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2021-03-26
 */
public interface MessageMapper extends BaseMapper<Message> {

    List<Map<String, Long>> selectTypeCount(@Param("read") int read, @Param("receiver") String receiver);

    List<MessageVO> selectList(Message message);

}
