package com.qcz.qmplatform.module.other.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.other.domain.Notepad;
import com.qcz.qmplatform.module.other.mapper.NotepadMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.other.vo.NotepadVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 记事本 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2021-11-04
 */
@Service
public class NotepadService extends ServiceImpl<NotepadMapper, Notepad> {

    public boolean saveOne(Notepad notepad) {
        notepad.setCreateUserId(SubjectUtils.getUserId());
        notepad.setCreateUserId(SubjectUtils.getUserName());
        notepad.setCreateTime(DateUtils.getCurrTimestamp());
        return save(notepad);
    }

    public List<Notepad> getList(NotepadVO notepadVO) {
        return baseMapper.getList(notepadVO);
    }
}
