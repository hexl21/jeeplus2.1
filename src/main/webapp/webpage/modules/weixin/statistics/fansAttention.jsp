<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>关注人数统计</title>
    <meta name="decorator" content="ani"/>
    <script src="${ctxStatic}/plugin/echarts3/echarts.min.js" type="text/javascript"></script>
</head>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">关注人数统计</h3>
        </div>
        <div class="panel-body">
            <div class="ibox-content">
                <div id="main" style="width: 800px;height: 400px;"></div>

            </div>
            <div class="ibox-content">
                <div id="main1" style="width: 800px;height: 400px;"></div>

            </div>
        </div>
    </div>
</div>
<script>
    var myChart = echarts.init(document.getElementById("main"));
    var myChart1 = echarts.init(document.getElementById("main1"));
    myChart.setOption({
        title: {
            text: '近7天用户增减数据',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['新增用户', '取消数量']
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
                data: ${date}
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '新增用户',
                type: 'bar',
                data:  ${add},

            },
            {
                name: '取消数量',
                type: 'bar',
                data: ${cel},

            }
        ]
    });
    myChart1.setOption({
        title: {
            text: '近7天累计用户数据',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['累计用户']
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
                data: ${date1}
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '累计用户',
                type: 'bar',
                data:  ${cum},

            }
        ]
    });
</script>
</body>
</html>