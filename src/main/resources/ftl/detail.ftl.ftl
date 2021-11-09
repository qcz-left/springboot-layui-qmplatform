<!DOCTYPE html>
<html lang="en">
${a!'<#include "/include/include.ftl">'}
<body>
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="${table.entityPath}-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">名称</label>
            <div class="layui-input-block">
                <input type="text" name="name" lay-verify="required" placeholder="请输入名称" class="layui-input">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form']);
</script>
</body>
</html>
