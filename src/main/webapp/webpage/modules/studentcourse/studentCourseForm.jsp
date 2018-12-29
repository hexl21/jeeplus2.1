<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程记录管理</title>
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
                jp.post("${ctx}/studentcourse/studentCourse/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="studentCourse" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>学生：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/student/student/data" id="student" name="student.id" value="${studentCourse.student.id}" labelName="" labelValue="${studentCourse.}"
							 title="选择学生" cssClass="form-control required" fieldLabels="姓名" fieldKeys="name" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>课程：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/course/course/data" id="course" name="course.id" value="${studentCourse.course.id}" labelName="" labelValue="${studentCourse.}"
							 title="选择课程" cssClass="form-control required" fieldLabels="课程名" fieldKeys="name" searchLabels="课程名" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>分数：</label></td>
					<td class="width-35">
						<form:input path="score" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>