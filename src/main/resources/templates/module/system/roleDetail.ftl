<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body>
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="role-form">
        <input type="hidden" name="roleId">
        <div class="layui-form-item">
            <label class="layui-form-label required">角色名称</label>
            <div class="layui-input-block">
                <input type="text" name="roleName" lay-verify="" placeholder="请输入角色名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">标识</label>
            <div class="layui-input-block">
                <input type="text" name="roleSign" lay-verify="" placeholder="请输入标识" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea" id="remark" name="remark"></textarea>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form']);
</script>
</body>
</html>
