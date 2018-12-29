<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>微信消息管理</title>
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
                jp.post("${ctx}/weixin/wxMsgBase/save", $('#inputForm').serialize(), function (data) {
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
                    if (ss[i] == 1) {
                        $('#subList' + idx + '_foreignTitle').attr('class', 'form-control hide');
                        $('#subList' + idx + '_remarks').attr('class', 'form-control required');
                    } else {
                        $('#subList' + idx + '_foreignTitle').attr('class', 'form-control');
                        $('#subList' + idx + '_remarks').attr('class', 'form-control hide');
                    }
                    if (row != undefined) {
                        if (ss[i] == 1) {
                            $('#subList' + idx + '_remarks').val(row.remarks);
                        } else {
                            $('#subList' + idx + '_foreignTitle').val(row.foreignTitle);
                            $('#subList' + idx + '_foreignId').val(row.foreignId);
                        }
                    }
                }
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
<form:form id="inputForm" modelAttribute="wxMsgBase" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>消息类型：</label></td>
            <td class="width-35">
                <form:select path="msgType" class="form-control required">
                    <form:option value="" label="=请选择="/>
                    <form:options items="${fns:getDictList('wx_msgType')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否可用：</label></td>
            <td class="width-35">
                <form:select path="enable" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">匹配规则：</label></td>
            <td class="width-35">
                <form:select path="rule" class="form-control ">
                    <form:option value="" label="=请选择="/>
                    <form:options items="${fns:getDictList('wx_msg_rule')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">关键字：</label></td>
            <td class="width-35">
                <form:input path="inputCode" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>回复内容：</label></td>
            <td class="width-35" colspan="3">
                <a class="btn btn-primary btn-sm"
                   onclick="addRow('#subList', subListRowIdx, subListTpl);subListRowIdx = subListRowIdx + 1;"
                   title="新增"><i class="fa fa-plus"></i> 新增</a>
                <table id="contentTable" class="table table-striped table-bordered table-condensed spec_table">
                    <tbody id="subList">
                    </tbody>
                    <script type="text/template" id="subListTpl">//<!--
				  <tr id="subList{{idx}}">
					  <td class="hide">
						<input id="subList{{idx}}_tplId" name="subList[{{idx}}].tplId" type="hidden" value="${wxMsgBase.id}"/>
						<input id="subList{{idx}}_id" name="subList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="subList{{idx}}_delFlag" name="subList[{{idx}}].delFlag" type="hidden" value="0"/>
					 </td>
	                    <td>
                            <c:forEach items="${fns:getDictList('wx_type')}" var="dict" varStatus="dictStatus">
                                <span><input id="subList{{idx}}_foreignType${dictStatus.index}"  onchange="getType(this.value,{{idx}});"  name="subList[{{idx}}].foreignType" type="radio" class="i-checks required" value="${dict.value}" data-value="{{row.foreignType}}"><label for="subList{{idx}}_foreignType${dictStatus.index}">${dict.label}</label></span>
                            </c:forEach>
                            <input  id="subList{{idx}}_foreignId" name="subList[{{idx}}].foreignId" type="text" value="{{row.foreignId}}"  class="form-control hide"/>
                            <input id="subList{{idx}}_foreignTitle" name="subList[{{idx}}].foreignTitle" readonly="readonly" type="text" value="{{row.foreignTitle}}"  class="form-control hide"/>

                            <textarea id="subList{{idx}}_remarks" rows="5" title="remarks" name="subList[{{idx}}].remarks" type="text" value="{{row.remarks}}"  class="form-control hide">
                            </textarea>
						 </td>
                          <td>
                          {{#delBtn}}<span class="close" onclick="delRow(this, '#subList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
                      </td>
						</tr>
				</tr>//-->
                    </script>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
<script>
    var subListRowIdx = 0, subListTpl = $("#subListTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
    $(document).ready(function () {
        var data = ${fns:toJson(wxMsgBase.subList)};
        for (var i = 0; i < data.length; i++) {
            addRow('#subList', subListRowIdx, subListTpl, data[i]);
            subListRowIdx = subListRowIdx + 1;
        }
    });

    function getType(val, idx) {
        if (val == 1) {
            $('#subList' + idx + '_foreignTitle').attr('class', 'form-control hide');
            $('#subList' + idx + '_remarks').attr('class', 'form-control required');
        } else {
            $('#subList' + idx + '_remarks').val('');
            $('#subList' + idx + '_foreignTitle').attr('class', 'form-control');
            $('#subList' + idx + '_remarks').attr('class', 'form-control hide');
        }
        if (val == 2) {
            openDialogTextList('选择图文', '${ctx}/weixin/wxMsgNews/select', idx, '图文')
        }
        if (val == 3) {
            openDialogTextList('选择图片', '${ctx}/weixin/wxMediaImage/select', idx, '图片')
        }
        if (val == 4) {
            openDialogTextList('选择音频', '${ctx}/weixin/wxMediaVoice/select', idx, '音频')
        }
        if (val == 5) {
            openDialogTextList('选择视频', '${ctx}/weixin/wxMediaVideo/select', idx, '视频')
        }
    }
    //打开对话框(添加修改)
    function openDialogTextList(title, url, idx, tit) {
        if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，就使用自适应大小弹窗
            width = 'auto';
            height = 'auto';
        } else {//如果是PC端，根据用户设置的width和height显示。

        }
        top.layer.open({
            type: 2,
            area: ['1200px', '800px'],
            title:title,
            auto:true,
            maxmin: true, //开启最大化最小化按钮
            content:  url,
            btn: ['确定', '关闭'],
            yes: function(index, layero){
                var ids = layero.find("iframe")[0].contentWindow.getIdSelections();
                var title = layero.find("iframe")[0].contentWindow.getTitleSelections();
                console.log('title=='+title);
                console.log('ids=='+ids);
                $('#subList' + idx + '_foreignId').val(ids);
                $('#subList' + idx + '_foreignTitle').val(title);
                top.layer.close(index);
            },
            cancel: function(index){
                //取消默认为空，如需要请自行扩展。
                top.layer.close(index);
            }
        });
    }
</script>
</body>
</html>