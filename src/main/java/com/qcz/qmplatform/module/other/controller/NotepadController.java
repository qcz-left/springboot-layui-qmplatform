package com.qcz.qmplatform.module.other.controller;


import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.other.domain.Notepad;
import com.qcz.qmplatform.module.other.service.NotepadService;
import com.qcz.qmplatform.module.other.vo.NotepadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 * 记事本 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2021-11-04
 */
@Controller
@RequestMapping("/other/notepad")
@Module("记事本")
public class NotepadController extends BaseController {

    private static final String PREFIX = "/module/other/";

    @Autowired
    NotepadService notepadService;

    @GetMapping("/addPage")
    public String addPage() {
        return PREFIX + "addNotepad";
    }

    @GetMapping("/listPage")
    public String listPage() {
        return PREFIX + "notepad";
    }

    @PostMapping("/list")
    public ResponseResult<?> list(PageRequest pageRequest, NotepadVO notepadVO) {
        PageResultHelper.startPage(pageRequest.getPage(), pageRequest.getLimit());
        List<Notepad> notepadList = notepadService.getList(notepadVO);
        return ResponseResult.ok(PageResultHelper.parseResult(notepadList));
    }

    @PostMapping("/save")
    @RecordLog(type = OperateType.INSERT, description = "添加记事本")
    public ResponseResult<?> save(@RequestBody Notepad notepad) {
        if (notepadService.saveOne(notepad)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    @PostMapping("/delete")
    @RecordLog(type = OperateType.DELETE, description = "删除记事本")
    public ResponseResult<?> delete() {

        return ResponseResult.error();
    }
}
