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

                //轮播图======(START)=====
                $.ajax({
                    url: "${pageContext.request.contextPath}/slideshowBooks",
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        //alert("2");
                        console.log(dta.rows);

                        var slideshowBookshtm = "";

                        for (var i = 0; i < dta.rows.length; i++) {

                            console.log(dta.rows[i].id);


                            // "<a onclick='chapters(\""+dta.rows[i].id+"\")'>\n"
                            slideshowBookshtm = "<a href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[i].id + "\">\n" +
                                "\t\t\t\t<div class=\"lunb-img\"><img src=\"" + dta.rows[i].bookPic + "\"/></div>\n" +
                                "\t\t\t\t<div class=\"lunb-txt\">\n" +
                                "\t\t\t\t\t<h3>" + dta.rows[i].bookName + "</h3>\n" +
                                "\t\t\t\t\t<p>" + dta.rows[i].bookIntro + "</p>\n" +
                                "\t\t\t\t\t<p class=\"lunb-style\">" + dta.rows[i].category.name + "</p>\n" +
                                "\t\t\t\t\t<p>" + dta.rows[i].bookReadnumber + "人阅读</p>\n" +
                                "\t\t\t\t</div>\n" +
                                "\t\t\t</a>";

                            $("#slideshowBooksdiv0" + [i] + "").html(slideshowBookshtm);
                        }


                    },
                });

                //轮播图=======(END)======

                //主编推荐======(START)=====
                $.ajax({
                    url: "${pageContext.request.contextPath}/recommendBooks",
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        //alert("2");
                        // console.log(dta.rows.length);

                        var recommendBookshtm = "<ul>";
                        for (var i = 0; i < 3; i++) {  //${pageContext.request.contextPath}/selectOneBookDetails?id="+dta.rows[i].id+"
                            recommendBookshtm += "<li>\n" +
                                "\t\t\t\t<a class=\"eda-img\" href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[i].id + "\"><img src=\"" + dta.rows[i].bookPic + "\"/></a>\n" +
                                "\t\t\t\t<a class=\"sea-p\" href=\"###\">" + dta.rows[i].bookName + "</a>\n" +
                                "\t\t\t</li>";
                        }
                        recommendBookshtm += "</ul>";
                        $("#recommendBooksdiv01").html(recommendBookshtm);


                        var recommendBookshtm2 = "";
                        for (var i = 3; i < dta.rows.length; i++) {
                            recommendBookshtm2 += "<a href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[i].id + "\">\n" +
                                "\t\t\t<span class=\"ed-a\">" + dta.rows[i].bookName + "</span>\n" +
                                "\t\t\t<span class=\"ed-b\">" + dta.rows[i].category.name + "</span>\n" +
                                "\t\t</a>";
                        }
                        $("#recommendBooksdiv02").html(recommendBookshtm2);

                    },
                });

                //主编推荐=======(END)======


                //新书上架======(START)=====
                //alert("1");
                $.ajax({
                    url: "${pageContext.request.contextPath}/newBooks",
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        //alert("2");
                        // console.log(dta.rows.length);

                        var newbookhtm = "<a href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[0].id + "\">\n" +
                            "\t\t<div class=\"lunb-img\"><img src=\"" + dta.rows[0].bookPic + "\"/></div>\n" +
                            "\t\t<div class=\"lunb-txt\">\n" +
                            "\t\t\t<h3>" + dta.rows[0].bookName + "</h3>\n" +
                            "\t\t\t<p>" + dta.rows[0].bookIntro + "</p>\n" +
                            "\t\t\t<p class=\"lunb-style\">" + dta.rows[0].category.name + "</p>\n" +
                            "\t\t\t<p>" + dta.rows[0].bookReadnumber + "人阅读</p>\n" +
                            "\t\t</div>\n" +
                            "\t</a>"
                        $("#newbookdiv01").html(newbookhtm);
                        var newbookhtm2 = "";
                        for (var i = 1; i < dta.rows.length; i++) {
                            newbookhtm2 += "<a href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[i].id + "\">\n" +
                                "\t\t\t<span class=\"ed-a\">" + dta.rows[i].bookName + "</span>\n" +
                                "\t\t\t<span class=\"ed-b\">" + dta.rows[i].category.name + "</span>\n" +
                                "\t\t</a>";
                        }
                        $("#newbookdiv02").html(newbookhtm2);

                    },
                });
                //新书上架=======(END)======


                //连载精品======(START)=====
                $.ajax({
                    url: "${pageContext.request.contextPath}/serialBooks",
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        //alert("2");
                        // console.log(dta.rows);

                        var serialBookshtm = "<a href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[0].id + "\">\n" +
                            "\t\t<div class=\"lunb-img\"><img src=\"" + dta.rows[0].bookPic + "\"/></div>\n" +
                            "\t\t<div class=\"lunb-txt\">\n" +
                            "\t\t\t<h3>" + dta.rows[0].bookName + "</h3>\n" +
                            "\t\t\t<p>" + dta.rows[0].bookIntro + "</p>\n" +
                            "\t\t\t<p class=\"lunb-style\">" + dta.rows[0].category.name + "</p>\n" +
                            "\t\t\t<p>" + dta.rows[0].bookReadnumber + "人阅读</p>\n" +
                            "\t\t</div>\n" +
                            "\t</a>"
                        $("#serialBooksdiv01").html(serialBookshtm);
                        var serialBookshtm2 = "";
                        for (var i = 1; i < dta.rows.length; i++) {
                            serialBookshtm2 += "<a href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[i].id + "\">\n" +
                                "\t\t\t<span class=\"ed-a\">" + dta.rows[i].bookName + "</span>\n" +
                                "\t\t\t<span class=\"ed-b\">" + dta.rows[i].category.name + "</span>\n" +
                                "\t\t</a>";
                        }
                        $("#serialBooksdiv02").html(serialBookshtm2);

                    },
                });
                //连载精品=======(END)======

                //完结精品======(START)=====
                $.ajax({
                    url: "${pageContext.request.contextPath}/completionBooks",
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        //alert("2");
                        // console.log(dta.rows);

                        var completionBookshtm = "<a href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[0].id + "\">\n" +
                            "\t\t<div class=\"lunb-img\"><img src=\"" + dta.rows[0].bookPic + "\"/></div>\n" +
                            "\t\t<div class=\"lunb-txt\">\n" +
                            "\t\t\t<h3>" + dta.rows[0].bookName + "</h3>\n" +
                            "\t\t\t<p>" + dta.rows[0].bookIntro + "</p>\n" +
                            "\t\t\t<p class=\"lunb-style\">" + dta.rows[0].category.name + "</p>\n" +
                            "\t\t\t<p>" + dta.rows[0].bookReadnumber + "人阅读</p>\n" +
                            "\t\t</div>\n" +
                            "\t</a>"
                        $("#completionBooksdiv01").html(completionBookshtm);
                        var completionBookshtm2 = "";
                        for (var i = 1; i < dta.rows.length; i++) {
                            completionBookshtm2 += "<a href=\"${pageContext.request.contextPath}/selectOneBookDetails?id=" + dta.rows[i].id + "\">\n" +
                                "\t\t\t<span class=\"ed-a\">" + dta.rows[i].bookName + "</span>\n" +
                                "\t\t\t<span class=\"ed-b\">" + dta.rows[i].category.name + "</span>\n" +
                                "\t\t</a>";
                        }
                        $("#completionBooksdiv02").html(completionBookshtm2);

                    },
                });
                //完结精品=======(END)======


            })

            //图书章节信息
            function chapters(obj) {
                alert(obj);

            }

            //图书章节信息==========

		</script>
		<title>首页</title>
	</head>
