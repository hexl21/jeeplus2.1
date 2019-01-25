<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>管理收藏管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">

        $(document).ready(function () {

        });

        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if (!isValidate) {
                return false;
            } else {
                jp.loading();
                jp.post("${ctx}/ebook/enshrine/save", $('#inputForm').serialize(), function (data) {
                    if (data.success) {
                        jp.getParent().refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    } else {
                        jp.error(data.msg);
                    }
                })
            }

        }
    </script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="enshrine" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">书名：</label></td>
            <td class="width-35">
                <form:input path="bookname" htmlEscape="false" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">图书ID：</label></td>
            <td class="width-35">
                <form:input path="bookid" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">图书封面：</label></td>
            <td class="width-35">
                <sys:fileUpload path="bookpic" value="${enshrine.bookpic}" type="file" uploadPath="/ebook/enshrine"/>
            </td>
            <td class="width-15 active"><label class="pull-right">章节名：</label></td>
            <td class="width-35">
                <form:input path="chaptername" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">章节ID：</label></td>
            <td class="width-35">
                <form:input path="chapterid" htmlEscape="false" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">用户ID：</label></td>
            <td class="width-35">
                <form:input path="userid" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">当前系统时间：</label></td>
            <td class="width-35">
                <form:input path="date" htmlEscape="false" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
            <td class="width-35">
                <form:textarea path="remarks" htmlEscape="false" rows="4" class="form-control "/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>