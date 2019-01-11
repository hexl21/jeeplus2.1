<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>管理用户管理</title>
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
                jp.post("${ctx}/users/users/save", $('#inputForm').serialize(), function (data) {
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
<form:form id="inputForm" modelAttribute="users" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
            <td class="width-35">
                <form:textarea path="remarks" htmlEscape="false" rows="4" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">用户ID：</label></td>
            <td class="width-35">
                <form:input path="userid" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">用户名：</label></td>
            <td class="width-35">
                <form:input path="username" htmlEscape="false" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">密码：</label></td>
            <td class="width-35">
                <form:input path="password" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">书币：</label></td>
            <td class="width-35">
                <form:input path="money" htmlEscape="false" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">用户状态：</label></td>
            <td class="width-35">
                <form:radiobuttons path="state" items="${fns:getDictList('user_state')}" itemLabel="label"
                                   itemValue="value" htmlEscape="false" class="i-checks "/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>