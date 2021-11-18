package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.system.assist.Thirdparty;
import com.qcz.qmplatform.module.system.domain.UserThirdparty;
import com.qcz.qmplatform.module.system.mapper.UserThirdpartyMapper;
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

    public boolean saveOne(UserThirdparty userThirdparty) {
        return save(userThirdparty);
    }

    public boolean updateOne(UserThirdparty userThirdparty) {
        return updateById(userThirdparty);
    }

    /**
     * 查询与第三方绑定的用户ID
     *
     * @param thirdpartyId 三方ID
     * @param thirdparty   三方服务商
     */
    public UserThirdparty getUserIdByThirdparty(String thirdpartyId, Thirdparty thirdparty) {
        QueryWrapper<UserThirdparty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("thirdparty_id", thirdpartyId);
        queryWrapper.eq("access_type", thirdparty.getType());
        return baseMapper.selectOne(queryWrapper);
    }
}
