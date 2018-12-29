<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>用户属性统计</title>
    <meta name="decorator" content="ani"/>
    <script src="${ctxStatic}/plugin/echarts3/echarts.min.js" type="text/javascript"></script>

</head>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">用户属性统计</h3>
        </div>
        <div class="panel-body">
            <div class="col-sm-6 ui-sortable">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div id="main" style="width: 500px;height: 300px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 ui-sortable">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div id="main1" style="width: 500px;height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12 ui-sortable">
                <div class="ibox float-e-margins">

                    <div class="ibox-content">
                        <div id="main2" style="width: 1500px;height: 300px;"></div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<script>
    $(function () {
        //自适应设置
        $("#main2").css("width", $(window).width() - 300);
    });

    $(window).resize(function () {
        $("#main2").css("width", $(window).width() - 300);
    });

    var myChart = echarts.init(document.getElementById("main"));
    var myChart1 = echarts.init(document.getElementById("main1"));
    var myChart2 = echarts.init(document.getElementById("main2"));
    myChart.setOption({
        title: {
            text: "性别分布"
        },
        tooltip: {
            text: "this is tool tip"
        },
        legend: {
            data: ['性别']
        },
        xAxis: {
            data: ["男", "女", "其他"]
        },
        yAxis: {},
        series: [{
            itemStyle: {
                normal: {
                    color: '#4ad2ff'
                }
            },
            type: "bar",
            data: [${wxFans.sex1}, ${wxFans.sex2}, ${wxFans.sex3}]
        }]
    });

    myChart1.setOption({
        title: {
            text: "语言分布"
        },
        tooltip: {
            text: "this is tool tip"
        },
        legend: {
            data: ['语言']
        },
        xAxis: {
            data: ["中文简体", "中文繁体", "英文", "其他"]
        },
        yAxis: {},
        series: [{
            itemStyle: {
                normal: {
                    color: '#4ad2ff'
                }
            },
            type: "bar",
            data: [${wxFans.language1}, ${wxFans.language2+wxFans.language3}, ${wxFans.language4}, ${wxFans.count-(wxFans.language1+wxFans.language2+wxFans.language3)}]
        }]
    });
    myChart2.setOption({
        title: {
            text: "省份分布"
        },
        tooltip: {
            text: "this is tool tip"
        },
        legend: {
            data: ['省份']
        },
        xAxis: {
            data: ${provinceStr}
            , axisLabel: {
                interval: 0,
                rotate: 60,//倾斜度 -90 至 90 默认为0
                margin: 2,
            },
        },
        yAxis: {},
        series: [{
            itemStyle: {
                normal: {
                    color: '#4ad2ff'
                }
            },
            type: "bar",
            data:${provinceCount}
        }]
    });
</script>
</body>
</html>