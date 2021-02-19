const Common = {
    /**
     * -
     */
    NULL: '-'
};

const Password = {
    /**
     * 未改变的密码标识
     */
    UN_CHANGED_PASSWORD: '******'
};

/**
 * 状态类型
 */
const StatusType = {
    /**
     * 成功
     */
    SUCCESS: 'success',
    /**
     * 普通信息
     */
    INFO: 'info',
    /**
     * 警告
     */
    WARNING: 'warning',
    /**
     * 错误
     */
    ERROR: 'error'
};

/**
 * ajax请求类型
 */
const AjaxType = {
    /**
     * get
     */
    GET: 'get',
    /**
     * post
     */
    POST: 'post',
    /**
     * put
     */
    PUT: 'put',
    /**
     * patch
     */
    PATCH: 'patch',
    /**
     * delete
     */
    DELETE: 'delete'
};

const Msg = {
    /**
     * 操作成功
     */
    OPERATE_SUCCESS: '操作成功！',
    /**
     * 操作失败
     */
    OPERATE_FAILURE: '操作失败！',
    /**
     * 保存成功
     */
    SAVE_SUCCESS: '保存成功！',
    /**
     * 保存失败
     */
    SAVE_FAILURE: '保存失败！',
    /**
     * 删除成功
     */
    DELETE_SUCCESS: '删除成功！',
    /**
     * 删除失败
     */
    DELETE_FAILURE: '删除失败！',
    /**
     * 请至少选择一条数据！
     */
    AT_LEAST_CHOOSE_ONE: '请至少选择一条数据！'
};

/**
 * 短信模板类型
 * @type {{}}
 */
const TemplateType = {
    VALIDATE_CODE: '验证码',
    ALARM: '告警',
    NOTIFY: '广播通知'
};

/**
 * 操作类型
 */
const OperateType = {
    /**
     * 添加
     */
    ADD: 'add',
    /**
     * 编辑
     */
    EDIT: 'edit',
    /**
     * 删除
     */
    DELETE: 'delete',
    /**
     * 导出
     */
    EXPORT: 'export',
    /**
     * 导入
     */
    IMPORT: 'import'
};
