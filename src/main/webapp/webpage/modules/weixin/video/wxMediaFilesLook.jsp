<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>素材信息管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">

        $(document).ready(function () {

        });

    </script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="wxMediaFiles" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="mediaId"/>
    <form:hidden path="filesPath"/>
    <form:hidden path="uploadUrl"/>
    <sys:message content="${message}"/>
    <div class="panel-body">
        <video style="height: 450px; width: 730px;" controls="controls"
               src="${wxMediaFiles.uploadUrl}"></video>
    </div>
</form:form>
</body>
</html>