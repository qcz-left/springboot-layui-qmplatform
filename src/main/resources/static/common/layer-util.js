let layer;
layui.use('layer', function () {
    layer = layui.layer;
    layer.config({
        maxWidth: 500
    });
});

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
    },

    /**
     * 打开一个弹窗<p>
     * 1、loaded和success函数如果同时存在，只生效loaded <p>
     * 2、submit和yes函数如果同时存在，只生效submit <p>
     * 3、如果设置了submit函数，按钮显示默认为['保存', '取消'] <p>
     * 4、默认宽高50% <p>
     * 5、layer默认type类型为2 <p>
     * @param options 原生layer参数
     * @param isTop 是否顶层
     */
    openLayer: function (options, isTop) {
        isTop = isTop !== undefined ? isTop : false;
        if (options.loaded && typeof options.loaded == "function") {
            options.success = function (layerDom, index) {
                let iframeWin = layerDom.find('iframe')[0].contentWindow;
                options.loaded(iframeWin, index);
            }
        }
        if (options.submit && typeof options.submit == "function") {
            if (!options.btn) {
                options.btn = ['保存', '取消'];
            }
            options.yes = function (index, layerDom) {
                let iframeWin = layerDom.find('iframe')[0].contentWindow;
                // 表单提交时做表单校验
                let form = iframeWin.layui.form;
                if (options.formVerify && form) {
                    if (!form.doVerify(iframeWin.$("form"))) {
                        return false;
                    }
                }
                options.submit(iframeWin, index);
            }
        }

        if (!options.area) {
            options.area = ['50%', '50%'];
        }
        if (!options.type) {
            options.type = 2;
        }
        let currentLayer;
        if (isTop) {
            currentLayer = top.layer;
        } else {
            currentLayer = layer;
        }
        return currentLayer.open(options);
    },

    /**
     * layer的tooltips
     *
     * @param options
     * content: 显示内容<br>
     * follow: 展示位置<br>
     * trigger: 触发事件，鼠标移上去展示（move）或鼠标左键点击展示（click），默认 move<br>
     * @param tipsOptions
     */
    tips: function (options, tipsOptions) {
        let content = options.content;
        let follow = options.follow;
        let iframeWin = options.window || window;
        let trigger = options.trigger || 'move';

        let tipIndex;
        let tipsTrigger;
        let cancelTipsTrigger;
        let isClickTrigger = trigger === 'click';
        if (isClickTrigger) {
            tipsTrigger = "click";
        } else {
            tipsTrigger = "mouseenter";
            cancelTipsTrigger = "mouseleave";
        }

        let $follow = iframeWin.$(follow);
        $follow.css("cursor", "pointer");
        $follow.on(tipsTrigger, function (event) {
            event.stopPropagation();
            tipIndex = iframeWin.layer.tips(content, this, $.extend({
                time: 0
            }, tipsOptions));

            if (isClickTrigger) {
                $("#layui-layer" + tipIndex).click(function (event) {
                    event.stopPropagation();
                });

                let closeTips = function (window) {
                    if (!window) {
                        return;
                    }
                    window.$("html").click(function () {
                        window.layer.close(tipIndex)
                    });
                    if (window !== top) {
                        closeTips(window.parent);
                    }
                }

                closeTips(iframeWin);
            }
        });

        if (cancelTipsTrigger) {
            $follow.on(cancelTipsTrigger, function () {
                iframeWin.layer.close(tipIndex)
            })
        }

    }
};
