<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>管理图书管理</title>
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
                jp.post("${ctx}/ebook/books/save", $('#inputForm').serialize(), function (data) {
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
<form:form id="inputForm" modelAttribute="books" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">图书封面路径：</label></td>
            <td class="width-35">
                <sys:fileUpload path="bookPic" value="${books.bookPic}" type="file" uploadPath="/ebook/books"/>
            </td>
            <td class="width-15 active"><label class="pull-right">图书名：</label></td>
            <td class="width-35">
                <form:input path="bookName" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">所属类别：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/book_category/category/data" id="category" name="category.id"
                                value="${books.category.id}" labelName="category.name"
                                labelValue="${books.category.name}"
                                title="选择所属类别" cssClass="form-control " fieldLabels="类别名|备注" fieldKeys="name|remarks"
                                searchLabels="类别名|备注" searchKeys="name|remarks"></sys:gridselect>
            </td>
            <td class="width-15 active"><label class="pull-right">简介：</label></td>
            <td class="width-35">
                <form:textarea path="bookIntro" htmlEscape="false" rows="4" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">内容：</label></td>
            <td class="width-35">
                <form:textarea path="bookContent" htmlEscape="false" rows="4" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">阅读人数：</label></td>
            <td class="width-35">
                <form:input path="bookReadnumber" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">图书状态：</label></td>
            <td class="width-35">
                <form:select path="state" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('book_state')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">适合性别：</label></td>
            <td class="width-35">
                <form:select path="sex" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
            <td class="width-35">
                <form:textarea path="remarks" htmlEscape="false" rows="4" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">作者：</label></td>
            <td class="width-35">
                <form:input path="author" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>