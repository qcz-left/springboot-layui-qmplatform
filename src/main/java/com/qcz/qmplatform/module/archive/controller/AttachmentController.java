package com.qcz.qmplatform.module.archive.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.archive.domain.Attachment;
import com.qcz.qmplatform.module.archive.service.AttachmentService;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.system.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-12
 */
@Controller
@RequestMapping("/archive/attachment")
@Module("文件管理")
public class AttachmentController extends BaseController {

    private static final String PREFIX = "/module/archive/";

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/attachmentListPage")
    public String attachmentListPage() {
        return PREFIX + "attachmentList";
    }

    @GetMapping("/uploadPage")
    public String uploadPage() {
        return PREFIX + "attachmentDetail";
    }

    @GetMapping("/getAttachmentList")
    @ResponseBody
    public ResponseResult<PageResult> getAttachmentList(PageRequest pageRequest, Attachment attachment) {
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(attachment.getAttachmentName()), "attachment_name", attachment.getAttachmentName());
        queryWrapper.like(StringUtils.isNotBlank(attachment.getUploadUserName()), "upload_user_name", attachment.getUploadUserName());
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(attachmentService.list(queryWrapper)));
    }

    @PostMapping("/uploadAttachment")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "上传文件")
    public ResponseResult<?> uploadAttachment(MultipartFile file, Attachment attachment) throws IOException {
        ResponseResult<Map<String, String>> upload = upload(file);
        // 将文件路径保存到数据库
        if (upload.isOk()) {
            attachment.setAttachmentId(StringUtils.uuid());
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setAttachmentUrl(upload.getData().get("filePath"));
            attachment.setUploadTime(DateUtils.getCurrTimestamp());
            User currentUser = SubjectUtils.getUser();
            attachment.setUploadUserId(currentUser.getId());
            attachment.setUploadUserName(currentUser.getUsername());
            attachmentService.save(attachment);
        }
        return upload;
    }

    @DeleteMapping("/delAttachment")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除文件")
    public ResponseResult<?> delAttachment(String attachmentIds) {
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
        List<String> idList = Arrays.asList(attachmentIds.split(","));
        queryWrapper.in("attachment_id", idList);
        List<String> attachmentUrls = CollectionUtil.getFieldValues(attachmentService.list(queryWrapper), "attachmentUrl", String.class);
        for (String attachmentUrl : attachmentUrls) {
            FileUtil.del(FileUtils.getRealFilePath(attachmentUrl));
        }
        if (attachmentService.removeByIds(idList)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }
}
