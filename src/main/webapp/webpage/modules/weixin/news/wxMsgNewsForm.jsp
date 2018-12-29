<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>图文信息管理</title>
    <meta name="decorator" content="ani"/>
    <script src="${ctxStatic}/plugin/wangEditor/wangEditor.min.js?v=1.2"></script>
    <script type="text/javascript">

        $(document).ready(function () {

        });

        function save() {
            var str = $("#fromUrl").val();
            if (str.length > 0) {
                var Expression = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
                var objExp = new RegExp(Expression);
                if (objExp.test(str) != true) {
                    alert('网址格式不正确！请重新输入');
                    return false;
                }
            }
            $('#content').val(editor.txt.html());
            if ($('#content').val().length == 0) {
                alert('请输入内容');
                return false;
            }
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if (!isValidate) {
                return false;
            } else {
                jp.loading();
                jp.post("${ctx}/weixin/wxMsgNews/save", $('#inputForm').serialize(), function (data) {
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
<form:form id="inputForm" modelAttribute="wxMsgNews" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>标题：</label></td>
            <td class="width-35">
                <form:input path="title" htmlEscape="false" class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right">作者：</label></td>
            <td class="width-35">
                <form:input path="author" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>摘要：</label></td>
            <td class="width-35" colspan="3">
                <form:textarea path="brief" htmlEscape="false" rows="4" class="form-control required"/>
            </td>
        </tr>

        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>封面图片：</label></td>
            <td class="width-35" colspan="3">
                <sys:fileUpload path="picDir" value="${wxMsgNews.picDir}" type="image" uploadPath="/weixin/wxMsgNews"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>外部链接：</label></td>
            <td class="width-35">
                <form:input path="fromUrl" htmlEscape="false" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否显示图片：</label></td>
            <td class="width-35">
                <form:select path="showPic" class="input-sm ">
                    <form:option value="" label="=请选择="/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
                <font color="red">*</font>图文类型：
                <form:select path="multType" class="input-sm ">
                    <form:option value="" label="=请选择="/>
                    <form:options items="${fns:getDictList('wx_mult_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>描述：</label></td>
            <td class="width-35" colspan="3">
                <div id="editor" style="border: 1px solid grey;">
                </div>
                <form:hidden path="content"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
<script>
    var editor = new wangEditor('#editor');
    editor.customConfig.showLinkImg = false;
    editor.customConfig.uploadImgServer = '/weixin/attach/upfile';
    editor.customConfig.uploadImgTimeout = 5000;
    editor.customConfig.pasteText = true;
    editor.customConfig.menus = [
        'head',  // 标题
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'image',  // 插入图片
        'table',  // 插入图片
        'undo',  // 撤销
        'redo'  // 重复
    ];
    editor.customConfig.uploadImgHooks = {
        success: function (xhr, editor, result) {
            if (result.success) {
                //alert(result.AttUrl);
            }
        },
        fail: function (xhr, editor, result) {
            // 图片上传并返回结果，但图片插入错误时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        error: function (xhr, editor) {
            // 图片上传出错时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },
        timeout: function (xhr, editor) {
            // 图片上传超时时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },

        // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
        // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
        customInsert: function (insertImg, result, editor) {
            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            var url = result.AttUrl;
            insertImg(url)

            // result 必须是一个 JSON 格式字符串！！！否则报错
        }
    };
    editor.create();
    editor.txt.html($('#content').val());
</script>
</body>
</html>