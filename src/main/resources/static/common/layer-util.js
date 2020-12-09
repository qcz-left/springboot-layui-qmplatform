let layer;
layui.use('layer', function () {
    layer = layui.layer;
})

const LayerUtil = {
    /**
     * 请求响应信息
     * @param res 响应结果
     * @param successMsg 成功信息（默认：操作成功）
     * @param failMsg 失败信息（默认：操作失败）
     * @param successCallback 成功回调
     * @param failCallback 失败回调
     */
    respMsg: function (res, successMsg, failMsg, successCallback, failCallback) {
        if (CommonUtil.respSuccess(res)) {
            top.layer.success(res.msg || successMsg || Msg.OPERATE_SUCCESS)
            if (typeof (successCallback) == "function") {
                successCallback(res.data)
            }
        } else {
            top.layer.error(res.msg || failMsg || Msg.OPERATE_FAILURE)
            if (typeof (failCallback) == "function") {
                failCallback()
            }
        }
    }
}
