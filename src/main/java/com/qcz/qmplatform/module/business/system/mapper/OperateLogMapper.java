package com.qcz.qmplatform.module.business.system.mapper;

import com.qcz.qmplatform.module.business.system.domain.OperateLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.business.system.domain.qo.OperateLogTimeQO;
import com.qcz.qmplatform.module.business.system.domain.vo.OperateLogVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-06
 */
public interface OperateLogMapper extends BaseMapper<OperateLog> {
    List<OperateLogVO> queryOperateLogList(OperateLogTimeQO operateLog);
}
