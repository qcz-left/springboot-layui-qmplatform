package com.qcz.qmplatform.module.business.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.business.system.domain.UserThirdparty;
import com.qcz.qmplatform.module.business.system.domain.assist.Thirdparty;
import com.qcz.qmplatform.module.business.system.mapper.UserThirdpartyMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和第三方的绑定关系 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2021-11-18
 */
@Service
public class UserThirdpartyService extends ServiceImpl<UserThirdpartyMapper, UserThirdparty> {

    /**
     * 如果不存在就插入一条
     */
    public void saveIfNotExists(UserThirdparty userThirdparty) {
        boolean exists = exists(
                Wrappers.lambdaQuery(UserThirdparty.class)
                        .eq(UserThirdparty::getUserId, userThirdparty.getUserId())
                        .eq(UserThirdparty::getAccessType, userThirdparty.getAccessType())
        );
        if (!exists) {
            save(userThirdparty);
        }

    }

    /**
     * 查询与第三方绑定的用户ID
     *
     * @param thirdpartyId 三方ID
     * @param thirdparty   三方服务商
     */
    public UserThirdparty getUserIdByThirdparty(String thirdpartyId, Thirdparty thirdparty) {
        LambdaQueryWrapper<UserThirdparty> queryWrapper = Wrappers.lambdaQuery(UserThirdparty.class)
                .eq(UserThirdparty::getThirdpartyId, thirdpartyId)
                .eq(UserThirdparty::getAccessType, thirdparty.getType());
        return baseMapper.selectOne(queryWrapper);
    }
}
