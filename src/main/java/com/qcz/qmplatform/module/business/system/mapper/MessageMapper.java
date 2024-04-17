package com.qcz.qmplatform.module.business.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.business.system.domain.Message;
import com.qcz.qmplatform.module.business.system.domain.vo.MessageVO;
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

    List<Map<String, Long>> selectTypeCount(@Param("readed") int readed, @Param("receiver") String receiver);

    List<Map<String, Object>> selectTypeCountByUserIds(@Param("readed") int readed, @Param("receivers") List<String> receivers);

    List<MessageVO> selectMessageList(Message message);

}
