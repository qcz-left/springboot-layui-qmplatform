<!DOCTYPE html>
<html lang="en">
<#assign params = ["sparklines"]>
<#include "/include/include.ftl">
<script type="text/javascript">
$(function () {
    let myvalues = [10, 8, 5, 7, 4, 4, 1];

    $('.inlinesparkline').sparkline(myvalues, {
        type: 'discrete',
        width: '200',
        height: '100'
    });

    $('.dynamicsparkline').sparkline(myvalues, {
        type: 'line',
        width: '200',
        height: '100'
    });

    $('.dynamicbar').sparkline(myvalues, {
        type: 'pie',
        barColor: 'green',
        width: '100',
        height: '100'
    });

    $('.inlinebar').sparkline('html', {
        type: 'bar',
        height: '100',
        barWidth: 20
    });
});
</script>
<style>
    .layui-container {
        width: 100%;
    }
    .layui-panel {
        padding: 30px;
        height: 100px;
    }

    .layui-panel .layui-row, .layui-panel .layui-row .layui-col-lg6 {
        height: 100%;
    }

    .layui-panel .layui-row .layui-col-lg6 div {
        height: 50%;
    }

    .layui-icon {
        font-size: 100px;
    }

    .statistic-title {
        font-family: cursive;
        font-size: 25px;
    }

    .statistic-num {
        font-family: fantasy;
        font-size: 30px;
    }
</style>
<body>
<div class="layui-container">
    <div class="layui-row layui-col-space20">
        <div class="layui-col-lg3">
            <div class="layui-panel">
                <div class="layui-row">
                    <div class="layui-col-lg6"><i class="layui-icon layui-icon-user" style="color: #21a4b9;"></i></div>
                    <div class="layui-col-lg6">
                        <div class="statistic-title vh-center">
                            访问量
                        </div>
                        <div class="statistic-num vh-center">
                            30000
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-lg3">
            <div class="layui-panel">
                <div class="layui-row">
                    <div class="layui-col-lg6"><i class="layui-icon layui-icon-reply-fill" style="color: #6c5050;"></i></div>
                    <div class="layui-col-lg6">
                        <div class="statistic-title vh-center">
                            消息
                        </div>
                        <div class="statistic-num vh-center">
                            40000
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-lg3">
            <div class="layui-panel">
                <div class="layui-row">
                    <div class="layui-col-lg6"><i class="layui-icon layui-icon-rmb" style="color: #a70a0a;"></i></div>
                    <div class="layui-col-lg6">
                        <div class="statistic-title vh-center">
                            金额
                        </div>
                        <div class="statistic-num vh-center">
                            50000
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-lg3">
            <div class="layui-panel">
                <div class="layui-row">
                    <div class="layui-col-lg6"><i class="layui-icon layui-icon-cart-simple" style="color: #4a7e05;"></i></div>
                    <div class="layui-col-lg6">
                        <div class="statistic-title vh-center">
                            订单
                        </div>
                        <div class="statistic-num vh-center">
                            60000
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-row layui-col-space20">
        <div class="layui-col-lg3">
            <div class="layui-panel">
                <span class="inlinesparkline">Loading..</span>
            </div>
        </div>
        <div class="layui-col-lg3">
            <div class="layui-panel">
                <span class="dynamicsparkline">Loading..</span>
            </div>
        </div>
        <div class="layui-col-lg3">
            <div class="layui-panel">
                <span class="dynamicbar">Loading..</span>
            </div>
        </div>
        <div class="layui-col-lg3">
            <div class="layui-panel">
                <span class="inlinebar">1,3,4,5,3,5</span>
            </div>
        </div>
    </div>
</div>
</body>
</html>
