<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>模板消息管理</title>
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
                jp.post("${ctx}/weixin/wxTplMsg/save", $('#inputForm').serialize(), function (data) {
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

        function addRow(list, idx, tpl, row) {
            $(list).append(Mustache.render(tpl, {
                idx: idx, delBtn: true, row: row
            }));
            $(list + idx).find("select").each(function () {
                $(this).val($(this).attr("data-value"));
            });
            $(list + idx).find("input[type='checkbox'], input[type='radio']").each(function () {
                var ss = $(this).attr("data-value").split(',');
                for (var i = 0; i < ss.length; i++) {
                    if ($(this).val() == ss[i]) {
                        $(this).attr("checked", "checked");
                    }
                }
            });
            $(list + idx).find(".form_datetime").each(function () {
                $(this).datetimepicker({
                    format: "YYYY-MM-DD HH:mm:ss"
                });
            });
        }

        function delRow(obj, prefix) {
            var id = $(prefix + "_id");
            var delFlag = $(prefix + "_delFlag");
            if (id.val() == "") {
                $(obj).parent().parent().remove();
            } else if (delFlag.val() == "0") {
                delFlag.val("1");
                $(obj).html("&divide;").attr("title", "撤销删除");
                $(obj).parent().parent().addClass("error");
            } else if (delFlag.val() == "1") {
                delFlag.val("0");
                $(obj).html("&times;").attr("title", "删除");
                $(obj).parent().parent().removeClass("error");
            }
        }
    </script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="wxTplMsg" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 "><label class="pull-right"><font color="red">*</font>模板ID：</label></td>
            <td class="width-35">
                <form:input path="tplId" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 "><label class="pull-right"><font color="red">*</font>模板标题：</label></td>
            <td class="width-35">
                <form:input path="title" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 "><label class="pull-right"><font color="red">*</font>访问地址：</label></td>
            <td class="width-35">
                <form:input path="url" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15"><label class="pull-right">模板参数：</label></td>
            <td class="width-35" colspan="3">
                <a class="btn btn-primary btn-sm"
                   onclick="addRow('#subList', subListRowIdx, subListTpl);subListRowIdx = subListRowIdx + 1;"
                   title="新增"><i class="fa fa-plus"></i> 新增</a>
                <p style="color:red;">提示：根据微信官网参数填写，如：【{{first.DATA}}】 填写 【first】即可</p>
                <table id="contentTable" class="table table-striped table-bordered table-condensed spec_table">
                    <thead>
                    <th><font color="red">参数</font></th>
                    <th><font color="red">内容</font></th>
                    <th><font color="red">排序</font></th>
                    <th width="10">&nbsp;</th>
                    </thead>
                    <tbody id="subList">
                    </tbody>
                    <script type="text/template" id="subListTpl">//<!--
				<tr id="subList{{idx}}">
					<td class="hide">
						<input id="subList{{idx}}_tplId" name="subList[{{idx}}].tplId" type="hidden" value="${wxTplMsgText.id}"/>
						<input id="subList{{idx}}_id" name="subList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="subList{{idx}}_delFlag" name="subList[{{idx}}].delFlag" type="hidden" value="0"/>
					 </td>
                      <td>	<input id="subList{{idx}}_name" name="subList[{{idx}}].name" type="text" value="{{row.name}}"  class="form-control required"/>

                      </td>
	                    <td>	<input id="subList{{idx}}_title" name="subList[{{idx}}].title" type="text" value="{{row.title}}"  class="form-control required"/>
						</td> <td  style="width: 80px;">
						<input id="subList{{idx}}_sort"  name="subList[{{idx}}].sort" type="text" value="{{row.sort}}" maxlength="2"  class="form-control required"/>
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#subList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
                    </script>
                </table>

            </td>
        </tr>
        </tbody>
    </table>
</form:form>
<script>
    var subListRowIdx = 0,
        subListTpl = $("#subListTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
    $(document).ready(function () {
        var data = ${fns:toJson(wxTplMsg.subList)};
        for (var i = 0; i < data.length; i++) {
            addRow('#subList', subListRowIdx, subListTpl, data[i]);
            subListRowIdx = subListRowIdx + 1;
        }
    });
</script>
</body>
</html>