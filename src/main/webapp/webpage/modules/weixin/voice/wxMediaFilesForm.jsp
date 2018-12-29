<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>素材信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

        $(document).ready(function() {

        });
        function save() {
            var uploadUrl = $('#uploadUrl').val();
            if (uploadUrl.length == 0) {
                alert('请上传音频文件');
                return false;
            }
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                jp.loading();
                jp.post("${ctx}/weixin/wxMediaVoice/save",$('#inputForm').serialize(),function(data){
                    if(data.success){
                        jp.getParent().refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    }else{
                        jp.error(data.msg);
                    }
                })
            }

        }
	</script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="wxMediaFiles" class="form-horizontal">
	<form:hidden path="id"/>
	<form:hidden path="mediaId"/>
	<form:hidden path="filesPath"/>
	<form:hidden path="uploadUrl"/>
	<sys:message content="${message}"/>
	<table class="table table-bordered">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>标题：</label></td>
			<td class="width-35">
				<form:input path="title" htmlEscape="false" class="form-control required"/>
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>简介说明：</label></td>
			<td class="width-35">
				<form:input path="introduction" htmlEscape="false" class="form-control required"/>
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>音频文件：</label></td>
			<td class="width-35">
				<input type="file" class="" accept="audio/mpeg" id="audioName" name="audioName"/><a
					href="javascript:" onclick="audioNameFinder(1);"
					class="btn btn-primary">上传音频</a><span id="fileMsg" color="red"></span></td>
		</tr>
		</tbody>
	</table>
</form:form>

<script src="${ctxStatic}/common/js/ajaxfileupload.js" type="text/javascript"></script>
<script>
    function audioNameFinder(id) {
        //获取file的全部id
        var uplist = $("input[name^=audioName]");
        var arrId = [];
        for (var i = 0; i < uplist.length; i++) {
            if (uplist[i].value) {
                arrId[i] = uplist[i].id;
            }
        }
        if (arrId.length > 0) {
            if (confirm('您确定要上传音频吗?')) {
                $.ajaxFileUpload({
                    url: '/weixin/attach/upfile',
                    secureuri: false,
                    fileElementId: arrId, //这里不在是以前的id了，要写成数组的形式哦！
                    dataType: 'json',
                    success: function (datas) {
                        if (datas.success) {
                            $('#filesPath').val(datas.filesPath);
                            $('#uploadUrl').val(datas.AttUrl);
                            $('#fileMsg').html(datas.msg);
                        } else {
                            $('#fileMsg').html(datas.msg);
                        }
                    }
                });
            }
        } else {
            alert('请选择上传文件！');
        }
    }
</script>
</body>
</html>