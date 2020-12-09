<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/operateLogList.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-form" lay-filter="log-search">
        <div class="layui-form-item">
            <#--搜索条件-->
            <span class="search-where">
                <div class="layui-inline">
                    <label class="layui-form-label">描述内容</label>
                    <div class="layui-input-block" style="width: 120px">
                        <input type="text" name="description" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">操作类型</label>
                    <div class="layui-input-block" style="width: 120px">
                        <select name="operateType">
                            <option value=""></option>
                            <option value="-1">退出系统</option>
                            <option value="1">登录系统</option>
                            <option value="3">新增</option>
                            <option value="4">修改</option>
                            <option value="5">删除</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">操作状态</label>
                    <div class="layui-input-block" style="width: 100px">
                        <select name="operateStatus">
                            <option value=""></option>
                            <option value="1">成功</option>
                            <option value="0">失败</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">操作时间</label>
                    <div class="layui-input-block" style="width: 300px">
                        <input type="text" class="layui-input" id="operateTime" placeholder=" - " autocomplete="off">
                        <input type="hidden" id="operateTime_start" name="operateTime_start">
                        <input type="hidden" id="operateTime_end" name="operateTime_end">
                    </div>
                </div>
            </span>
            <#--搜索栏-->
            <span class="searcher">
                <div class="layui-inline">
                    <button id="btnSearch" type="button" class="layui-btn layui-btn-normal"><i class="layui-icon layui-icon-search"></i>搜索</button>
                </div>
            </span>
        </div>
    </div>
    <div class="layui-col-xs12">
        <script type="text/html" id="toolbar">
        </script>
        <table class="layui-hide" id="log-list-tab" lay-filter="log">
        </table>
    </div>
</div>
</body>
</html>
