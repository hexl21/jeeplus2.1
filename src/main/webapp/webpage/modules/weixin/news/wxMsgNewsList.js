<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    $(document).ready(function () {
        $('#wxMsgNewsTable').bootstrapTable({

            //请求方法
            method: 'post',
            //类型json
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            //显示检索按钮
            showSearch: true,
            //显示刷新按钮
            showRefresh: true,
            //显示切换手机试图按钮
            showToggle: true,
            //显示 内容列下拉框
            showColumns: true,
            //显示到处按钮
            showExport: true,
            //显示切换分页按钮
            showPaginationSwitch: true,
            //最低显示2行
            minimumCountColumns: 2,
            //是否显示行间隔色
            striped: true,
            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            cache: false,
            //是否显示分页（*）
            pagination: true,
            //排序方式
            sortOrder: "asc",
            //初始化加载第一页，默认第一页
            pageNumber: 1,
            //每页的记录行数（*）
            pageSize: 10,
            //可供选择的每页的行数（*）
            pageList: [10, 25, 50, 100],
            //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
            url: "${ctx}/weixin/wxMsgNews/data",
            //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
            //queryParamsType:'',
            ////查询参数,每次调用是会带上这个参数，可自定义
            queryParams: function (params) {
                var searchParam = $("#searchForm").serializeJSON();
                searchParam.pageNo = params.limit === undefined ? "1" : params.offset / params.limit + 1;
                searchParam.pageSize = params.limit === undefined ? -1 : params.limit;
                searchParam.orderBy = params.sort === undefined ? "" : params.sort + " " + params.order;
                return searchParam;
            },
            //分页方式：client客户端分页，server服务端分页（*）
            sidePagination: "server",
            contextMenuTrigger: "right",//pc端 按右键弹出菜单
            contextMenuTriggerMobile: "press",//手机端 弹出菜单，click：单击， press：长按。
            contextMenu: '#context-menu',
            onContextMenuItem: function (row, $el) {
                if ($el.data("item") == "edit") {
                    edit(row.id);
                } else if ($el.data("item") == "view") {
                    view(row.id);
                } else if ($el.data("item") == "delete") {
                    jp.confirm('确认要删除该图文信息记录吗？', function () {
                        jp.loading();
                        jp.get("${ctx}/weixin/wxMsgNews/delete?id=" + row.id, function (data) {
                            if (data.success) {
                                $('#wxMsgNewsTable').bootstrapTable('refresh');
                                jp.success(data.msg);
                            } else {
                                jp.error(data.msg);
                            }
                        })

                    });

                }
            },

            onClickRow: function (row, $el) {
            },
            onShowSearch: function () {
                $("#search-collapse").slideToggle();
            },
            columns: [{
                checkbox: true

            }, {
                field: 'title',
                title: '标题'

            }, {
                    field: 'author',
                    title: '作者'

            }, {
                    field: 'multType',
                    title: '图文类型',
                    sortable: true,
                    sortName: 'multType',
                    formatter: function (value, row, index) {
                        return jp.getDictLabel(${fns:toJson(fns:getDictList('wx_mult_type'))}, value, "-");
                    }
             }
             , {
                    field: 'brief',
                    title: '简介'

                }

                , {
                    field: 'updateDate',
                    title: '更新时间',
                    sortable: true,
                    sortName: 'updateDate'

                }, {
                    field: 'status',
                    title: '是否发布',
                    sortable: true,
                    sortName: 'status',
                    formatter: function (value, row, index) {
                        if(value==1){
                            return '<button onclick="updateStatus(\''+row.id+'\',0,\'撤销发布\')"  class="btn btn-danger btn-xs"><i class="fa fa-minus"></i>撤销发布</button>';
                        } if(value==0){
                            return '<button onclick="updateStatus(\''+row.id+'\',1,\'发布\')" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>发布</button>';
                        }
                    }

                }, {
                    field: '',
                    title: '操作',
                    formatter: function (value, row, index) {
                        var html = '';
                        if (row.multType == 2) {
                            html += '<a href="#" onclick="javascript:jp.openTab(\'${ctx}/weixin/wxMsgNewsArticle/list?newsId=' + row.id + '\',\'【' + row.title + '】图文列表\',false)" class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>图文列表</a>&nbsp;';
                            html += '<a class="btn btn-primary btn-xs"  onclick="javascript:add1(\'' + row.id + '\')"><i class="glyphicon glyphicon-plus"></i>添加图文</a>&nbsp;';
                        }
                        if (row.status != 1) {
                            html += '<a class="btn btn-success btn-xs"  onclick="javascript:edit(\'' + row.id + '\')"><i class="glyphicon glyphicon-edit"></i>修改</a>';
                        }else{
                            html += '<a class="btn btn-success btn-xs"  onclick="javascript:edit(\'' + row.id + '\')"><i class="fa fa-share"></i> 消息群发</a>';

                       }
                        return html;
                    }
                }
            ]
        });


        if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端


            $('#wxMsgNewsTable').bootstrapTable("toggleView");
        }

        $('#wxMsgNewsTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', !$('#wxMsgNewsTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#wxMsgNewsTable').bootstrapTable('getSelections').length != 1);
        });

        $("#search").click("click", function () {// 绑定查询按扭
            $('#wxMsgNewsTable').bootstrapTable('refresh');
        });

        $("#reset").click("click", function () {// 绑定查询按扭
            $("#searchForm  input").val("");
            $("#searchForm  select").val("");
            $("#searchForm  .select-item").html("");
            $('#wxMsgNewsTable').bootstrapTable('refresh');
        });


    });

function getIdSelections() {
    return $.map($("#wxMsgNewsTable").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}
function updateStatus(id,status,txt) {
    jp.confirm('确认要'+txt+'吗？', function(){
        jp.loading();
        jp.get("${ctx}/weixin/wxMsgNews/updateStatus?status="+status+"&id="+id, function(data){
            if(data.success){
                $('#wxMsgNewsTable').bootstrapTable('refresh');
                jp.success(data.msg);
            }else{
                jp.error(data.msg);
            }
        })
    })
}
function deleteAll() {
    jp.confirm('确认要删除该图文信息记录吗？', function () {
        jp.loading();
        jp.get("${ctx}/weixin/wxMsgNews/deleteAll?ids=" + getIdSelections(), function (data) {
            if (data.success) {
                $('#wxMsgNewsTable').bootstrapTable('refresh');
                jp.success(data.msg);
            } else {
                jp.error(data.msg);
            }
        })

    })
}

//刷新列表
function refresh() {
    $('#wxMsgNewsTable').bootstrapTable('refresh');
}

function add() {
    jp.openSaveDialog('新增图文信息', "${ctx}/weixin/wxMsgNews/form", '70%', '90%');
}
function add1(id) {
    jp.openSaveDialog('新增图文信息', "${ctx}/weixin/wxMsgNewsArticle/form?newsId="+id, '70%', '90%');
}


function edit(id) {//没有权限时，不显示确定按钮
    if (id == undefined) {
        id = getIdSelections();
    }
    jp.openSaveDialog('编辑图文信息', "${ctx}/weixin/wxMsgNews/form?id=" + id, '70%', '90%');
}

function view(id) {//没有权限时，不显示确定按钮
    if (id == undefined) {
        id = getIdSelections();
    }
    jp.openViewDialog('查看图文信息', "${ctx}/weixin/wxMsgNews/form?id=" + id, '70%', '90%');
}


</script>