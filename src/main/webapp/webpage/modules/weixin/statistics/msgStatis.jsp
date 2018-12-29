<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>消息数据统计</title>
    <meta name="decorator" content="ani"/>
    <script src="${ctxStatic}/plugin/echarts3/echarts.min.js" type="text/javascript"></script>
</head>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">消息数据统计</h3>
        </div>
        <div class="panel-body">

            <div class="ibox-content">
                <div id="main" style="width: 800px;height: 400px;"></div>
            </div>
            <div class="ibox-content">
                <div id="main1" style="width: 1200px;height: 400px;"></div>

            </div>
            <div class="ibox-content">
                <div id="main2" style="width: 1200px;height: 400px;"></div>

            </div>
            <div class="ibox-content">
                <div id="main3" style="width: 1200px;height: 400px;"></div>
            </div>
        </div>
    </div>
</div>
<script>
    var myChart = echarts.init(document.getElementById("main"));
    var myChart1 = echarts.init(document.getElementById("main1"));
    var myChart2 = echarts.init(document.getElementById("main2"));
    var myChart3 = echarts.init(document.getElementById("main3"));
    myChart1.setOption({
        title: {
            text: '近1天消息分送分时数据统计',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['用户总数', '消息总数']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: ${ref_hour}
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '用户总数',
                type: 'bar',
                data:  ${msg_user1},

            },
            {
                name: '消息总数',
                type: 'bar',
                data: ${msg_count1},

            }
        ]
    });
    myChart.setOption({
        title: {
            text: '近7天消息发送概况统计',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['用户总数', '消息总数']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: ${ref_date}
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '用户总数',
                type: 'bar',
                data:  ${msg_user},

            },
            {
                name: '消息总数',
                type: 'bar',
                data: ${msg_count},

            }
        ]
    });
    myChart2.setOption({
        title: {
            text: '近15天消息发送分布数据统计',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['用户总数', '分布区间']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: ${ref_date2}
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '用户总数',
                type: 'bar',
                data:  ${msg_user2},

            },
            {
                name: '分布区间',
                type: 'bar',
                data: ${count_interval},

            }
        ]
    });
    myChart3.setOption({
        title: {
            text: '近30天消息发送分布数据统计',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['用户总数', '分布区间']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: ${ref_date3}
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '用户总数',
                type: 'bar',
                data:  ${msg_user3},

            },
            {
                name: '分布区间',
                type: 'bar',
                data: ${count_interval3},

            }
        ]
    });
</script>
</body>
</html>