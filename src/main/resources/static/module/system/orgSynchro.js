layui.use(['form', 'laydate'], function () {
    let form = layui.form;
    let laydate = layui.laydate;

    let syncTimeValue;

    /**
     * 同步周期改变事件
     * @param value 改变的值
     */
    let syncPeriodChange = function (value) {
        let $weekMonthContent = $(".week-month-content");
        let $syncDate = $("[name=syncDate]");
        $weekMonthContent.addClass("hide");
        $syncDate.html("");
        clearFormValid($syncDate);
        if (value === "week" || value === "month") {
            $weekMonthContent.removeClass("hide");
            addFormValid($syncDate);
            if (value === "week") {
                $syncDate.append($(".week-content").html());
            } else {
                $syncDate.append($(".month-content").html());
            }
        }
        form.render('select');
    }

    // 表单数据初始化
    CommonUtil.postSync(ctx + '/organization/getSynchroConfig', {}, function (result) {
        let data = result.data;
        syncPeriodChange(data.syncPeriod)
        form.val('synchro-form', data);
        syncTimeValue = data.syncTime || '01:00';
    });

    laydate.render({
        elem: '[name=syncTime]',
        type: 'time',
        format: 'HH:mm',
        value: syncTimeValue
    });

    form.on('select(syncPeriod)', function (data) {
        syncPeriodChange(data.value);
    });

    form.on('submit(save)', function (data) {
        let loadIndex = layer.load(2);
        CommonUtil.postAjax(ctx + '/organization/saveSynchroConfig', data.field, function (result) {
            layer.close(loadIndex);
            LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE);
        });
        return false;
    });

    form.on('submit(sync)', function (data) {
        let loadingIndex = layer.loadingWithText("正在同步中...");
        CommonUtil.postAjax(ctx + '/organization/immediatelySync', data.field, function (result) {
            layer.close(loadingIndex);
            LayerUtil.respMsg(result, "同步成功", "同步失败", function () {
                reloadFrame();
            });
        });
        return false;
    });
})