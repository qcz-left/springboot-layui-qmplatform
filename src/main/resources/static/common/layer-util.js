let layer;
layui.use('layer', function () {
    layer = layui.layer;
    layer.config({
        maxWidth: 500
    });
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
            if (typeof (successCallback) == "function") {
                successCallback(res.data)
            }
            top.layer.success(res.msg || successMsg || Msg.OPERATE_SUCCESS)
        } else {
            if (typeof (failCallback) == "function") {
                failCallback()
            }
            top.layer.error(res.msg || failMsg || Msg.OPERATE_FAILURE)
        }
    }
}
