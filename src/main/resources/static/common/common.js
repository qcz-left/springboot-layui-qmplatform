/**
 * 公共模块
 */
const CommonUtil = {

    getSync: function (_url, _data, _success, _fail) {
        CommonUtil.getAjax(_url, _data, _success, _fail, false)
    },

    postSync: function (_url, _data, _success, _fail) {
        CommonUtil.postAjax(_url, _data, _success, _fail, false)
    },

    /**
     * post方式的异步提交操作
     * _url : 接口路径 （必填）
     * _data : 请求数据 （必填）
     * _success : 成功回调函数 （非必填）
     * _fail : 失败回调函数 （非必填）
     * _async : 是否异步（默认异步）
     */
    postAjax: function (_url, _data, _success, _fail, _async) {
        $.ajax({
            type: AjaxType.POST,
            url: _url,
            async: typeof (_async) == "undefined" ? true : _async,
            data: JSON.stringify(_data),
            contentType: 'application/json;charset=UTF-8',
            success: function (msg) {
                if (_success && typeof _success == "function") {
                    _success(msg);
                }
            },
            error: function () {
                if (_fail && typeof _fail == "function") {
                    _fail();
                }
            }
        });
    },

    /**
     * get方式的异步提交操作
     * _url : 接口路径 （必填）
     * _success : 成功回调函数 （非必填）
     * _fail : 失败回调函数 （非必填）
     * _async : 是否异步（默认异步）
     */
    getAjax: function (_url, _data, _success, _fail, _async) {
        $.ajax({
            type: AjaxType.GET,
            url: _url,
            data: _data,
            async: typeof (_async) == "undefined" ? true : _async,
            success: function (msg) {
                if (_success && typeof _success == "function") {
                    _success(msg);
                }
            },
            error: function () {
                if (_fail && typeof _fail == "function") {
                    _fail();
                }
            }
        });
    },

    /**
     * put方式的异步提交操作
     * _url : 接口路径 （必填）
     * _data : 请求数据 （必填）
     * _success : 成功回调函数 （非必填）
     * _fail : 失败回调函数 （非必填）
     */
    putAjax: function (_url, _data, _success, _fail) {
        $.ajax({
            type: AjaxType.PUT,
            url: _url,
            data: JSON.stringify(_data),
            contentType: 'application/json;charset=UTF-8',
            success: function (msg) {
                if (_success && typeof _success == "function") {
                    _success(msg);
                }
            },
            error: function () {
                if (_fail && typeof _fail == "function") {
                    _fail();
                }
            }
        });
    },

    /**
     * patch方式的异步提交操作
     * _url : 接口路径 （必填）
     * _data : 请求数据 （必填）
     * _success : 成功回调函数 （非必填）
     * _fail : 失败回调函数 （非必填）
     */
    patchAjax: function (_url, _data, _success, _fail) {
        $.ajax({
            type: AjaxType.PATCH,
            url: _url,
            data: JSON.stringify(_data),
            contentType: 'application/json;charset=UTF-8',
            success: function (msg) {
                if (_success && typeof _success == "function") {
                    _success(msg);
                }
            },
            error: function () {
                if (_fail && typeof _fail == "function") {
                    _fail();
                }
            }
        });
    },

    postOrPut: function (_isPut, _url, _data, _success, _fail) {
        $.ajax({
            type: _isPut ? AjaxType.PUT : AjaxType.POST,
            url: _url,
            data: JSON.stringify(_data),
            contentType: 'application/json;charset=UTF-8',
            success: function (msg) {
                if (_success && typeof _success == "function") {
                    _success(msg);
                }
            },
            error: function () {
                if (_fail && typeof _fail == "function") {
                    _fail();
                }
            }
        });
    },

    /**
     * delete方式的异步提交操作
     * _url : 接口路径 （必填）
     * _data : 请求数据 （必填）
     * _success : 成功回调函数 （非必填）
     * _fail : 失败回调函数 （非必填）
     */
    deleteAjax: function (_url, _data, _success, _fail) {
        $.ajax({
            type: AjaxType.DELETE,
            url: _url,
            data: _data,
            success: function (msg) {
                if (_success && typeof _success == "function") {
                    _success(msg);
                }
            },
            error: function () {
                if (_fail && typeof _fail == "function") {
                    _fail();
                }
            }
        });
    },

    /**
     * js动态导入static静态文件（js、css）
     * resourceNames ：资源名称，多个以逗号“,”隔开（如：'layui,vue'）
     */
    loadStaticResources: function (resourceNames) {
        let resourceNameArr = resourceNames.split(",");
        for (let i in resourceNameArr) {
            let item = resourceNameArr[i];
            if (item == "layui") {
                document.write("<link rel='stylesheet' href='" + ctx + "/static/plugin/layui/css/layui.css'>");
                document.write("<script type='text/javascript' src='" + ctx + "/static/plugin/layui/layui.js'><\/script>");
            } else if (item == "jquery") {
                document.write("<script type='text/javascript' src='" + ctx + "/static/plugin/jquery/jquery-2.2.2.min.js'><\/script>");
            } else if (item == "echarts") {
                document.write("<script type='text/javascript' src='" + ctx + "/static/plugin/echarts/echarts.min.js'><\/script>");
            } else if (item == "layer") {
                document.write("<link rel='stylesheet' href='" + ctx + "/static/plugin/layer/layer-v3.1.0/theme/default/layer.css'>");
                document.write("<script type='text/javascript' src='" + ctx + "/static/plugin/layer/layer-v3.1.0/layer.js'><\/script>");
            } else if (item == "vue") {
                document.write("<script type='text/javascript' src='" + ctx + "/static/plugin/vuejs/vue-2.3.0/vue.min.js'><\/script>");
            }
        }
    },

    /**
     * http 响应成功
     * @param res
     * @returns {boolean}
     */
    respSuccess: function (res) {
        return res.code === 200 || res.code === 201;
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
     * 从数据种移除指定数据
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
    }

}
