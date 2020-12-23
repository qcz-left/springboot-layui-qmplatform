package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.system.domain.OperateLog;
import com.qcz.qmplatform.module.system.mapper.OperateLogMapper;
import com.qcz.qmplatform.module.system.vo.OperateLogTimeVO;
import com.qcz.qmplatform.module.system.vo.OperateLogVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-06
 */
@Service
public class OperateLogService extends ServiceImpl<OperateLogMapper, OperateLog> {
    public List<OperateLogVO> queryOperateLogList(OperateLogTimeVO operateLogTimeVO) {
        return baseMapper.queryOperateLogList(operateLogTimeVO);
    }
}
