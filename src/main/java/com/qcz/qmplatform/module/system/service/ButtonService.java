package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        QueryWrapper<Button> wrapper = new QueryWrapper<>();
        String buttonName = button.getButtonName();
        wrapper.like(StringUtils.isNotBlank(buttonName), "button_name", buttonName);
        return list(wrapper);
    }

    public boolean addButtonOne(Button button) {
        button.setButtonId(IdUtils.simpleUUID());
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
