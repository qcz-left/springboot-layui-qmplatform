<!DOCTYPE html>
<html lang="en">
<#assign params = ["echarts"]>
<#include "/include/include.ftl">
<script type="text/javascript">
$(function () {
    temperatureView();
    dashboardView();
    clockView();

    function clockView() {
        let chartDom = document.getElementById('clockView');
        let myChart = echarts.init(chartDom);
        let option;

        option = {
            series: [
                {
                    name: 'hour',
                    type: 'gauge',
                    startAngle: 90,
                    endAngle: -270,
                    min: 0,
                    max: 12,
                    splitNumber: 12,
                    clockwise: true,
                    axisLine: {
                        lineStyle: {
                            width: 15,
                            color: [[1, 'rgba(0,0,0,0.7)']],
                            shadowColor: 'rgba(0, 0, 0, 0.5)',
                            shadowBlur: 15
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            shadowColor: 'rgba(0, 0, 0, 0.3)',
                            shadowBlur: 3,
                            shadowOffsetX: 1,
                            shadowOffsetY: 2
                        }
                    },
                    axisLabel: {
                        fontSize: 12,
                        distance: 25,
                        formatter: function (value) {
                            if (value === 0) {
                                return '';
                            }
                            return value + '';
                        }
                    },
                    anchor: {
                        show: true,
                        icon: 'path://M532.8,70.8C532.8,70.8,532.8,70.8,532.8,70.8L532.8,70.8C532.7,70.8,532.8,70.8,532.8,70.8z M456.1,49.6c-2.2-6.2-8.1-10.6-15-10.6h-37.5v10.6h37.5l0,0c2.9,0,5.3,2.4,5.3,5.3c0,2.9-2.4,5.3-5.3,5.3v0h-22.5c-1.5,0.1-3,0.4-4.3,0.9c-4.5,1.6-8.1,5.2-9.7,9.8c-0.6,1.7-0.9,3.4-0.9,5.3v16h10.6v-16l0,0l0,0c0-2.7,2.1-5,4.7-5.3h10.3l10.4,21.2h11.8l-10.4-21.2h0c6.9,0,12.8-4.4,15-10.6c0.6-1.7,0.9-3.5,0.9-5.3C457,53,456.7,51.2,456.1,49.6z M388.9,92.1h11.3L381,39h-3.6h-11.3L346.8,92v0h11.3l3.9-10.7h7.3h7.7l3.9-10.6h-7.7h-7.3l7.7-21.2v0L388.9,92.1z M301,38.9h-10.6v53.1H301V70.8h28.4l3.7-10.6H301V38.9zM333.2,38.9v10.6v10.7v31.9h10.6V38.9H333.2z M249.5,81.4L249.5,81.4L249.5,81.4c-2.9,0-5.3-2.4-5.3-5.3h0V54.9h0l0,0c0-2.9,2.4-5.3,5.3-5.3l0,0l0,0h33.6l3.9-10.6h-37.5c-1.9,0-3.6,0.3-5.3,0.9c-4.5,1.6-8.1,5.2-9.7,9.7c-0.6,1.7-0.9,3.5-0.9,5.3l0,0v21.3c0,1.9,0.3,3.6,0.9,5.3c1.6,4.5,5.2,8.1,9.7,9.7c1.7,0.6,3.5,0.9,5.3,0.9h33.6l3.9-10.6H249.5z M176.8,38.9v10.6h49.6l3.9-10.6H176.8z M192.7,81.4L192.7,81.4L192.7,81.4c-2.9,0-5.3-2.4-5.3-5.3l0,0v-5.3h38.9l3.9-10.6h-53.4v10.6v5.3l0,0c0,1.9,0.3,3.6,0.9,5.3c1.6,4.5,5.2,8.1,9.7,9.7c1.7,0.6,3.4,0.9,5.3,0.9h23.4h10.2l3.9-10.6l0,0H192.7z M460.1,38.9v10.6h21.4v42.5h10.6V49.6h17.5l3.8-10.6H460.1z M541.6,68.2c-0.2,0.1-0.4,0.3-0.7,0.4C541.1,68.4,541.4,68.3,541.6,68.2L541.6,68.2z M554.3,60.2h-21.6v0l0,0c-2.9,0-5.3-2.4-5.3-5.3c0-2.9,2.4-5.3,5.3-5.3l0,0l0,0h33.6l3.8-10.6h-37.5l0,0c-6.9,0-12.8,4.4-15,10.6c-0.6,1.7-0.9,3.5-0.9,5.3c0,1.9,0.3,3.7,0.9,5.3c2.2,6.2,8.1,10.6,15,10.6h21.6l0,0c2.9,0,5.3,2.4,5.3,5.3c0,2.9-2.4,5.3-5.3,5.3l0,0h-37.5v10.6h37.5c6.9,0,12.8-4.4,15-10.6c0.6-1.7,0.9-3.5,0.9-5.3c0-1.9-0.3-3.7-0.9-5.3C567.2,64.6,561.3,60.2,554.3,60.2z',
                        showAbove: false,
                        offsetCenter: [0, '-35%'],
                        size: 40,
                        keepAspect: true,
                        itemStyle: {
                            color: '#707177'
                        }
                    },
                    pointer: {
                        icon: 'path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z',
                        width: 12,
                        length: '55%',
                        offsetCenter: [0, '8%'],
                        itemStyle: {
                            color: '#C0911F',
                            shadowColor: 'rgba(0, 0, 0, 0.3)',
                            shadowBlur: 8,
                            shadowOffsetX: 2,
                            shadowOffsetY: 4
                        }
                    },
                    detail: {
                        show: false
                    },
                    title: {
                        offsetCenter: [0, '30%']
                    },
                    data: [
                        {
                            value: 0
                        }
                    ]
                },
                {
                    name: 'minute',
                    type: 'gauge',
                    startAngle: 90,
                    endAngle: -270,
                    min: 0,
                    max: 60,
                    clockwise: true,
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    },
                    pointer: {
                        icon: 'path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z',
                        width: 8,
                        length: '70%',
                        offsetCenter: [0, '8%'],
                        itemStyle: {
                            color: '#C0911F',
                            shadowColor: 'rgba(0, 0, 0, 0.3)',
                            shadowBlur: 8,
                            shadowOffsetX: 2,
                            shadowOffsetY: 4
                        }
                    },
                    anchor: {
                        show: true,
                        size: 20,
                        showAbove: false,
                        itemStyle: {
                            borderWidth: 15,
                            borderColor: '#C0911F',
                            shadowColor: 'rgba(0, 0, 0, 0.3)',
                            shadowBlur: 8,
                            shadowOffsetX: 2,
                            shadowOffsetY: 4
                        }
                    },
                    detail: {
                        show: false
                    },
                    title: {
                        offsetCenter: ['0%', '-40%']
                    },
                    data: [
                        {
                            value: 0
                        }
                    ]
                },
                {
                    name: 'second',
                    type: 'gauge',
                    startAngle: 90,
                    endAngle: -270,
                    min: 0,
                    max: 60,
                    animationEasingUpdate: 'bounceOut',
                    clockwise: true,
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    },
                    pointer: {
                        icon: 'path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z',
                        width: 4,
                        length: '85%',
                        offsetCenter: [0, '8%'],
                        itemStyle: {
                            color: '#C0911F',
                            shadowColor: 'rgba(0, 0, 0, 0.3)',
                            shadowBlur: 8,
                            shadowOffsetX: 2,
                            shadowOffsetY: 4
                        }
                    },
                    anchor: {
                        show: true,
                        size: 15,
                        showAbove: true,
                        itemStyle: {
                            color: '#C0911F',
                            shadowColor: 'rgba(0, 0, 0, 0.3)',
                            shadowBlur: 8,
                            shadowOffsetX: 2,
                            shadowOffsetY: 4
                        }
                    },
                    detail: {
                        show: false
                    },
                    title: {
                        offsetCenter: ['0%', '-40%']
                    },
                    data: [
                        {
                            value: 0
                        }
                    ]
                }
            ]
        };
        setInterval(function () {
            var date = new Date();
            var second = date.getSeconds();
            var minute = date.getMinutes() + second / 60;
            var hour = (date.getHours() % 12) + minute / 60;
            option.animationDurationUpdate = 300;
            myChart.setOption({
                series: [
                    {
                        name: 'hour',
                        animation: hour !== 0,
                        data: [{value: hour}]
                    },
                    {
                        name: 'minute',
                        animation: minute !== 0,
                        data: [{value: minute}]
                    },
                    {
                        animation: second !== 0,
                        name: 'second',
                        data: [{value: second}]
                    }
                ]
            });
        }, 1000);

        option && myChart.setOption(option);
    }

    function dashboardView() {
        let chartDom = document.getElementById('dashboardView');
        let myChart = echarts.init(chartDom);
        let option;

        option = {
            series: [
                {
                    type: 'gauge',
                    axisLine: {
                        lineStyle: {
                            width: 30,
                            color: [
                                [0.3, '#67e0e3'],
                                [0.7, '#37a2da'],
                                [1, '#fd666d']
                            ]
                        }
                    },
                    pointer: {
                        itemStyle: {
                            color: 'auto'
                        }
                    },
                    axisTick: {
                        distance: -30,
                        length: 8,
                        lineStyle: {
                            color: '#fff',
                            width: 2
                        }
                    },
                    splitLine: {
                        distance: -30,
                        length: 30,
                        lineStyle: {
                            color: '#fff',
                            width: 4
                        }
                    },
                    axisLabel: {
                        color: 'auto',
                        distance: 40,
                        fontSize: 10
                    },
                    detail: {
                        valueAnimation: true,
                        formatter: '{value} km/h',
                        color: 'auto',
                        fontSize: 13
                    },
                    data: [
                        {
                            value: 70
                        }
                    ]
                }
            ]
        };
        setInterval(function () {
            myChart.setOption({
                series: [
                    {
                        data: [
                            {
                                value: +(Math.random() * 100).toFixed(2)
                            }
                        ]
                    }
                ]
            });
        }, 2000);

        option && myChart.setOption(option);
    }

    function temperatureView() {
        let chartDom = document.getElementById('temperatureView');
        let myChart = echarts.init(chartDom);
        let option;

        const colors = ['#5470C6', '#91CC75', '#EE6666'];
        option = {
            color: colors,
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross'
                }
            },
            grid: {
                right: '20%'
            },
            legend: {
                data: ['蒸发量', '降水量', '温度']
            },
            xAxis: [
                {
                    type: 'category',
                    axisTick: {
                        alignWithLabel: true
                    },
                    data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '蒸发量',
                    position: 'right',
                    alignTicks: true,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: colors[0]
                        }
                    },
                    axisLabel: {
                        formatter: '{value} ml'
                    }
                },
                {
                    type: 'value',
                    name: '降水量',
                    position: 'right',
                    alignTicks: true,
                    offset: 80,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: colors[1]
                        }
                    },
                    axisLabel: {
                        formatter: '{value} ml'
                    }
                },
                {
                    type: 'value',
                    name: '温度',
                    position: 'left',
                    alignTicks: true,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: colors[2]
                        }
                    },
                    axisLabel: {
                        formatter: '{value} °C'
                    }
                }
            ],
            series: [
                {
                    name: '蒸发量',
                    type: 'bar',
                    data: [
                        2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3
                    ]
                },
                {
                    name: '降水量',
                    type: 'bar',
                    yAxisIndex: 1,
                    data: [
                        2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3
                    ]
                },
                {
                    name: '温度',
                    type: 'line',
                    yAxisIndex: 2,
                    data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
                }
            ]
        };

        option && myChart.setOption(option);
    }
});
</script>
<style>
    html {
        height: unset;
    }

    .layui-container {
        width: 100%;
        min-width: 1135px;
    }

    .collect-data .layui-panel {
        padding: 30px;
        height: 100px;
    }

    .temperature-data .layui-panel {
        padding: 30px;
        height: 300px;
    }

    .dashboard-data .layui-panel {
        height: 300px;
    }

    .layui-panel .layui-row, .layui-panel .layui-row .layui-col-xs6 {
        height: 100%;
    }

    .layui-panel .layui-row .layui-col-xs6 div {
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
<div class="layui-container layui-fluid">
    <div class="collect-data layui-row">
        <div class="layui-col-xs3">
            <div class="layui-panel">
                <div class="layui-row">
                    <div class="layui-col-xs6"><i class="layui-icon layui-icon-user" style="color: #21a4b9;"></i></div>
                    <div class="layui-col-xs6">
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
        <div class="layui-col-xs3">
            <div class="layui-panel">
                <div class="layui-row">
                    <div class="layui-col-xs6"><i class="layui-icon layui-icon-reply-fill" style="color: #6c5050;"></i></div>
                    <div class="layui-col-xs6">
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
        <div class="layui-col-xs3">
            <div class="layui-panel">
                <div class="layui-row">
                    <div class="layui-col-xs6"><i class="layui-icon layui-icon-rmb" style="color: #a70a0a;"></i></div>
                    <div class="layui-col-xs6">
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
        <div class="layui-col-xs3">
            <div class="layui-panel">
                <div class="layui-row">
                    <div class="layui-col-xs6"><i class="layui-icon layui-icon-cart-simple" style="color: #4a7e05;"></i></div>
                    <div class="layui-col-xs6">
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
    <div class="dashboard-data layui-row">
        <div class="layui-col-xs6">
            <div class="layui-panel">
                <div style="width: 100%; height: 100%" id="dashboardView" class="vh-center"></div>
            </div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-panel">
                <div style="width: 100%; height: 100%" id="clockView" class="vh-center"></div>
            </div>
        </div>
    </div>
    <div class="temperature-data layui-row">
        <div class="layui-col-xs12">
            <div class="layui-panel">
                <div style="width: 100%; height: 100%" id="temperatureView" class="vh-center"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
