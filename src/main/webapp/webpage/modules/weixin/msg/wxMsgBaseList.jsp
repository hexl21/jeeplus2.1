<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>微信消息管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="wxMsgBaseList.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">自动回复列表</h3>
        </div>
        <div class="panel-body">

            <!-- 搜索 -->
            <div id="search-collapse" class="collapse">
                <div class="accordion-inner">
                    <form:form id="searchForm" modelAttribute="wxMsgBase" class="form form-horizontal well clearfix">
                        <div class="col-xs-12 col-sm-6 col-md-4">
                            <label class="label-item single-overflow pull-left" title="消息类型：">消息类型：</label>
                            <form:select path="msgType" class="form-control m-b">
                                <form:option value="" label="=请选择="/>
                                <form:options items="${fns:getDictList('wx_msgType')}" itemLabel="label"
                                              itemValue="value" htmlEscape="false"/>
                            </form:select>
                        </div>
                        <div class="col-xs-12 col-sm-6 col-md-4">
                            <label class="label-item single-overflow pull-left" title="关键字：">关键字：</label>
                            <form:input path="inputCode" htmlEscape="false" maxlength="200" class=" form-control"/>
                        </div>
                        <div class="col-xs-12 col-sm-6 col-md-4">
                            <label class="label-item single-overflow pull-left" title="是否可用：">是否可用：</label>
                            <form:select path="enable" class="form-control m-b">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                        </div>
                        <div class="col-xs-12 col-sm-6 col-md-4">
                            <div style="margin-top:26px">
                                <a id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                        class="fa fa-search"></i> 查询</a>
                                <a id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                        class="fa fa-refresh"></i> 重置</a>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>

            <!-- 工具栏 -->
            <div id="toolbar">
                <shiro:hasPermission name="weixin:wxMsgBase:add">
                    <button id="add" class="btn btn-primary" onclick="add()">
                        <i class="glyphicon glyphicon-plus"></i> 新建
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="weixin:wxMsgBase:edit">
                    <button id="edit" class="btn btn-success" disabled onclick="edit()">
                        <i class="glyphicon glyphicon-edit"></i> 修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="weixin:wxMsgBase:del">
                    <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
                        <i class="glyphicon glyphicon-remove"></i> 删除
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="weixin:wxMsgBase:view">
                    <button id="view" class="btn btn-default" disabled onclick="view()">
                        <i class="fa fa-search-plus"></i> 查看
                    </button>
                </shiro:hasPermission>
            </div>

            <!-- 表格 -->
            <table id="wxMsgBaseTable" data-toolbar="#toolbar"></table>

            <!-- context menu -->
            <ul id="context-menu" class="dropdown-menu">
                <shiro:hasPermission name="weixin:wxMsgBase:view">
                    <li data-item="view"><a>查看</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="weixin:wxMsgBase:edit">
                    <li data-item="edit"><a>编辑</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="weixin:wxMsgBase:del">
                    <li data-item="delete"><a>删除</a></li>
                </shiro:hasPermission>
                <li data-item="action1"><a>取消</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>