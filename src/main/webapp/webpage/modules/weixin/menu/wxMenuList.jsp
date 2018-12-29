<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>自定义菜单管理</title>
<meta name="decorator" content="ani"/>
<link rel="stylesheet" href="${ctxStatic}/plugin/weixin/css/bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/plugin/weixin/css/wx-custom.css">
<!--[if lt IE 9]>
	<script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	<script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
  <%@include file="wxMenuList.js" %>
</head>
<body>
<div class="custom-menu-edit-con">
  <div class="hbox">
    <div class="inner-left">
      <div class="custom-menu-view-con">
        <div class="custom-menu-view">
          <div class="custom-menu-view__title">${account.name}</div>
          <div class="custom-menu-view__body">
            <div class="weixin-msg-list">
              <ul class="msg-con">
              </ul>
            </div>
          </div>
          <div id="menuMain" class="custom-menu-view__footer">
            <div class="custom-menu-view__footer__left"></div>
            <div class="custom-menu-view__footer__right"> </div>
          </div>
        </div>
      </div>
    </div>
    <div class="inner-right">
      <div class="cm-edit-after" style="display: block;">
        <div class="cm-edit-right-header b-b"><span id="cm-tit"></span> <a id="delMenu" class="pull-right" href="javascript:;">删除菜单</a></div>
        <form class="form-horizontal wrapper-md" name="custom_form">
          <div class="form-group">
            <label class="col-sm-2 control-label">菜单名称:</label>
            <div class="col-sm-5">
              <input name="custom_input_title" class="form-control" ng-model="menuname"  placeholder="" ng-maxlength="5" type="text">
            </div>
            <div class="col-sm-5 help-block">
              <div ng-show="custom_form.custom_input_title.$dirty&amp;&amp;custom_form.custom_input_title.$invalid-maxlength">字数不超过5个汉字或16个字符</div>
              <div class="font_sml" style="display: none;">若无二级菜单，可输入20个汉字或60个字符</div>
            </div>
          </div>
          <div class="form-group" id="radioGroup">
            <label class="col-sm-2 control-label">菜单内容:</label>
            <div class="col-sm-10 LebelRadio">
              <label class="checkbox-inline">
                <input name="radioBtn" value="sendmsg" checked="checked" type="radio">
                发送消息</label>
              <label class="checkbox-inline">
                <input name="radioBtn" value="link" type="radio">
                跳转网页</label>
            </div>
          </div>
        </form>
        <div class="cm-edit-content-con" id="editMsg" style="display: block;">
          <div class="editTab">
            <div class="editTab-heading">
              <ul class="msg-panel__tab">
                <li class="msg-tab_item on"> <span class="msg-icon msg-icon-tuwen"></span> 图文消息 </li>
                <li class="msg-tab_item "> <span class="msg-icon msg-icon-word"></span> 文本 </li>
                <li class="msg-tab_item "> <span class="msg-icon msg-icon-pic"></span> 图片 </li>
                <li class="msg-tab_item "> <span class="msg-icon msg-icon-video"></span> 视频 </li>
                <li class="msg-tab_item "> <span class="msg-icon msg-icon-audio"></span> 音频 </li>
              </ul>
            </div>
            <div class="editTab-body" style="height:296px; overflow:hidden">
              <div class="msg-panel__context" >
                <div class="msg-context__item" style="display: none;">
                  <div class="msg-panel__center msg-panel_select" data-toggle="modal" data-target="#selectModal"><span class="message-plus">+</span><br>
                    从素材库中选择</div>
                </div>
                <div class="msg-template" style="display: block;"> </div>
              </div>           
              <div class="msg-panel__context">
                <div class="msg-context__item" style="display: none;">
                  <div class="msg-panel__center msg-panel_select" data-toggle="modal" data-target="#selectModal"><span class="message-plus">+</span><br>
                    从文本素材库中选择</div>
                </div>
                <div class="msg-template" style="display: block;"> </div>
              </div>
              <div class="msg-panel__context">
                <div class="msg-context__item" style="display: none;">
                  <div class="msg-panel__center msg-panel_select" data-toggle="modal" data-target="#selectModal"><span class="message-plus">+</span><br>
                    从图片素材库中选择</div>
                </div>
                <div class="msg-template" style="display: block;"> </div>
              </div>
              <div class="msg-panel__context">
                <div class="msg-context__item" style="display: none;">
                  <div class="msg-panel__center msg-panel_select" data-toggle="modal" data-target="#selectModal"><span class="message-plus">+</span><br>
                    从视频素材库中选择</div>
                </div>
                <div class="msg-template" style="display: block;"> </div>
              </div>
              <div class="msg-panel__context">
                <div class="msg-context__item" style="display: none;">
                  <div class="msg-panel__center msg-panel_select" data-toggle="modal" data-target="#selectModal"><span class="message-plus">+</span><br>
                    从音频素材库中选择</div>
                </div>
                <div class="msg-template" style="display: block;"> </div>
              </div>
            </div>
          </div>
        </div>
        <div class="cm-edit-content-con" id="editPage" style="display: none;">
          <div class="cm-edit-page">
            <div class="row">
              <label class="col-sm-6 control-label" style="text-align: left;">粉丝点击该菜单会跳转到以下链接: </label>
            </div>
            <div class="row">
              <label class="col-sm-2 control-label" style="text-align: left;">页面地址: </label>
              <div class="col-sm-5">
                <input name="url" class="form-control" placeholder="认证号才可手动输入地址" value="http://v.qq.com/" type="text">
                <span class="help-block">必填,必须是正确的URL格式</span> </div>
            </div>
          </div>
        </div>
      </div>
      <div class="cm-edit-before" style="display: none;">
        <h5>点击左侧菜单进行操作</h5>
      </div>
    </div>
  </div>
</div>
<div class="cm-edit-footer">
  <button id="sortBtn" type="button" class="btn btn-default">菜单排序</button>
  <button id="sortBtnc" type="button" class="btn btn-default">完成排序</button>
  <button id="saveBtns" type="button" class="btn btn-info1">保存并发布</button>
</div>
<!-- 自定义菜单排序 --> 
<script src="${ctxStatic}/plugin/weixin/js/Sortable.js"></script>
</body>
</html>