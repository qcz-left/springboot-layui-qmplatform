layui.config({
    base: ctx + '/static/plugin/layui/module/'
}).extend({
    treetable: 'treetable-lay/treetable',
    xmSelect: 'xm-select/xm-select',
    dtree: 'dtree/dtree',
    step: 'step-lay/step'
});
layui.use(["layer"], function () {
    let layer = layui.layer;
    $.extend(layer, {
        _defultOption: {
            success: {
                time: 1000,
                icon: 1
            },
            warning: {
                time: 3000,
                icon: 3
            },
            error: {
                icon: 2
            },
            loadingWithText: {}

        },
        /**
         * 成功提示
         */
        success: function (msg, option) {
            let opt = $.extend(this._defultOption.success, option);
            return this.msg(msg, opt);
        },
        /**
         * 错误提示，需要手动关闭
         */
        error: function (msg, option) {
            let opt = $.extend(this._defultOption.error, option);
            return this.msg(msg, opt);
        },
        warning: function (msg, option) {
            let opt = $.extend(this._defultOption.warning, option);
            return this.msg(msg, opt);
        },
        /**
         * 带文字的加载提示
         */
        loadingWithText: function (msg, option) {
            option = option || {
                icon: 16,
                shade: 0.01,
                time: 0
            };
            return this.msg(msg, option);
        }
    });
});
