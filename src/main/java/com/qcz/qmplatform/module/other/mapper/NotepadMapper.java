package com.qcz.qmplatform.module.other.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.other.domain.Notepad;
import com.qcz.qmplatform.module.other.domain.vo.NotepadVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2021-11-04
 */
public interface NotepadMapper extends BaseMapper<Notepad> {

    List<Notepad> getList(NotepadVO notepadVO);
}
