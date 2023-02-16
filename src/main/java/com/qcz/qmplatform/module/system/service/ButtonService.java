package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.system.domain.Button;
import com.qcz.qmplatform.module.system.mapper.ButtonMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Service
public class ButtonService extends ServiceImpl<ButtonMapper, Button> {

    public List<Button> getButtonList(Button button) {
        String buttonName = button.getButtonName();
        LambdaQueryWrapper<Button> wrapper = Wrappers.lambdaQuery(Button.class)
                .like(StringUtils.isNotBlank(buttonName), Button::getButtonName, buttonName);
        return list(wrapper);
    }

    public boolean addButtonOne(Button button) {
        button.setButtonId(IdUtils.getUUID());
        return save(button);
    }

    public boolean updateButtonOne(Button button) {
        return updateById(button);
    }

    protected boolean saveButtonOne(Button button) {
        if (StringUtils.isBlank(button.getButtonId())) {
            return addButtonOne(button);
        }
        return updateButtonOne(button);
    }

    public boolean deleteButton(List<String> buttonIds) {
        return removeByIds(buttonIds);
    }

    public void deleteButtonByMenuId(String menuId) {
        baseMapper.deleteButtonByMenuId(menuId);
    }
}
