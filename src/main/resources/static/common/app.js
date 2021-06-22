/**
 * app 内置方法
 */
$(function () {
    $(".closeNowLayer").click(function () {
        closeCurrentIframe();
    });
});
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
Date.prototype.format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

String.prototype.format = function () {
    if (arguments.length == 0) {
        return this;
    }
    let s = this;
    for (let i = 0; i < arguments.length; i++) {
        s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
    }
    return s;
};

function closeCurrentIframe() {
    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
    parent.layer.close(index);
}

/*
 * 获取表单参数 定制为在class为layui-form下的class为data-param的表单参数，
 */
function getParam(jqDom) {
    let param = {};
    $.each(jqDom.find(".layui-form .data-param,input,select,textarea"), function () {
        let nodeName = $(this).context.nodeName.toLowerCase();
        let attrName = $(this).attr("name");
        let attrType = $(this).attr("type");
        if (nodeName == "textarea" || nodeName == "div") {
            param[attrName] = $(this)[0].value;
        } else {
            if (attrType == "radio" || attrType == "checkbox") {
                if ($(this).is(":checked")) {
                    param[attrName] = $(this).val();
                }
            } else {
                param[attrName] = $(this).val();
            }
        }
    });
    return param;
}

function getLayeroParam(layero) {
    return getParam(layero.find("iframe")[0].contentWindow.$);
}

/**
 * 重载上层数据表格
 */
function reloadParentTable() {
    parent.$.find(".layui-show iframe")[0].contentWindow.tableReload();
}

/**
 * 重载上层数据表格
 */
function reloadTable(openId) {
    parent.$.find("#" + openId + " iframe")[0].contentWindow.tableReload();
}

function reloadFrame() {
    top.$(".layui-tab[lay-filter='main-tab'] .layui-show iframe:first")[0].contentWindow.location.reload(true);
}

/*
 * layui数据表格排序监听事件 layuiTable layui表格对象 tableId table元素id
 */
function sortEventListen(layuiTable, layFilter, tableId) {
    layuiTable.on('sort(' + layFilter + ')', function (obj) { // 注：tool是工具条事件名，data-table是table原始容器的属性
        let config = layuiTable.getConfig(tableId);
        let where = config.where;
        delete where['orderName'];
        delete where['order'];
        if (obj.type) {
            where['orderName'] = obj.field;
            where['order'] = obj.type;
        }
        layuiTable.reload(tableId, {
            where: where
        });
    });
}

/*
 * 打开表单
 * title 标题
 * url 请求路径
 * isTop 是否在顶层的基础上打开图层，默认false
 */
function openCommonForm(table, tableId, title, url, area, isTop) {
    isTop = isTop != undefined ? isTop : false;
    var options = {
        type: 2,
        title: title,
        content: url,
        area: area ? area : ["90%", "90%"],
        skin: 'layui-layer-molv',
        shade: 0.3,
        maxmin: true,
        resize: true,
        moveOut: true,
        zIndex: layer.zIndex, //多窗口模式，层叠打开
        end: function () {
            table.reload(tableId);
        }
    };
    if (isTop) {
        top.layer.open(options);
    } else {
        layer.open(options);
    }
}

function toUrlParam(param, key) {
    let paramStr = "";
    if (param instanceof String || param instanceof Number || param instanceof Boolean) {
        paramStr += "&" + key + "=" + encodeURIComponent(param);
    } else {
        $.each(param, function (i) {
            let k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
            paramStr += '&' + toUrlParam(this, k);
        });
    }
    return paramStr.substr(1);
}

function getColNames(tableIns) {
    let colNames = {};
    let cols = tableIns.config.cols[0];
    for (let i = 0; i < cols.length; i++) {
        let item = cols[i];
        let excelAble;
        if (typeof item.excel == "undefined") {
            excelAble = true;
        }
        if (excelAble) {
            colNames[item.field] = {
                title: item.title,
                width: item.exportWidth || 25
            };
        }
    }
    return colNames;
}

/**
 * RSA参数加密
 */
function rsaEncrypt(data) {
    let encrypt = new JSEncrypt();
    encrypt.setPublicKey(top.rsaPublicKey);
    return "ENC({0})".format(encrypt.encrypt(data));
}