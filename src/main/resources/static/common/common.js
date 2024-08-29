/**
 * 公共模块
 */
const CommonUtil = {

    /**
     * 全局ajax提交操作
     * @param _type 接口类型
     * @param _url 接口路径 （必填）
     * @param _data 请求数据 （必填）
     * @param _success 成功回调函数 （非必填）
     * @param _fail 失败回调函数 （非必填）
     * @param _async 是否异步（默认异步）
     * @param _transferJson 是否将参数转换成json（默认转换）
     */
    ajax: function (_type, _url, _data, _success, _fail, _async, _transferJson) {
        $.ajax({
            type: _type,
            url: _url,
            data: _transferJson && typeof(_data) === "object" ? encodeURIComponent(JSON.stringify(_data)) : _data,
            contentType: _transferJson ? 'application/json;charset=UTF-8' : 'application/x-www-form-urlencoded;charset=UTF-8',
            async: typeof (_async) == "undefined" ? true : _async,
            success: function (result) {
                if (result.code === ResponseCode.AUTHORIZED_EXPIRE) {
                    gotoLoginPage(ResponseCode.AUTHORIZED_EXPIRE);
                    return;
                }
                if (_success && typeof _success == "function") {
                    _success(result);
                }
            },
            error: function () {
                if (_fail && typeof _fail == "function") {
                    _fail();
                }
            }
        });
    },

    getSync: function (_url, _data, _success, _fail) {
        CommonUtil.getAjax(_url, _data, _success, _fail, false)
    },

    postSync: function (_url, _data, _success, _fail) {
        CommonUtil.postAjax(_url, _data, _success, _fail, false)
    },

    /**
     * post方式的异步提交操作
     * @param _url 接口路径 （必填）
     * @param _data 请求数据 （必填）
     * @param _success 成功回调函数 （非必填）
     * @param _fail 失败回调函数 （非必填）
     * @param _async 是否异步（默认异步）
     * @param _transferJson 是否将参数转换成json（默认转换）
     */
    postAjax: function (_url, _data, _success, _fail, _async, _transferJson) {
        _transferJson = typeof (_transferJson) == "undefined" ? true : _transferJson;
        CommonUtil.ajax(AjaxType.POST, _url, _data, _success, _fail, _async, _transferJson)
    },

    /**
     * get方式的异步提交操作
     * @param _url 接口路径 （必填）
     * @param _data 参数
     * @param _success 成功回调函数 （非必填）
     * @param _fail 失败回调函数 （非必填）
     * @param _async 是否异步（默认异步）
     */
    getAjax: function (_url, _data, _success, _fail, _async) {
        CommonUtil.ajax(AjaxType.GET, _url, _data, _success, _fail, _async, false)
    },

    /**
     * put方式的异步提交操作
     * @param _url 接口路径 （必填）
     * @param _data 请求数据 （必填）
     * @param _success 成功回调函数 （非必填）
     * @param _fail 失败回调函数 （非必填）
     * @param _async 是否异步（默认异步）
     */
    putAjax: function (_url, _data, _success, _fail, _async) {
        CommonUtil.ajax(AjaxType.PUT, _url, _data, _success, _fail, _async, true)
    },

    /**
     * patch方式的异步提交操作
     * @param _url 接口路径 （必填）
     * @param _data 请求数据 （必填）
     * @param _success 成功回调函数 （非必填）
     * @param _fail 失败回调函数 （非必填）
     */
    patchAjax: function (_url, _data, _success, _fail) {
        CommonUtil.ajax(AjaxType.PATCH, _url, _data, _success, _fail, true, true)
    },

    /**
     * delete方式的异步提交操作
     * @param _url 接口路径 （必填）
     * @param _data 请求数据 （必填）
     * @param _success 成功回调函数 （非必填）
     * @param _fail 失败回调函数 （非必填）
     */
    deleteAjax: function (_url, _data, _success, _fail) {
        CommonUtil.ajax(AjaxType.DELETE, _url, _data, _success, _fail, true, false)
    },

    exportExcel: function (exportParam) {
        let index = top.layer.loadingWithText("正在导出数据到Excel，请稍后...");
        CommonUtil.postAjax(ctx + '/generateExportFile', exportParam, function (result) {
            top.layer.close(index);
            if (result.ok) {
                window.location = ctx + '/downloadExcelFile?filePath=' + encodeURIComponent(result.data);
            }
        });
    },

    downloadTemplate: function (templateParam) {
        let index = top.layer.loadingWithText("正在下载模板，请稍后...");
        CommonUtil.postAjax(ctx + '/generateTemplate', templateParam, function (result) {
            top.layer.close(index);
            if (result.ok) {
                window.location = ctx + '/downloadExcelFile?filePath=' + encodeURIComponent(result.data) + "&fileName=" + encodeURIComponent(templateParam.generateName);
            }
        });
    },

    /**
     * http 响应成功
     * @param res
     * @returns {boolean}
     */
    respSuccess: function (res) {
        return res.code === ResponseCode.SUCCESS || res.code === ResponseCode.DATA_BAK_RECOVER;
    },

    /**
     * 从对象数组中获取指定属性
     * @param array
     * @param attrName 属性名
     * @returns {[]}
     */
    getAttrFromArray: function (array, attrName) {
        let attr = [];
        array.forEach(item => {
            attr.push(item[attrName]);
        })
        return attr;
    },

    /**
     * 分组属性
     * @param array
     */
    groupAttrFromArray: function (array, attrNames) {
        let resultArr = [];
        attrNames.forEach(attrName => {
            let attrValArr = [];
            array.forEach(item => {
                attrValArr.push(item[attrName])
            });
            resultArr.push(attrValArr);
        });

        return resultArr;
    },

    /**
     * 通过逗号拼接字符串数组
     * @param array
     * @param split
     * @returns {*}
     */
    joinMulti: function (array, split) {
        split = split || ',';
        let result = "";
        let arrLen = array.length;
        for (let i = 0; i < arrLen; i++) {
            result += split + array[i];
        }
        return result.substring(1);
    },

    /**
     * 通过逗号拼接字符串数组，显示几个长度
     * @param array
     * @param len
     * @param split
     * @returns {string}
     */
    joinMultiByLen: function (array, len, split) {
        split = split || ',';
        let result = "";
        let arrLen = array.length;
        for (let i = 0; i < arrLen; i++) {
            if (i > len - 1) {
                result += " 等 " + arrLen + " 条数据";
                break;
            }
            result += split + array[i];
        }
        return result.substring(1);
    },

    /**
     * 对象数组转化为 key->value 的Object对象
     * @param array
     * @param key
     * @param value
     */
    covertFromArray: function (array, key, value) {
        let obj = {};
        for (let i = 0; i < array.length; i++) {
            let item = array[i];
            obj[item[key]] = item[value];
        }
        return obj;
    },

    /**
     * 从数组中移除指定数据
     * @param array
     * @param key
     * @param value
     */
    removeArrayItem: function (array, key, value) {
        if (value.constructor === Array) {
            for (let i = 0; i < value.length; i++) {
                const index = array.findIndex(text => text[key] == value[i]);
                array.splice(index, 1);
            }
        } else {
            const index = array.findIndex(text => text[key] == value);
            array.splice(index, 1);
        }
    },

    getTemplateTypeName: function (type) {
        if (type == 1) {
            return TemplateType.VALIDATE_CODE;
        } else if (type == 2) {
            return TemplateType.ALARM;
        } else if (type == 3) {
            return TemplateType.NOTIFY;
        }
    },

    /**
     * 移除字符串中的非数字字符
     */
    removeNotNumStr: function (str) {
        str = str || '';
        if (str) {
            str = str.toString().replace(/\D/g, '');
        }
        return str;
    },

    /**
     * 将字节byte长度转化为易读的KB/MB/GB
     * @param byteSize 字节长度
     */
    convertByte: function (byteSize) {
        let size = "";
        if (byteSize < 0.1 * 1024) { //如果小于0.1KB转化成B
            size = byteSize.toFixed(2) + " B";
        } else if (byteSize < 0.1 * 1024 * 1024) {//如果小于0.1MB转化成KB
            size = (byteSize / 1024).toFixed(2) + " KB";
        } else if (byteSize < 0.1 * 1024 * 1024 * 1024) { //如果小于0.1GB转化成MB
            size = (byteSize / (1024 * 1024)).toFixed(2) + " MB";
        } else { //其他转化成GB
            size = (byteSize / (1024 * 1024 * 1024)).toFixed(2) + " GB";
        }

        let sizeStr = size + "";
        let len = sizeStr.indexOf("\.");
        let dec = sizeStr.substr(len + 1, 2);
        if (dec === "00") {//当小数点后为00时 去掉小数部分
            return sizeStr.substring(0, len) + sizeStr.substr(len + 3, 2);
        }
        return sizeStr;
    },

    /**
     * 将KB/MB/GB长度转化为byte
     * @param sizeStr KB/MB/GB长度
     */
    convertToByte: function (sizeStr) {
        let unit = sizeStr.substring(sizeStr.length - 2).toUpperCase();
        let size = sizeStr.substring(0, sizeStr.length - 2);
        if (unit === "KB") {
            size = size * 1024;
        } else if (unit === "MB") {
            size = size * 1024 * 1024;
        } else if (unit === "GB") {
            size = size * 1024 * 1024 * 1024;
        } else {
            // byte
            size = sizeStr;
        }
        return size;
    },

    /**
     * 获取websocket协议
     */
    getWsProtocol: function () {
        return window.location.protocol === "https:" ? "wss" : "ws";
    },

    /**
     * 获取文件名后缀
     */
    getFileSuf: function (fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    },

    /**
     * 选择用户树
     * @param option 参数选项
     * <p> title: 弹窗标题（不填默认显示‘选择用户’）</p>
     * <p> success: 保存后的回调函数</p>
     * <p> checkbar: 是否开启复选框，true 或 false，默认false</p>
     * <p> notExistsUserGroupId: 过滤已经存在于该用户组ID的用户</p>
     */
    chooseUserTree: function (option) {
        let checkbar = option.checkbar || false;
        let notExistsUserGroupId = option.notExistsUserGroupId || '';
        let currentLayer = LayerUtil.openLayer({
            title: option.title || '选择用户',
            content: ctx + "/user/chooseUserTree",
            area: ['30%', '80%'],
            btn: ['选择', '取消'],
            loaded: function (iframeWin) {
                iframeWin.getParams = function () {
                    return {
                        notExistsUserGroupId: notExistsUserGroupId,
                        checkbar: checkbar
                    };
                };
            },
            submit: function (iframeWin) {
                if (typeof option.success == "function") {
                    option.success(iframeWin.getCurrentNode());
                    currentLayer.closeAll();
                }
            }
        });
    },

    /**
     * 选择部门
     * @param option 参数选项
     * <p> title: 弹窗标题（不填默认显示‘选择部门’）</p>
     * <p> success: 保存后的回调函数</p>
     * <p> checkbar: 是否开启复选框，true 或 false，默认false</p>
     */
    chooseDept: function (option) {
        let title = option.title;
        let checkbar = option.checkbar || false;
        let currentLayer = LayerUtil.openLayer({
            title: title || '选择部门',
            content: ctx + "/organization/chooseDept?checkbar=" + checkbar,
            area: ['30%', '80%'],
            btn: ['选择', '取消'],
            submit: function (iframeWin) {
                if (typeof option.success == "function") {
                    option.success(iframeWin.getCurrentNode());
                    currentLayer.closeAll();
                }
            }
        });
    },

    /**
     * 回车键事件
     * @param selector 选择器
     * @param fun 回调函数
     */
    enterKeyEvent: function (selector, fun) {
        $(selector).keyup(function (event) {
            if (event.keyCode === 13) {
                fun();
            }
        });
    },

    /**
     * 切换皮肤
     * @param $jq jquery对象
     * @param type 皮肤色（black: 黑；blue: 蓝）
     */
    changeSkin: function ($jq, type) {
        type = type || "black";
        if (!$jq) {
            return;
        }
        $jq(".skinLink").html('<link rel="stylesheet" href="' + ctx + '/static/common/css/skin-' + type + '.css" />');
        if ($jq("iframe").length > 0) {
            $jq.each($jq("iframe"), function () {
                CommonUtil.changeSkin(this.contentWindow.$, type);
            });
        }
    }
};

