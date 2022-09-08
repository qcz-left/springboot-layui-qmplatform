<!DOCTYPE html>
<html lang="en">
<#assign params = ["echarts"]>
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/other/billList.js"></script>
<style>
    .statistics-div .layui-panel {
        height: 300px;
        margin: 10px;
    }

    #billTypeView, #billDateView {
        width: 100%;
        height: 100%;
        padding: 10px;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-form" lay-filter="bill-search">
        <div class="layui-form-item">
            <span class="search-where">
                <div class="layui-inline">
                    <label class="layui-form-label">账单类型</label>
                    <div class="layui-input-inline">
                        <div id="typeId"></div>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">消费人</label>
                    <div class="layui-input-inline">
                        <input type="text" name="consumer" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-inline">
                        <input type="text" name="remark" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">消费时间</label>
                </div>
                <div class="layui-inline">
                    <input type="text" class="layui-input" id="consumeTimeStart" name="consumeTimeStart">
                </div>
                <div class="layui-inline">
                    -
                </div>
                <div class="layui-inline">
                    <input type="text" class="layui-input" id="consumeTimeEnd" name="consumeTimeEnd">
                </div>
            </span>
            <span class="searcher">
                <div class="layui-inline">
                    <button id="btnSearch" type="button" class="layui-btn layui-btn-normal"><i class="layui-icon layui-icon-search"></i>搜索</button>
                </div>
            </span>
        </div>
    </div>
    <div class="layui-col-xs8">
        <div class="layui-col-xs12">
            <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>记一笔</button>
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </div>
            </script>
            <script type="text/html" id="operator">
            <button class="layui-btn layui-btn-sm" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</button>
            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </script>
            <table class="layui-hide" id="bill-list-tab" lay-filter="bill"></table>
        </div>
    </div>
    <div class="layui-col-xs4 statistics-div">
        <div class="layui-panel">
            <div id="billTypeView"></div>
        </div>
        <div class="layui-panel">
            <div id="billDateView"></div>
        </div>
    </div>
</div>
</body>
</html>