<body style="background: #f1f1f1;">
<div class="index-head">
<div class="header">
	<ul>
		<li class="on"><a href="index.jsp">
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
<div class="index-center clearfix">
	<div class="cen-left">
		<i><img src="images/index-tou.png"/></i>
		<span>Lucky</span>
	</div>
	<a class="cen-a" href="personal.jsp">个人中心</a>
</div>
<div class="index-lunbo">
	<p class="index-time"><i><img src="images/index-a6.png"/></i>上次阅读到：《官场红颜路》第1章</p>

	<div id="focus" class="focus">
		<div class="bd" id="slideshowBooksdiv">
			<div class="lunbo-book" id="slideshowBooksdiv00">
				<a href="${pageContext.request.contextPath}/selectOneBookDetails?id=">
					<div class="lunb-img"><img src="images/index-book3.png" width="254"/></div>
				<div class="lunb-txt">
					<h3>因为痛，所以叫婚姻</h3>
					<p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
					<p class="lunb-style">都市言情</p>
					<p>4613人阅读</p>
				</div>
				</a>
			</div>

			<div class="lunbo-book" id="slideshowBooksdiv01">
				<%--<a href="book-details-a.jsp">
                <div class="lunb-img"><img src="images/index-book3.png"/></div>
                <div class="lunb-txt">
                    <h3>因为痛，所以叫婚姻</h3>
                    <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
                    <p class="lunb-style">都市言情</p>
                    <p>4614人阅读</p>
                </div>
            </a>--%>
			</div>

			<div class="lunbo-book" id="slideshowBooksdiv02">
				<%--<a href="book-details-a.jsp">
                <div class="lunb-img"><img src="images/index-book3.png"/></div>
                <div class="lunb-txt">
                    <h3>因为痛，所以叫婚姻</h3>
                    <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
                    <p class="lunb-style">都市言情</p>
                    <p>4615人阅读</p>
                </div>
            </a>--%>
			</div>

			<div class="lunbo-book" id="slideshowBooksdiv03">
				<%-- <a href="book-details-a.jsp">
                 <div class="lunb-img"><img src="images/index-book3.png"/></div>
                 <div class="lunb-txt">
                     <h3>因为痛，所以叫婚姻</h3>
                     <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
                     <p class="lunb-style">都市言情</p>
                     <p>4616人阅读</p>
                 </div>
             </a>--%>
			</div>

			<div class="lunbo-book" id="slideshowBooksdiv04">
				<%--<a href="book-details-a.jsp">
                <div class="lunb-img"><img src="images/index-book3.png"/></div>
                <div class="lunb-txt">
                    <h3>因为痛，所以叫婚姻</h3>
                    <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
                    <p class="lunb-style">都市言情</p>
                    <p>4617人阅读</p>
                </div>
            </a>--%>
			</div>

		</div>
		<div class="hd">
			<ul></ul>
		</div>
	</div>			
