/**
 * home
 */
layui.use([ 'element' ], function() {
	$("#echarts-operateType").width($(document).width()/2 - 50).height($(document).height() - 50);
	$("#echarts-visitor").width($(document).width()/2 - 50).height($(document).height() - 50);
	var echartsOperateTypeData;
	var echartsVisitorData;
	$.ajax({
		url : _ctx + "sysOperateLog/statisticsOperateLog",
		type : "get",
		async : false,
		success : function(result) {
			echartsOperateTypeData = result.data;
		}
	});
	var legendData = [];
	var seriesData = [];
	for ( var i in echartsOperateTypeData) {
		var item = echartsOperateTypeData[i];
		var operateTypeName = item.operateTypeName;
		legendData.push(operateTypeName);
		var seriesDataItem = {
			name : operateTypeName,
			value : item.operateTypeCount
		}
		seriesData.push(seriesDataItem);
	}
	var echartsOperateType = echarts.init(document.getElementById('echarts-operateType'));
	operateTypeOption = {
		title : {
			text : '操作类型统计',
			left : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b}: {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : legendData
		},
		series : [ {
			type : 'pie',
			radius : [ '40%', '55%' ],
			label : {
				normal : {
					formatter : '{b|{b}：}{c}  {per|{d}%}  ',
					backgroundColor : '#eee',
					borderColor : '#aaa',
					borderWidth : 1,
					borderRadius : 4,
					rich : {
						a : {
							color : '#999',
							lineHeight : 22,
							align : 'center'
						},
						hr : {
							borderColor : '#aaa',
							width : '100%',
							borderWidth : 0.5,
							height : 0
						},
						b : {
							fontSize : 16,
							lineHeight : 33
						},
						per : {
							color : '#eee',
							backgroundColor : '#334455',
							padding : [ 2, 4 ],
							borderRadius : 2
						}
					}
				}
			},
			data : seriesData
		} ]
	};
	echartsOperateType.setOption(operateTypeOption);
	
	// 访问人echarts
	$.ajax({
		url : _ctx + "sysOperateLog/statisticsVisitUser",
		type : "get",
		async : false,
		success : function(result) {
			echartsVisitorData = result.data;
		}
	});
	var visitorXAxisData = [];
	var visitorSeriesData = [];
	for ( var i in echartsVisitorData) {
		var item = echartsVisitorData[i];
		if (item.userName) {
            visitorXAxisData.push(item.userName);
            visitorSeriesData.push(item.operateCount);
        }
	}
	var echartsVisitor = echarts.init(document.getElementById('echarts-visitor'));
	echartsVisitorOption = {
		title : {
			text : '用户访问量统计',
			left : 'center'
		},
	    color: ['#3398DB'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : visitorXAxisData,
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            type:'bar',
	            barWidth: '60%',
	            data:visitorSeriesData
	        }
	    ]
	};
	echartsVisitor.setOption(echartsVisitorOption);
});