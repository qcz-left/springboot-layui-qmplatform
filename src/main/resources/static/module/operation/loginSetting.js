layui.use(['form'], function () {
    let baseUrl = ctx + '/operation/loginSetting';

    $(".main-content").mouseout(function () {
        $(this).find(".layui-tab").removeClass("gray-disabled")
        $(".edit-btn").hide();
    }).mouseover(function () {
        $(this).find(".layui-tab").addClass("gray-disabled")
        $(".edit-btn").show();
    });

    $(".edit-btn").click(function () {
        LayerUtil.openLayer({
            title: '登录设置',
            content: baseUrl + '/detail',
            area: ['50%', '80%'],
            loaded: function (iframeWin) {
                iframeWin.layui.use(['form'], function () {
                    let form = iframeWin.layui.form;
                    let tinymce = iframeWin.tinymce;

                    CommonUtil.getAjax(baseUrl + '/get', {}, function (result) {
                        form.val('login-setting-form', result.data);
                        tinymce.get('bottomInfo').setContent(result.data.bottomInfo);
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
                params.bottomInfo = tinymce.get('bottomInfo').getContent();
                CommonUtil.postAjax(baseUrl + '/save', params, function (result) {
                    layer.close(index);
                });
            }
        });
    });

});