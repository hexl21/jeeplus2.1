<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
	<head>
		<meta charset="utf-8" />
    	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<link rel="stylesheet" href="css/base.css" />
		<link rel="stylesheet" href="css/style.css" />
		<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
		<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
		<script type="text/javascript" src="js/book.js" ></script>
        <script type="text/javascript">
            $(function () {
                //类别展示
                $.ajax({
                    url: "${pageContext.request.contextPath}/selectAllCategory",
                    dataType: "JSON",
                    type: "post",
                    async: false,
                    success: function (dta) {

                        // alert("1");
                        console.log(dta.rows);
                        var lbulidhtm = "";
                        for (var i = 0; i < dta.rows.length; i++) {
                            lbulidhtm += "<li><p>" + dta.rows[i].name + "</p></li>\n";
                        }
                        $("#lbulid").html(lbulidhtm);
                    },
                });
                //类别展示=======================

                fun();//初始==>查询全部

                var cname;
                var sex;
                var state;
                var sss;

                //类别条件
                $("#lbulid li").click(function () {
                    cname = $(this).find("p").eq(0).text();
                    // alert(lb);
                    fun(cname, sex, state, sss);
                });

                //性别条件
                $("#sexid li").click(function () {
                    sex = $(this).find("p").eq(0).text();
                    // alert(sex=="女生");
                    if (sex == "女生") {
                        sex = 0;
                    } else if (sex == "男生") {
                        sex = 1;
                    } else if (sex == "全部") {
                        sex = undefined;
                    }
                    fun(cname, sex, state, sss);
                });

                //状态条件
                $("#stateid li").click(function () {
                    state = $(this).find("p").eq(0).text();
                    if (state == "连载中") {
                        state = 1;
                    } else if (state == "已完结") {
                        state = 0;
                    } else if (state == "全部") {
                        state = undefined;
                    }
                    fun(cname, sex, state, sss);
                });

                //（全部/新书）条件
                $("#sssid li").click(function () {
                    sss = $(this).find("p").eq(0).text();
                    if (sss == "全部") {

                        cname = undefined;
                        sex = undefined;
                        state = undefined;
                        sss = undefined;
                        parent.location.reload();

                    } else if (sex == "新书") {
                        sss = "sss";
                    }
                    fun(cname, sex, state, sss);
                });

            });

            //查询方法
            function fun(cname, sex, state, sss) {
                //条件查询======(START)=====
                $.ajax({
                    url: "${pageContext.request.contextPath}/selectConditionBooks",
                    // data:{cname:"都市言情",sex:"1",state:"0",sss:"null"},
                    data: {cname: cname, sex: sex, state: state, sss: sss},
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        // alert("1");
                        console.log(dta.rows);
                        var selectConditionBookshtm = "";
                        for (var i = 0; i < dta.rows.length; i++) {
                            selectConditionBookshtm += "<div class=\"lunbo-book\"><a href=\"book-details-a.jsp\">\n" +
                                "\t\t\t<div class=\"lunb-img\"><img src=\"" + dta.rows[i].bookPic + "\" /></div>\n" +
                                "\t\t\t<div class=\"lunb-txt\">\n" +
                                "\t\t\t\t<h3>" + dta.rows[i].bookName + "</h3>\n" +
                                "\t\t\t\t<p>" + dta.rows[i].bookIntro + "</p>\n" +
                                "\t\t\t\t<p class=\"lunb-style\">" + dta.rows[i].category.name + "</p>\n" +
                                "\t\t\t\t<p>" + dta.rows[i].bookReadnumber + "人阅读</p>\n" +
                                "\t\t\t</div>\n" +
                                "\t\t</a></div>";
                        }
                        $("#selectConditionBooksdiv").html(selectConditionBookshtm);


                    },
                });

                //条件查询=======(END)======
            }

        </script>
		<title>书库</title>
	</head>
<body style="background: #f1f1f1;">
<div class="index-head">
<div class="header">
	<ul>
		<li><a href="index.jsp">
				<i><img src="images/index-h1.png"/></i>
				<span>首页</span>
		</a></li>
		<li class="on"><a href="library.jsp">
				<i><img src="images/index-h2.png"/></i>
				<span>书库</span>
		</a></li>
		<li><a href="list.jsp">
				<i><img src="images/index-h3.png"/></i>
				<span>榜单</span>
		</a></li>
		<li><a href="search.jsp">
				<i><img src="images/index-h4.png"/></i>
				<span>搜索</span>
		</a></li>
		<li><a href="top-up.jsp">
				<i><img src="images/index-h5.png"/></i>
				<span>充值</span>
		</a></li>
	</ul>
</div>
<div class="index-top" id="indextop"><img src="images/index-top.png" width="100%"/></div>
</div>
<div class="library">
	<div class="index-center clearfix">
		<div class="cen-left">
			<i><img src="images/index-tou.png"/></i>
			<span>Lucky</span>
		</div>
        <a class="cen-a" href="personal.jsp">个人中心</a>
	</div>
	<div class="lib-style">
		<div class="lib-div">
			<span>分类</span>
			<div class="lib-ul">
                <ul id="sssid">
					<li class="on"><p>全部</p></li>
					<li><p>新书</p></li>
				</ul>
                <ul class="lib-ulb" id="lbulid">
                    <%--<li><p>都市言情</p></li>
                    <li><p>乡村激情</p></li>
                    <li><p>奇幻玄幻</p></li>
                    <li><p>重生穿越</p></li>
                    <li><p>科幻灵异</p></li>
                    <li><p>武侠仙侠</p></li>
                    <li><p>游戏竞技</p></li>
                    <li><p>历史军事</p></li>--%>
				</ul>
			</div>			
		</div>
		<div class="lib-div">
			<span>类型</span>
			<div class="lib-ul">
                <ul id="sexid">
					<li class="on"><p>全部</p></li>
					<li><p>男生</p></li>
					<li><p>女生</p></li>
				</ul>
			</div>			
		</div>
		<div class="lib-div">
			<span>进度</span>
			<div class="lib-ul">
                <ul id="stateid">
					<li class="on"><p>全部</p></li>
					<li><p>连载中</p></li>
					<li><p>已完结</p></li>
				</ul>
			</div>			
		</div>
	</div>
	<div class="lib-book">
		<div class="lib-tou">
			<h3>分类阅读</h3>
		</div>
        <div id="selectConditionBooksdiv">

            <%--<div class="lunbo-book"><a href="book-details-a.jsp">
                <div class="lunb-img"><img src="images/index-book3.png" width="254" height="361" /></div>
                <div class="lunb-txt">
                    <h3>因为痛，所以叫婚姻</h3>
                    <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
                    <p class="lunb-style">都市言情</p>
                    <p>4613人阅读</p>
                </div>
            </a></div>

            <div class="lunbo-book"><a href="book-details-a.jsp">
                <div class="lunb-img"><img src="images/index-book3.png"/></div>
                <div class="lunb-txt">
                    <h3>因为痛，所以叫婚姻</h3>
                    <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
                    <p class="lunb-style">都市言情</p>
                    <p>4613人阅读</p>
                </div>
            </a></div>--%>


        </div>
	</div>
</div>
</body>
</html>
