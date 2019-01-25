<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>管理章节管理</title>
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
                jp.post("${ctx}/ebook/chapter/save", $('#inputForm').serialize(), function (data) {
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
<form:form id="inputForm" modelAttribute="chapter" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">所属图书ID：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/books/books/data" id="books" name="books.id" value="${chapter.books.id}"
                                labelName="books.bookName" labelValue="${chapter.books.bookName}"
                                title="选择所属图书ID" cssClass="form-control " fieldLabels="书名|备注"
                                fieldKeys="bookName|remarks" searchLabels="书名|备注"
                                searchKeys="bookName|remarks"></sys:gridselect>
            </td>
            <td class="width-15 active"><label class="pull-right">章节名：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">内容：</label></td>
            <td class="width-35">
                <form:textarea path="content" htmlEscape="false" rows="4" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">章节路径：</label></td>
            <td class="width-35">
                <sys:fileUpload path="sectionpath" value="${chapter.sectionpath}" type="file"
                                uploadPath="/ebook/chapter"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">是否付费：</label></td>
            <td class="width-35">
                <form:select path="charge" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('charge')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">书币：</label></td>
            <td class="width-35">
                <form:input path="money" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
            <td class="width-35">
                <form:textarea path="remarks" htmlEscape="false" rows="4" class="form-control "/>
            </td>
            <td class="width-15 active"></td>
            <td class="width-35"></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>