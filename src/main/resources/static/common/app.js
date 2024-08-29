/**
 * app 内置方法
 */
$(function () {
    $(".closeNowLayer").click(function () {
        closeCurrentIframe();
    });
    let $iframe = top.$(".layui-show [id^=iframe-body]")[0];
    if ($iframe) {
        $iframe.contentWindow.$("html:first").css("background-color", "#f2f2f2");
    }
});
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
Date.prototype.format = function (fmt) { // author: meizz
    let o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

String.prototype.format = function () {
    if (arguments.length === 0) {
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
        if (nodeName === "textarea" || nodeName === "div") {
            param[attrName] = $(this)[0].value;
        } else {
            if (attrType === "radio" || attrType === "checkbox") {
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

/**
 * 重载上层数据表格
 */
function reloadParentTable() {
    parent.$.find(".layui-show iframe")[0].contentWindow.tableReload();
}

/**
 * 重载上层指定的数据表格
 * @param openId 打开layer时指定的id
 */
function reloadTable(openId) {
    getWindow(openId).tableReload();
}

/**
 * 获取上层iframe的contentWindow对象
 * @param openId 打开layer时指定的id
 */
function getWindow(openId) {
    return parent.$.find("#" + openId + " iframe")[0].contentWindow;
}

/**
 * 刷新当前菜单所在iframe
 */
function reloadFrame() {
    getFrameWindow().location.reload(true);
}

function getFrameWindow() {
    return top.$(".layui-tab[lay-filter='main-tab'] .layui-show iframe:first")[0].contentWindow;
}

/**
 * 跳转到登录页
 * @param code 状态码（见：ResponseCode）
 */
function gotoLoginPage(code) {
    code = code || 0;
    top.window.location = ctx + "/nnl/loginAgain?code=" + code;
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
        layuiTable.reloadData(tableId, {
            where: where
        });
    });
}

function layuiTableReload(layuiTable, tableId, where) {
    let config = layuiTable.getConfig(tableId);
    let oldWhere = config.where;
    if (where && oldWhere['orderName']) {
        where['orderName'] = oldWhere['orderName']
        where['order'] = oldWhere['order']
    }
    let page = config.page;
    if (page) {
        page = {
            curr: 1
        }
    }
    layuiTable.reloadData(tableId, {
        page: page,
        where: where
    });
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
        let excelAble = true;
        if (typeof item.excel == "boolean" && !item.excel) {
            excelAble = false;
        }
        if (excelAble) {
            colNames[item.field] = {
                title: item.title,
                width: item.exportWidth || 25,
                select: item.exportSelect || false,
                selectArray: item.exportSelectArray || []
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

/**
 * 清除表单上的校验
 * @param selector 选择器
 */
function clearFormValid(selector) {
    $(selector).attr("novalid", true);
}

/**
 * 添加表单上的校验
 * @param selector 选择器
 */
function addFormValid(selector) {
    $(selector).removeAttr("novalid");
}
