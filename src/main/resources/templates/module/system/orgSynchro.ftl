<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/orgSynchro.js"></script>
<style>
    .layui-form-item label {
        width: 120px;
    }

    .layui-form-item .layui-input-block {
        margin-left: 150px;
        width: 35%;
    }
</style>
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="synchro-form">
        <div class="layui-form-item">
            <label class="layui-form-label">同步开关</label>
            <div class="layui-input-block">
                <input type="checkbox" name="enableSynchro" value=1 lay-skin="switch" lay-filter="enableSynchro">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">同步类型</label>
            <div class="layui-input-block">
                <select name="syncType" lay-verify="required">
                    <option value=""></option>
                    <option value="dingtalk-synchro">钉钉组织架构同步</option>
                    <option value="work-wechat-synchro">企业微信组织架构同步</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">登录名同步方式</label>
            <div class="layui-input-block">
                <select name="loginNameSyncWay" lay-verify="required">
                    <option value=""></option>
                    <option value="usernamePinyin">用户名全拼音</option>
                    <option value="emailPrefix">邮箱前缀</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">同步周期</label>
            <div class="layui-input-block">
                <select name="syncPeriod" lay-verify="required" novalid lay-filter="syncPeriod">
                    <option value=""></option>
                    <option value="day">每天同步</option>
                    <option value="week">每周同步</option>
                    <option value="month">每月同步</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item week-month-content hide">
            <label class="layui-form-label required">同步日期</label>
            <div class="layui-input-block">
                <select name="syncDate" lay-verify="required" lay-filter="syncDate">

                </select>
                <div class="week-content hide">
                    <option value=""></option>
                    <option value="week1">星期天</option>
                    <option value="week2">星期一</option>
                    <option value="week3">星期二</option>
                    <option value="week4">星期三</option>
                    <option value="week5">星期四</option>
                    <option value="week6">星期五</option>
                    <option value="week7">星期六</option>
                </div>
                <div class="month-content hide">
                    <option value=""></option>
                    <option value="dayOfMonthEnd">月末</option>
                    <#list 1..28 as day>
                        <option value="day${day}">${day}</option>
                    </#list>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">同步时间</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input" autocomplete="off" name="syncTime" lay-verify="required">
            </div>
        </div>

        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="save">保存</button>
            <button type="submit" class="layui-btn" lay-submit lay-filter="sync">立即同步</button>
        </div>
    </form>
</div>
</body>
</html>