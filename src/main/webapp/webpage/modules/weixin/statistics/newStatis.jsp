<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>图文数据统计</title>
    <meta name="decorator" content="ani"/>
    <script src="${ctxStatic}/plugin/echarts3/echarts.min.js" type="text/javascript"></script>

</head>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">图文数据统计</h3>
        </div>
        <div class="panel-body">
            <div class="ibox-content">
                <div id="main" style="width: 1200px;height: 400px;"></div>
            </div>
        </div>
    </div>
</div>
<script>
    var myChart = echarts.init(document.getElementById("main"));
    myChart.setOption({
        title: {
            text: '近3天图文数据统计',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['阅读人数', '阅读次数', '分享人数', '分享次数', '收藏人数', '收藏次数']
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
                name: '阅读人数',
                type: 'bar',
                data:  ${int_page_read_user},

            },
            {
                name: '阅读次数',
                type: 'bar',
                data: ${int_page_read_count},

            },
            {
                name: '分享人数',
                type: 'bar',
                data: ${share_user},

            },
            {
                name: '分享次数',
                type: 'bar',
                data: ${share_count},

            },
            {
                name: '收藏人数',
                type: 'bar',
                data: ${add_to_fav_user},

            },
            {
                name: '收藏次数',
                type: 'bar',
                data: ${add_to_fav_count},

            }
        ]
    });
</script>
</body>
</html>