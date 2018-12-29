<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>自定义菜单管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

		});

		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/weixin/wxMenu/save",$('#inputForm').serialize(),function(data){
                    if(data.success){
                        jp.getParent().refreshNode(data);
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
		<form:form id="inputForm" modelAttribute="wxMenu" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 "><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
            </tr>
            <tr>		<td class="width-15 "><label class="pull-right"><font color="red">*</font>上级菜单：</label></td>
					<td class="width-35">
						<sys:treeselect id="parent" name="parent.id" value="${wxMenu.parent.id}" labelName="parent.name" labelValue="${wxMenu.parent.name}"
						title="上级菜单" url="/weixin/wxMenu/treeData" extId="${wxMenu.id}" cssClass="form-control " allowClear="true"/>
					</td>
			</tr>
				<tr>
					<td class="width-15 "><label class="pull-right"><font color="red">*</font>页面地址：</label></td>
					<td class="width-35">
						<form:input path="url" htmlEscape="false"    class="form-control "/>
					</td>
            </tr>
            <tr>		<td class="width-15 "><label class="pull-right"><font color="red">*</font>排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		</form:form>
</body>
</html>