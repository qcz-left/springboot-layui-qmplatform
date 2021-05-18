package com.qcz.qmplatform.module.operation.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.operation.domain.LoginRecord;
import com.qcz.qmplatform.module.operation.mapper.LoginRecordMapper;
import com.qcz.qmplatform.module.operation.vo.LoginStrategyVO;
import com.qcz.qmplatform.module.system.assist.IniDefine;
import com.qcz.qmplatform.module.system.domain.Ini;
import com.qcz.qmplatform.module.system.service.IniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账号登录系统记录 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2021-05-17
 */
@Service
public class LoginRecordService extends ServiceImpl<LoginRecordMapper, LoginRecord> {

    private static final String SECTION = IniDefine.LOGIN_STRATEGY;

    @Autowired
    IniService iniService;

    /**
     * 获取策略信息
     */
    public LoginStrategyVO getStrategy() {
        Map<String, String> loginStrategy = iniService.getBySec(IniDefine.LOGIN_STRATEGY);
        LoginStrategyVO loginStrategyVO = new LoginStrategyVO();
        String codeAtErrorTimes = loginStrategy.get(IniDefine.LoginStrategy.CODE_AT_ERROR_TIMES);
        if (!StringUtils.isBlank(codeAtErrorTimes)) {
            loginStrategyVO.setCodeAtErrorTimes(Integer.parseInt(codeAtErrorTimes));
        }
        String lockAtErrorTimes = loginStrategy.get(IniDefine.LoginStrategy.LOCK_AT_ERROR_TIMES);
        if (!StringUtils.isBlank(lockAtErrorTimes)) {
            loginStrategyVO.setLockAtErrorTimes(Integer.parseInt(lockAtErrorTimes));
        }
        String enable = loginStrategy.get(IniDefine.LoginStrategy.ENABLE);
        if (!StringUtils.isBlank(enable)) {
            loginStrategyVO.setEnable(Integer.parseInt(enable));
        }
        return loginStrategyVO;
    }

    /**
     * 保存登录策略
     *
     * @param loginStrategyVO 策略
     */
    public boolean saveLoginStrategy(LoginStrategyVO loginStrategyVO) {
        iniService.deleteBySec(SECTION);
        return iniService.save(new Ini(SECTION, IniDefine.LoginStrategy.ENABLE, String.valueOf(loginStrategyVO.getEnable())))
                && iniService.save(new Ini(SECTION, IniDefine.LoginStrategy.CODE_AT_ERROR_TIMES, String.valueOf(loginStrategyVO.getCodeAtErrorTimes())))
                && iniService.save(new Ini(SECTION, IniDefine.LoginStrategy.LOCK_AT_ERROR_TIMES, String.valueOf(loginStrategyVO.getLockAtErrorTimes())));
    }

    public LoginRecord getLoginRecord(String loginName, String clientIp) {
        QueryWrapper<LoginRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", loginName);
        queryWrapper.eq("last_login_ip", clientIp);
        return this.getOne(queryWrapper);
    }

    /**
     * 删除错误记录
     *
     * @param loginName 登录名
     * @param clientIp  客户端
     */
    public void clearLoginRecord(String loginName, String clientIp) {
        QueryWrapper<LoginRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", loginName);
        queryWrapper.eq("last_login_ip", clientIp);
        this.remove(queryWrapper);
    }

    public int getLoginErrorTimes(String loginName, String clientIp) {
        LoginRecord loginRecord = getLoginRecord(loginName, clientIp);
        return loginRecord == null ? 0 : loginRecord.getErrorTimes();
    }

    /**
     * 增加登录错误次数
     *
     * @param loginName 登录名
     * @param clientIp  客户端
     */
    public void increaseErrorTimes(String loginName, String clientIp) {
        LoginRecord loginRecord = getLoginRecord(loginName, clientIp);
        if (loginRecord == null) {
            // 新增记录
            loginRecord = new LoginRecord();
            loginRecord.setRecordId(IdUtil.randomUUID());
            loginRecord.setErrorTimes(1);
            loginRecord.setLastLoginIp(clientIp);
            loginRecord.setLastLoginTime(DateUtils.getCurrTimestamp());
            loginRecord.setLoginName(loginName);

            this.save(loginRecord);
        } else {
            baseMapper.increaseErrorTimes(loginName, clientIp);
        }
    }

    /**
     * 添加登录错误备注
     *
     * @param loginName 登录名
     * @param clientIp  客户端ip
     * @param remark    备注
     */
    public void addRemark(String loginName, String clientIp, String remark) {
        UpdateWrapper<LoginRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("login_name", loginName);
        updateWrapper.eq("last_login_ip", clientIp);
        updateWrapper.set("remark", remark);
        this.update(updateWrapper);
    }

    /**
     * 通过id删除记录
     *
     * @param recordIds id集合
     */
    public boolean deleteLoginRecord(List<String> recordIds) {
        QueryWrapper<LoginRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("record_id", recordIds);
        return this.remove(queryWrapper);
    }
}