</div>
<div class="editor">
	<div class="index-tou clearfix">		
		<h3><i><img src="images/index-a4.png"/></i>主编推荐</h3>
	</div>
	<div class="edit-img" id="recommendBooksdiv01">
		<ul>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book2.png"/></a>
				<a class="sea-p" href="###">夏至</a>
			</li>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book1.png"/></a>
				<a class="sea-p" href="###">宫心锁</a>
			</li>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book4.png"/></a>
				<a class="sea-p" href="###">谪醉相思</a>
			</li>
		</ul>
	</div>
	<div class="edit-txt" id="recommendBooksdiv02">
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
	</div>
</div>
<!--新书上架-->
<div class="newbook editor">
	<div class="index-tou clearfix">		
		<h3><i><img src="images/index-a1.png"/></i>新书上架</h3>
		<p>火热强推</p>
	</div>
	<div class="lunbo-book" id="newbookdiv01"><a href="book-details-a.jsp">
		<div class="lunb-img"><img src="images/index-book3.png"/></div>
		<div class="lunb-txt">
			<h3>因为痛，所以叫婚姻</h3>
			<p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
			<p class="lunb-style">都市言情</p>
			<p>4613人阅读</p>
		</div>
	</a></div>
	<div class="edit-txt" id="newbookdiv02">
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
	</div>
</div>
<!--完结精品-->
<div class="over editor">
	<div class="index-tou clearfix">		
		<h3><i><img src="images/index-a2.png"/></i>完结精品</h3>
		<p>致命诱惑</p>
	</div>
	<div class="lunbo-book" id="completionBooksdiv01"><a href="book-details-a.jsp">
		<div class="lunb-img"><img src="images/index-book3.png"/></div>
		<div class="lunb-txt">
			<h3>因为痛，所以叫婚姻</h3>
			<p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
			<p class="lunb-style">都市言情</p>
			<p>4613人阅读</p>
		</div>
	</a></div>
	<div class="edit-txt" id="completionBooksdiv02">
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
	</div>
</div>
<!--连载精品-->
<div class="serial editor">
	<div class="index-tou clearfix">		
		<h3><i><img src="images/index-a3.png"/></i>连载精品</h3>
	</div>
	<div class="lunbo-book" id="serialBooksdiv01"><a href="book-details-a.jsp">
		<div class="lunb-img"><img src="images/index-book3.png"/></div>
		<div class="lunb-txt">
			<h3>因为痛，所以叫婚姻</h3>
			<p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
			<p class="lunb-style">都市言情</p>
			<p>4613人阅读</p>
		</div>
	</a></div>
	<div class="edit-txt" id="serialBooksdiv02">
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
		<a href="###">
			<span class="ed-a">凉生、我们可不可以不忧伤</span>
			<span class="ed-b">都市言情</span>
		</a>
	</div>
</div>
<!--限时免费-->
<div class="free editor">
	<div class="index-tou clearfix">		
		<h3><i><img src="images/index-a5.png"/></i>限时免费</h3>
		<p>还剩01天10小时12分37秒</p>
	</div>
	<div class="lunbo-book"><a href="book-details-a.jsp">
		<div class="lunb-img"><img src="images/index-book3.png"/></div>
		<div class="lunb-txt">
			<h3>因为痛，所以叫婚姻</h3>
			<p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
			<p class="lunb-style">都市言情</p>
			<p>4613人阅读</p>
		</div>
	</a></div>
	<div class="edit-img">
		<ul>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book2.png"/></a>
				<a class="sea-p" href="###">夏至</a>
			</li>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book1.png"/></a>
				<a class="sea-p" href="###">宫心锁</a>
			</li>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book4.png"/></a>
				<a class="sea-p" href="###">谪醉相思</a>
			</li>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book2.png"/></a>
				<a class="sea-p" href="###">夏至</a>
			</li>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book1.png"/></a>
				<a class="sea-p" href="###">宫心锁</a>
			</li>
			<li>
				<a class="eda-img" href="###"><img src="images/index-book4.png"/></a>
				<a class="sea-p" href="###">谪醉相思</a>
			</li>
		</ul>
	</div>
</div>
<div class="footer clearfix">
	<div class="foot-txt">
		<p>工作时间：09:00-23:00</p>
		<p>联系方式：2278679075</p>
		<p>公众号：看书吧</p>
	</div>
	<div class="foot-qr"><img src="images/index-qr.png" width="100%"/></div>
</div>
<script type="text/javascript">
	TouchSlide({slideCell:"#focus",titCell:".hd ul", mainCell:".bd", effect:"left", autoPlay:true, autoPage:true });
</script>
</body>
</html>
