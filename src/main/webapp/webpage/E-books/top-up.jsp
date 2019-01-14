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
		<title>充值</title>
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
		<li><a href="search.jsp">
				<i><img src="images/index-h4.png"/></i>
				<span>搜索</span>
		</a></li>
		<li class="on"><a href="top-up.jsp">
				<i><img src="images/index-h5.png"/></i>
				<span>充值</span>
		</a></li>
	</ul>
</div>
</div>
<div class="top-up">
	<div class="index-center clearfix">
		<div class="cen-left">
            <i><img src="${sessionScope.rows.users.portraitpic}"/></i>
            <span>${sessionScope.rows.users.username}</span>
		</div>
        <a class="cen-a" href="personal.jsp">个人中心</a>
	</div>
	<div class="top-div">
		<div class="top-p">
			<p>账户余额：<span>1000</span>书币</p>
			<p>充值书币：<span>70000</span>书币</p>
		</div>
		<div class="top-h clearfix">
			<h3>请选择充值金额</h3>
			<p>1元=100书币</p>
		</div>
		<div class="top-money">
			<h3 class="top-tou">【温馨提示】：充值包年、包季会员，可免费阅读所有书籍</h3>
			<ul>
				<li>
					<h1 class="top-monh">包年会员</h1>
					<p>365元</p>
					<h2 class="top-monh">每天1元、全年免费</h2>
					<span class="top-lan"><img src="images/top-a.png"/></span>
					<i><img src="images/top-c.png"/></i>
				</li>
				<li>
					<h1 class="top-monh">包季会员</h1>
					<p>180元</p>
					<h2 class="top-monh">每天2元、全季免费</h2>
					<span class="top-lan"><img src="images/top-a.png"/></span>
					<i><img src="images/top-c.png"/></i>
				</li>
				<li class="on">
					<h1 class="top-monh">50元</h1>
					<p>5000+2000书币</p>
					<h2 class="top-monh">送<span>20</span>元</h2>
					<span class="top-hot"><img src="images/top-b.png"/></span>
					<i><img src="images/top-c.png"/></i>
				</li>
				<li>
					<h1 class="top-monh">80元</h1>
					<p>8000+3000书币</p>
					<h2 class="top-monh">送<span>30</span>元</h2>
					<span class="top-lan"><img src="images/top-d.png"/></span>
					<i><img src="images/top-c.png"/></i>
				</li>
				<li>
					<h1 class="top-monh">100元</h1>
					<p>10000+5000书币</p>
					<h2 class="top-monh">送<span>50</span>元</h2>
					<i><img src="images/top-c.png"/></i>
				</li>
				<li>
					<h1 class="top-monh">30元</h1>
					<p>3000+0书币</p>
					<h2 class="top-monh">送<span>0</span>元</h2>
					<i><img src="images/top-c.png"/></i>
				</li>
			</ul>
		</div>
		<div class="top-low clearfix">
			<p>共计￥50</p>
			<input type="button" value="确定支付" />
		</div>
	</div>
</div>
</body>
</html>
