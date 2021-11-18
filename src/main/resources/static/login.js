/**
 * 登录
 */
layui.use(['form', 'layer'], function () {
    if (window != top)
        top.location.href = location.href;
    let layer = layui.layer; // 获取layer模块
    let form = layui.form;

    let expire = code === '402';
    let dataBakRecover = code === '405';
    if (expire || dataBakRecover) {
        let msg;
        let icon;
        if (expire) {
            icon = 0;
            msg = "当前会话已过期，请重新登录！";
        } else if (dataBakRecover) {
            icon = 1;
            msg = "恢复备份成功，请重新登录！";
        }
        layer.alert(msg, {
            icon: icon,
            title: null,
            btn: [],
            offset: '30px'
        });
    }

    $("#logo-code").click(function () {
        $(".pc-login").hide();
        $(".code-login").show();
    });

    $("#logo-pc").click(function () {
        $(".code-login").hide();
        $(".pc-login").show();
    });

    // 加载二维码
    $("#codeLoginContainer iframe").attr("src", "https://login.dingtalk.com/oauth2/auth?client_id=ding87uronmn84khshi8&prompt=consent&response_type=code&scope=openid&state=STATE&redirect_uri=" + window.location.protocol + "//" + window.location.host + ctx + "/noNeedLogin/preScanCodeLoginCheck");

    /**
     * 加载验证码
     */
    let loadCode = function () {
        $("#codeDiv").show();
        $("#user-login-code").attr("lay-verify", "required");
        form.render(null, 'validateCode');
        $("#codeImg").attr("src", ctx + "/noNeedLogin/getLoginCode");
    };
    $("#codeImg").click(function () {
        $(this).attr("src", ctx + "/noNeedLogin/getLoginCode?v=" + new Date().getTime());
    });
    form.on('submit(login)', function (data) {
        data.field.password = rsaEncrypt(data.field.password);
        // 登录表单提交操作
        layer.loadingWithText("正在努力登录...");
        CommonUtil.postAjax(ctx + "/login", data.field, function (response) {
            layer.closeAll("loading");
            if (!response.ok) {
                if (response.data && response.data['needCode']) {
                    loadCode();
                    if ($("#codeImg").attr("src")) {
                        $("#codeImg").click();
                    }
                }
                layer.error(response.msg);
            } else {
                top.location.href = ctx;
            }
        });
    });

});
