<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/operateLogList.js"></script>
<style>
    .layui-form-label {
        width: unset;
    }
    .layui-input-block {
        margin-left: 100px;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-form" lay-filter="log-search">
        <div class="layui-form-item">
            <#--搜索条件-->
            <span class="search-where">
                <div class="layui-inline">
                    <label class="layui-form-label">操作人</label>
                    <div class="layui-input-block" style="width: 120px">
                        <input type="text" name="operateUserName" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">描述内容</label>
                    <div class="layui-input-block" style="width: 120px">
                        <input type="text" name="description" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">操作类型</label>
                    <div class="layui-input-block" style="width: 130px">
                        <div id="operateType"></div>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">操作状态</label>
                    <div class="layui-input-block" style="width: 120px">
                        <div id="operateStatus"></div>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">操作时间</label>
                </div>
                <div class="layui-inline">
                    <input type="text" class="layui-input" id="operateTime_start" name="operateTime_start">
                </div>
                <div class="layui-inline">
                    -
                </div>
                <div class="layui-inline">
                    <input type="text" class="layui-input" id="operateTime_end" name="operateTime_end">
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
