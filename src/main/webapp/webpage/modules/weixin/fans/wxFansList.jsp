<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>微信粉丝管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <link href="${ctxStatic}/plugin/bootstrapTree/bootstrap-treeview.css" rel="stylesheet" type="text/css"/>
    <script src="${ctxStatic}/plugin/bootstrapTree/bootstrap-treeview.js" type="text/javascript"></script>
    <%@include file="wxFansList.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">微信粉丝列表</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-sm-3 col-md-2">
                    <div id="jstree"></div>
                </div>
                <div class="col-sm-9 col-md-10 animated fadeInRight">
                    <!-- 搜索 -->
                    <div id="search-collapse" class="collapse">
                        <div class="accordion-inner">
                            <form:form id="searchForm" modelAttribute="wxFans"
                                       class="form form-horizontal well clearfix">
                                <input id="tagId" name="tagId" type="hidden" value="${wxFans.tagId}"/>
                                <div class="col-xs-12 col-sm-6 col-md-4">
                                    <label class="label-item single-overflow pull-left" title="性别">性别：</label>
                                    <form:select path="sex" class="form-control m-b">
                                        <form:option value="" label=""/>
                                        <form:options items="${fns:getDictList('sex')}" itemLabel="label"
                                                      itemValue="value" htmlEscape="false"/>
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
                        <shiro:hasPermission name="weixin:wxFans:add">
                            <button id="synFans" class="btn btn-info"><i class="fa fa-reply"></i>批量同步粉丝</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="weixin:wxFans:edit">
                            <button id="edit" class="btn btn-success" disabled onclick="edit()">
                                <i class="glyphicon glyphicon-edit"></i>修改备注
                            </button>
                            <button id="addTag" class="btn btn-primary" disabled onclick="addTag()">
                                <i class="glyphicon glyphicon-plus"></i>上标签
                            </button>
                        </shiro:hasPermission>

                    </div>

                    <!-- 表格 -->
                    <table id="wxFansTable" data-toolbar="#toolbar"></table>

                    <!-- context menu -->
                    <ul id="context-menu" class="dropdown-menu">
                        <shiro:hasPermission name="weixin:wxFans:edit">
                            <li data-item="edit"><a>修改备注</a></li>
                        </shiro:hasPermission>
                        <li data-item="action1"><a>取消</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>