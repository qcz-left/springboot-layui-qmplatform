layui.use(['form'], function () {
    let baseUrl = ctx + '/operation/loginSetting';

    let $mainContent = $(".main-content");
    $mainContent.parents("html:first").mouseout(function () {
        $mainContent.find(".layui-tab").removeClass("gray-disabled")
        $(".edit-btn").hide();
    }).mouseover(function () {
        $mainContent.find(".layui-tab").addClass("gray-disabled")
        $(".edit-btn").show();
    });

    $(".edit-btn").click(function () {
        LayerUtil.openLayer({
            title: '登录设置',
            content: baseUrl + '/detail',
            area: ['50%', '85%'],
            loaded: function (iframeWin) {
                iframeWin.layui.use(['form'], function () {
                    let form = iframeWin.layui.form;
                    let tinymce = iframeWin.tinymce;

                    CommonUtil.getAjax(baseUrl + '/get', {}, function (result) {
                        let data = result.data;

                        if (data.otherLoginWay) {
                            iframeWin.$.each(data.otherLoginWay.split(","), function (index, item) {
                                iframeWin.$("[name^=otherLoginWay][value=" + item + "]").attr("checked", true);
                            });
                        }
                        form.val('login-setting-form', data);
                        tinymce.get('bottomInfo').setContent(data.bottomInfo);
                    })
                })
            },
            submit: function (iframeWin, index) {
                let form = iframeWin.layui.form;
                let tinymce = iframeWin.tinymce;
                if (!form.doVerify(iframeWin.$("form"))) {
                    return false;
                }
                let params = form.val('login-setting-form');
                let otherLoginWayArray = [];
                iframeWin.$("[name^=otherLoginWay]:checked").each(function () {
                    otherLoginWayArray.push($(this).val());
                });
                params.otherLoginWay = CommonUtil.joinMulti(otherLoginWayArray, ",");
                params.bottomInfo = tinymce.get('bottomInfo').getContent();
                let saveIndex = layer.loadingWithText("正在保存...");
                CommonUtil.postAjax(baseUrl + '/save', params, function (result) {
                    layer.close(saveIndex);
                    LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, function () {
                        reloadFrame();
                    });
                });
            }
        });
    });

});