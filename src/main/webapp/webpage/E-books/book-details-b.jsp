<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webpage/E-books/css/base.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webpage/E-books/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webpage/E-books/css/jquery.editable-select.css"
          rel="stylesheet"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webpage/E-books/js/jquery.1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webpage/E-books/js/jquery.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webpage/E-books/js/TouchSlide.1.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webpage/E-books/js/book.js"></script>
    <script type="text/javascript">

        function funoption() {
            /*用jQuery实现*/
            var checkText = $("#optionid").find("option:selected").text(); //获取Select选择的Text
            var checkValue = $("#optionid").val();
            window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + checkValue;
            // console.log("checkText ==> "+checkText +"     checkValue ==> "+checkValue);
        }


        function buychapat(id) {


            var member = $("#membersid").val();
            // alert(member=="1")
            //会员
            if (member == "1") {
                // alert(member=="1")
                window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + id;
                //非会员
            } else if (member == "0") {
                // alert("非会员  "+id);

                $.ajax({
                    url: "${pageContext.request.contextPath}/selectOneChapterCharge",
                    data: {id: id},
                    dataType: "JSON",
                    type: "post",
                    success: function (dta) {

                        //免费
                        if (dta.charge == "0") {
                            // alert("免费 "+id);
                            window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + id;

                            //付费
                        } else if (dta.charge == "1") {
                            $.ajax({
                                url: "${pageContext.request.contextPath}/selectOneBuychapter",
                                data: {chapterid: id},
                                dataType: "JSON",
                                type: "post",
                                success: function (dta) {
                                    // alert(dta.bool)
                                    //已购买
                                    if (dta.bool) {
                                        window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + id;
                                        // alert("000000000")
                                        //未购买
                                    } else {
                                        var money = $("#moneysid").val();
                                        // alert("书币："+money)
                                        $.ajax({
                                            url: "${pageContext.request.contextPath}/judgeMoney",
                                            data: {id: id},
                                            dataType: "JSON",
                                            type: "post",
                                            success: function (dta) {
                                                // alert(dta.bool);
                                                //书币是否够？
                                                if (dta.bool) {
                                                    var con = confirm("是否购买此章节？");
                                                    if (con) {
                                                        // alert(con)
                                                        $.ajax({
                                                            url: "${pageContext.request.contextPath}/insertChapterAndUpdateUsers",
                                                            data: {chapterid: id},
                                                            dataType: "JSON",
                                                            type: "post",
                                                            success: function (dta) {
                                                                window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + id;
                                                            }
                                                        });
                                                    } else {
                                                        // alert(con)
                                                    }
                                                } else {
                                                    alert("书币不足，请用户充值！")

                                                }
                                            }

                                        });


                                    }
                                }
                            });
                        }

                    },
                });

            }


        }


    </script>
    <title>书籍详情</title>
</head>
<body style="background: #f1f1f1;">
<input type="hidden" id="membersid" value="${sessionScope.rows.users.member}"/>
<input type="hidden" id="moneysid" value="${sessionScope.rows.users.money}"/>
<div class="index-head">
    <div class="header">
        <a href="${pageContext.request.contextPath}/pageSkip?id="></a>
        <ul>
            <li class="on"><a href="${pageContext.request.contextPath}/webpage/E-books/index.jsp">
                <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h1.png"/></i>
                <span>首页</span>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/webpage/E-books/library.jsp">
                <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h2.png"/></i>
                <span>书库</span>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/webpage/E-books/list.jsp">
                <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h3.png"/></i>
                <span>榜单</span>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/webpage/E-books/search.jsp">
                <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h4.png"/></i>
                <span>搜索</span>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/webpage/E-books/top-up.jsp">
                <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h5.png"/></i>
                <span>充值</span>
            </a></li>
        </ul>
    </div>
    <div class="index-top" id="indextop"><img
            src="${pageContext.request.contextPath}/webpage/E-books/images/index-top.png" width="100%"/></div>
</div>
<div class="details">
    <div class="lunbo-book">
        <div class="lunb-img"><img src="${DTO2.bookPic}"/></div>
        <div class="lunb-txt">
            <%--<h1>${sessionScope.id}</h1>--%>
            <h3>${DTO2.bookName}</h3>
            <p>${DTO2.bookIntro}</p>
            <p class="lunb-style">${DTO2.cname}</p>
            <p>${DTO2.bookReadnumber}人阅读</p>
        </div>
    </div>
    <div class="deta-ul">
        <h3><i><img src="${pageContext.request.contextPath}/webpage/E-books/images/deta-bg.png"/></i>目录</h3>
        <ul>
            <c:forEach var="ite" items="${DTO2.chapterLists}">
                <c:if test="${ite.charge=='0'}">
                    <li><a href="javascript:void(0)" onclick="buychapat('${ite.id}')"><span
                            style="width: 100%">${ite.name}</span></a>
                    </li>
                </c:if>
                <c:if test="${ite.charge=='1'}">
                    <li>
                        <a href="javascript:void(0)" onclick="buychapat('${ite.id}')">
                            <span style="width: 100%">${ite.name}<img
                                    src="${pageContext.request.contextPath}/webpage/E-books/images/deta-zs.png"
                                    style="width: 20px;height: 15.46px"/></span>

                        </a>
                    </li>
                </c:if>

            </c:forEach>

            <%--<li><a href="###"><span>第1章</span></a></li>
            <li><a href="###"><span>第2章</span></a></li>
            <li><a href="###"><span>第3章</span></a></li>
            <li><a href="###"><span>第4章</span></a></li>
            <li><a href="###"><span>第5章</span></a></li>
            <li><a href="###"><span>第6章</span></a></li>
            <li><a href="###"><span>第7章</span></a></li>
            <li><a href="###"><span>第8章</span></a></li>
            <li><a href="###"><span>第9章</span><i><img src="images/deta-zs.png"/></i></a></li>
            <li><a href="###"><span>第10章</span><i><img src="images/deta-zs.png"/></i></a></li>
            <li><a href="###"><span>第11章</span><i><img src="images/deta-zs.png"/></i></a></li>
            <li><a href="###"><span>第12章</span><i><img src="images/deta-zs.png"/></i></a></li>
            <li><a href="###"><span>第13章</span><i><img src="images/deta-zs.png"/></i></a></li>
            <li><a href="###"><span>第14章</span><i><img src="images/deta-zs.png"/></i></a></li>
            <li><a href="###"><span>第15章</span><i><img src="images/deta-zs.png"/></i></a></li>--%>
        </ul>
    </div>
    <div class="deta-search">
        <select id="optionid">
            <c:forEach var="ites" items="${DTO2.chapterLists}">
                <option value="${ites.id}">${ites.name}</option>
            </c:forEach>
        </select>
        <input type="button" value="确认" onclick="funoption()"/>
    </div>
</div>
</body>
</html>
