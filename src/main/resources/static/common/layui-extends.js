layui.config({
    base: ctx + '/static/plugin/layui/module/'
}).extend({
    treetable: 'treetable-lay/treetable',
    xmSelect: 'xm-select/xm-select',
    dtree: 'dtree/dtree'
})
layui.use(["layer", "table"], function () {
    let layer = layui.layer;
    let table = layui.table;

    $.extend(table.config, {
        limit: 20,
        limits: [20, 100, 200],
        autoSort: false,
        parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.data.count, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        },
        response: {
            statusName: 'code',  //规定数据状态的字段名称，默认：code
            statusCode: 200,  //规定成功的状态码，默认：0
            msgName: 'msg',  //规定状态信息的字段名称，默认：msg
            countName: 'count',  //规定数据总数的字段名称，默认：count
            dataName: 'data' //规定数据列表的字段名称，默认：data
        }
    })
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
            this.msg(msg, opt);
        },
        /**
         * 错误提示，需要手动关闭
         */
        error: function (msg, option) {
            let opt = $.extend(this._defultOption.error, option);
            this.msg(msg, opt);
        },
        warning: function (msg, option) {
            let opt = $.extend(this._defultOption.warning, option);
            this.msg(msg, opt);
        },
        /**
         * 带文字的加载提示
         */
        loadingWithText: function (msg, option) {
            let opt = $.extend(this._defultOption.loadingWithText, option);
            return this.open({
                content: "<h1 style='text-align:left;font-size:24px;color:#003366;margin-left:40px;width:500px;font-family: \"Microsoft YaHei\" ! important;'>" + msg + "</h1>",
                icon: 2,
                type: 3,
                shade: 0.2,
            });
        },
        getElementLayerIndex: function (element) {
            return element.closest("div.layui-layer").prop("id").replace("layui-layer", "");
        }
    });
});