const EditorUtil = {
    init: function (config) {
        config = $.extend({
            selector: '#tinymce',
            language: 'zh_CN',
            plugins: 'preview searchreplace autolink directionality visualchars fullscreen image link media code table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount emoticons',
            toolbar: 'code forecolor backcolor bold italic underline strikethrough link anchor | \
                                    alignleft aligncenter alignright indent2em lineheight | \
                                    bullist numlist | blockquote subscript superscript removeformat | \
                                    table image media bdmap emoticons charmap hr pagebreak insertdatetime \
                                    | fullscreen',
            fontsize_formats: '11px 12px 14px 16px 18px 24px 36px 48px',
            menubar: false,
            autosave_ask_before_unload: false
        }, config);

        tinymce.init(config);
    }
};

const DateUtil = {

    /**
     * 获取年月日
     * @param date 时间对象
     */
    getDate: function (date) {
        date = date || new Date();
        return date.format('yyyy-MM-dd');
    },

    /**
     * 获取上一周
     * @param date 时间对象
     */
    getLastWeek: function (date) {
        return this.getBeforeDate(date, 7);
    },

    /**
     * 获取 n 天前
     * @param date 时间对象
     * @param mount 数量
     */
    getBeforeDate: function (date, mount) {
        date = date || new Date();
        return new Date(date.getTime() - mount * 24 * 3600 * 1000).format('yyyy-MM-dd hh:mm:ss');
    },

    /**
     * 获取上一个月
     * @param date 时间对象
     */
    getLastMonth: function (date) {
        return this.getBeforeMonth(date, 1);
    },

    /**
     * 获取 n 个月前
     * @param date 时间对象
     * @param mount 数量
     */
    getBeforeMonth: function (date, mount) {
        date = date || new Date();
        date.setMonth(date.getMonth() - mount);
        return date.format('yyyy-MM-dd hh:mm:ss');
    },

    /**
     * 获取去年
     * @param date 时间对象
     */
    getLastYear: function (date) {
        date = date || new Date();
        date.setMonth(date.getMonth() - 12);
        return date.format('yyyy-MM-dd hh:mm:ss');
    },

    /**
     * 格式化时间
     * @param date 时间对象
     * @param format 格式化样式
     */
    format: function (date, format) {
        format = format || 'yyyy-MM-dd hh:mm:ss';
        return date.format(format);
    },
};

const TreeUtil = {
    render: function (dtree, config) {
        return dtree.render($.extend({
            dataStyle: "layuiStyle",
            skin: "laySimple",
            record: true,
            ficon: ["1", "8"],
            spreadSelected: false,// 伸缩时是否选中状态
            response: {
                title: "name", //节点名称
                childName: "childes" //子节点名称
            }
        }, config));
    }
};

/**
 * XmSelect工具
 */
const SelectUtil = {
    render: function (xmSelect, option) {
        // 单选
        let radio = option.radio || false;
        let treeExpandedKeys = option.treeExpandedKeys || [];
        let tree = option.tree || false;
        if (typeof (tree) == "boolean" && tree) {
            option.tree = {
                strict: false,
                show: true,
                showLine: false,
                clickExpand: false,
                expandedKeys: treeExpandedKeys
            };
            option.prop = {
                value: 'id',
                children: 'childes'
            };
        }
        option.clickClose = radio;

        return xmSelect.render(option);
    }
};
