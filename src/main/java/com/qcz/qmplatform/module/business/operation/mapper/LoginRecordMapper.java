package com.qcz.qmplatform.module.business.operation.mapper;

import com.qcz.qmplatform.module.business.operation.domain.LoginRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账号登录系统记录 Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2021-05-17
 */
public interface LoginRecordMapper extends BaseMapper<LoginRecord> {
    void increaseErrorTimes(@Param("loginName") String loginName, @Param("lastLoginIp") String lastLoginIp);
}
