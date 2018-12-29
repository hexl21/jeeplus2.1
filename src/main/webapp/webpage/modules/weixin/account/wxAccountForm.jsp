<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>公众号管理</title>
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
                jp.post("${ctx}/weixin/wxAccount/save", $('#inputForm').serialize(), function (data) {
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
<form:form id="inputForm" modelAttribute="wxAccount" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>公众号名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>公众号账号：</label></td>
            <td class="width-35">
                <form:input path="account" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>appid：</label></td>
            <td class="width-35">
                <form:input path="appid" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>appsecret：</label></td>
            <td class="width-35">
                <form:input path="appsecret" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">商户号：</label></td>
            <td class="width-35">
                <form:input path="mchId" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">商户名称：</label></td>
            <td class="width-35">
                <form:input path="mchName" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">商户密钥：</label></td>
            <td class="width-35">
                <form:input path="mchKey" htmlEscape="false" class="form-control "/>
            </td>
        </tr>

        <tr>
            <td class="width-15 active"><label class="pull-right">验证时用的url：</label></td>
            <td class="width-35">
                <form:input path="url" htmlEscape="false" readonly="true" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">token：</label></td>
            <td class="width-35">
                <form:input path="token" readonly="true" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>