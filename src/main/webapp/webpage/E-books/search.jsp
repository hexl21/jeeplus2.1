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
                //热门搜索======(START)=====
                $.ajax({
                    url: "${pageContext.request.contextPath}/popularityBooks",
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        //alert("2");
                        console.log(dta.rows);
                        var popularityhtma = "";
                        var popularityhtmb = "";
                        var popularityhtmc = "";
                        for (var i = 0; i < dta.rows.length; i++) {
                            if (i >= 0 && i < 4) {
                                popularityhtma += "<li><a href=\"###\">" + dta.rows[i].bookName + "</a></li>\n";

                            } else if (i >= 4 && i < 8) {
                                popularityhtmb += "<li><a href=\"###\">" + dta.rows[i].bookName + "</a></li>\n";

                            } else if (i >= 8) {
                                popularityhtmc += "<li><a href=\"###\">" + dta.rows[i].bookName + "</a></li>\n";

                            }

                        }
                        $("#popularityida").html(popularityhtma);
                        $("#popularityidb").html(popularityhtmb);
                        $("#popularityidc").html(popularityhtmc);

                    },
                });

                //热门搜索=======(END)======

            });

            //获取文本框内容
            function search() {
                var name = $("#booknameinputid").val();
                if (name != '') {
                    // alert("不空");
                    // alert(name);
                    // alert(name==undefined);
                    // alert(name=='');
                    seekbook(name);
                    $("#booknameinputid").val('');
                    // parent.location.reload();
                } else {
                    alert("请输入书名");
                    parent.location.reload();
                }
            }

            //调用搜索方法
            function seekbook(name) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/seekBooks",
                    data: {name: name},
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        // alert("1");
                        console.log(dta.rows);
                        var seekBookshtm = "";
                        for (var i = 0; i < dta.rows.length; i++) {
                            seekBookshtm += "<div class=\"lunbo-book\"><a href=\"book-details-a.jsp\">\n" +
                                "\t\t\t<div class=\"lunb-img\"><img src=\"" + dta.rows[i].bookPic + "\" /></div>\n" +
                                "\t\t\t<div class=\"lunb-txt\">\n" +
                                "\t\t\t\t<h3>" + dta.rows[i].bookName + "</h3>\n" +
                                "\t\t\t\t<p>" + dta.rows[i].bookIntro + "</p>\n" +
                                "\t\t\t\t<p class=\"lunb-style\">" + dta.rows[i].category.name + "</p>\n" +
                                "\t\t\t\t<p>" + dta.rows[i].bookReadnumber + "人阅读</p>\n" +
                                "\t\t\t</div>\n" +
                                "\t\t</a></div>";
                            ;
                        }
                        $("#seekBooksiddiv").html(seekBookshtm);
                    },
                });
            }
        </script>
		<title>搜索</title>
	</head>
<body>
<div class="index-head">
<div class="header">
	<ul>
		<li><a href="index.jsp">
				<i><img src="images/index-h1.png"/></i>
				<span>首页</span>
		</a></li>
		<li><a href="library.jsp">
				<i><img src="images/index-h2.png"/></i>
				<span>书库</span>
		</a></li>
		<li><a href="list.jsp">
				<i><img src="images/index-h3.png"/></i>
				<span>榜单</span>
		</a></li>
		<li class="on"><a href="search.jsp">
				<i><img src="images/index-h4.png"/></i>
				<span>搜索</span>
		</a></li>
		<li><a href="top-up.jsp">
				<i><img src="images/index-h5.png"/></i>
				<span>充值</span>
		</a></li>
	</ul>
</div>
</div>
<div class="search">
	<h1>搜索</h1>
	<div class="sea-inp">
		<span>书名</span>
        <input id="booknameinputid" type="text" placeholder="请输入书名"/>
	</div>
	<div class="sea-btn">
        <input type="button" value="搜索" onclick="search()"/>
	</div>
	<div class="sea-ul">
		<h3>热门搜索：</h3>
        <ul class="sea-ul-a" id="popularityida">
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
		</ul>
        <ul class="sea-ul-b" id="popularityidb">
			<li><a href="###">高管的未婚妻</a></li>
			<li><a href="###">高管的未婚妻</a></li>
			<li><a href="###">高管的未婚妻</a></li>
			<li><a href="###">高管的未婚妻</a></li>
		</ul>
        <ul class="sea-ul-c" id="popularityidc">
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
		</ul>
	</div>
    <div class="lib-book">
        <div id="seekBooksiddiv">


        </div>
    </div>
</div>
<div class="index-top" id="indextop"><img src="images/index-top.png" width="100%"/></div>
</body>
</html>
