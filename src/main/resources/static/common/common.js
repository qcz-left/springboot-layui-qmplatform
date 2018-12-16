/**
 * 公共模块
 */

var commonUtils = (function() {
	/*
	 * post方式的异步提交操作
	 * _url : 接口路径 （必填）
	 * _data : 请求数据 （必填）
	 * _success : 成功回调函数 （非必填）
	 * _fail : 失败回调函数 （非必填）
	 */
	var _postAjax = function(_url, _data, _success, _fail) {
		$.ajax({
			type : "POST",
			url : _url,
			data : _data,
			success : function(msg) {
				if (_success && typeof _success == "function") {
					_success(msg);
				}
			},
			error : function() {
				if (_fail && typeof _fail == "function") {
					_fail();
				}
			}
		});
	};
	
	/*
	 * get方式的异步提交操作 
	 * _url : 接口路径 （必填） 
	 * _success : 成功回调函数 （非必填）
	 * _fail : 失败回调函数 （非必填）
	 */
	var _getAjax = function(_url, _success, _fail) {
		$.ajax({
			type : "GET",
			url : _url,
			success : function(msg) {
				if (_success && typeof _success == "function") {
					_success(msg);
				}
			},
			error : function() {
				if (_fail && typeof _fail == "function") {
					_fail();
				}
			}
		});
	};
	
	/*
	 * patch方式的异步提交操作
	 * _url : 接口路径 （必填）
	 * _data : 请求数据 （必填）
	 * _success : 成功回调函数 （非必填）
	 * _fail : 失败回调函数 （非必填）
	 */
	var _patchAjax = function(_url, _data, _success, _fail) {
		$.ajax({
			type : "PATCH",
			url : _url,
			data : _data,
			success : function(msg) {
				if (_success && typeof _success == "function") {
					_success(msg);
				}
			},
			error : function() {
				if (_fail && typeof _fail == "function") {
					_fail();
				}
			}
		});
	};
	
	/*
	 * delete方式的异步提交操作
	 * _url : 接口路径 （必填）
	 * _data : 请求数据 （必填）
	 * _success : 成功回调函数 （非必填）
	 * _fail : 失败回调函数 （非必填）
	 */
	var _deleteAjax = function(_url, _data, _success, _fail) {
		$.ajax({
			type : "DELETE",
			url : _url,
			data : _data,
			success : function(msg) {
				if (_success && typeof _success == "function") {
					_success(msg);
				}
			},
			error : function() {
				if (_fail && typeof _fail == "function") {
					_fail();
				}
			}
		});
	};

	/*
	 * js动态导入static静态文件（js、css）
	 * resourceNames ：资源名称，多个以逗号“,”隔开（如：'layui,vue'）
	 */
	var _loadStaticResources = function(resourceNames) {
		var resourceNameArr = resourceNames.split(",");
		for ( var i in resourceNameArr) {
			var item = resourceNameArr[i];
			if (item == "layui") {
				document.write("<link rel='stylesheet' href='" + _ctx + "/static/plugin/layui/css/layui.css'>");
				document.write("<script type='text/javascript' src='" + _ctx + "/static/plugin/layui/layui.js'><\/script>");
			} else if (item == "jquery") {
				document.write("<script type='text/javascript' src='" + _ctx + "/static/plugin/jquery/jquery-2.2.2.min.js'><\/script>");
			} else if (item == "echarts") {
				document.write("<script type='text/javascript' src='" + _ctx + "/static/plugin/echarts/echarts.min.js'><\/script>");
			} else if (item == "layer") {
				document.write("<link rel='stylesheet' href='" + _ctx + "/static/plugin/layer/layer-v3.1.0/theme/default/layer.css'>");
				document.write("<script type='text/javascript' src='" + _ctx + "/static/plugin/layer/layer-v3.1.0/layer.js'><\/script>");
			} else if (item == "vue") {
				document.write("<script type='text/javascript' src='" + _ctx + "/static/plugin/vuejs/vue-2.3.0/vue.min.js'><\/script>");
			}
		}
	}
	
	// 对外调用方法
	return {
		postAjax : _postAjax,
		getAjax : _getAjax,
		patchAjax : _patchAjax,
		deleteAjax : _deleteAjax,
		loadStaticResources : _loadStaticResources
	}
})();