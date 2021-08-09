<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    td {
        width: 50%;
    }
</style>
<body class="detail-body">
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">计算机</div>
                <div class="layui-card-body">
                    <table class="layui-table">
                        <tbody>
                        <tr>
                            <td>计算机名称：${computer.computerName!''}</td>
                            <td>计算机地址：${computer.computerIp!''} / ${computer.computerMac!''}</td>
                        </tr>
                        <tr>
                            <td>系统名称：${computer.osName!''}</td>
                            <td>系统类型：${computer.osArch!''}</td>
                        </tr>
                        <tr>
                            <td>上次启动时间：${computer.lastStartTime!''}</td>
                            <td>运行时长：${computer.runTime!''}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">内存</div>
                <div class="layui-card-body">
                    <table class="layui-table">
                        <tbody>
                        <tr>
                            <td>总量：${mem.total!''}</td>
                            <td>已用：${mem.used!''}</td>
                        </tr>
                        <tr>
                            <td>剩余：${mem.free!''}</td>
                            <td>
                                <div class="layui-progress" lay-showpercent="true">
                                    <div class="layui-progress-bar" lay-percent="${mem.usage!'0'}%"></div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">磁盘</div>
                <div class="layui-card-body">
                    <table class="layui-table">
                        <tbody>
                        <tr>
                            <td>总量：${disk.total!''}</td>
                            <td>已用：${disk.used!''}</td>
                        </tr>
                        <tr>
                            <td>剩余：${disk.free!''}</td>
                            <td>
                                <div class="layui-progress" lay-showpercent="true">
                                    <div class="layui-progress-bar" lay-percent="${disk.usage!'0'}%"></div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <#list disk.sysFiles as sysFile>
                        <div class="layui-card">
                            <div class="layui-card-header">${sysFile.typeName} ${sysFile.sysTypeName}</div>
                            <div class="layui-card-body">
                                <table class="layui-table">
                                    <tbody>
                                    <tr>
                                        <td>总量：${sysFile.total!''}</td>
                                        <td>已用：${sysFile.used!''}</td>
                                    </tr>
                                    <tr>
                                        <td>剩余：${sysFile.free!''}</td>
                                        <td>
                                            <div class="layui-progress" lay-showpercent="true">
                                                <div class="layui-progress-bar" lay-percent="${sysFile.usage!'0'}%"></div>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    layui.use('element', function(){

    });
</script>
</body>
</html>
