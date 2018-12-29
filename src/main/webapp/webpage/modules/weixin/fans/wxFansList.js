<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
    $.getJSON("${ctx}/weixin/wxFansTag/bootstrapTreeData",function(data){
        $('#jstree').treeview({
            data: data,
            levels: 5,
            onNodeSelected: function(event, treeNode) {
                var id = treeNode.id;
                if (id == 0) {
                    $('#tagId').val(treeNode.id);
                    $('#wxFansTable').bootstrapTable('refresh');
                } else {
                    $('#tagId').val(treeNode.id);
                    $('#searchForm').attr('action', '${ctx}/weixin/wxFansTagMpping/list');
                    $('#searchForm').submit();

                }
            },
        });
    });
	$('#wxFansTable').bootstrapTable({
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
               pageNumber:1,
               //每页的记录行数（*）
               pageSize: 10,
               //可供选择的每页的行数（*）
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
               url: "${ctx}/weixin/wxFans/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',
               ////查询参数,每次调用是会带上这个参数，可自定义
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   		edit(row.id);
                   }else if($el.data("item") == "view"){
                       view(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该微信粉丝吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/weixin/wxFans/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#wxFansTable').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	});
                        });
                   }
               },

               onClickRow: function(row, $el){
               },
               	onShowSearch: function () {
			$("#search-collapse").slideToggle();
		},
               columns: [{
		        checkbox: true

		    },{
		        field: 'nickNameStr',
		        title: '昵称',
                   formatter:function (value,row,index) {
                       if(row.remark!='' && row.remark!=''){
                           return row.remark+'('+value+')';
                       }
                       return value;
                   }

		    },{
                   field: 'headImgUrl',
                   title: '头像',
                   sortable: true,
                   sortName: 'headImgUrl',
                   formatter:function(value, row , index){
                       return '<img src="'+value+'" width="30px" height="30px">';
                   }
               }
			,{
		        field: 'sex',
		        title: '性别',
		        sortable: true,
		        sortName: 'sex',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('sex'))}, value, "-");
		        }

		    }
			,{
		        field: 'language',
		        title: '语言',
		        sortable: true,
		        sortName: 'language'

		    }
			,{
		        field: 'country',
		        title: '国家',
		        sortable: true,
		        sortName: 'country'

		    }
			,{
		        field: 'province',
		        title: '省/城市',
		        sortable: true,
		        sortName: 'province',
                       formatter:function(value, row , index){
                    return value+'/'+row.city;
                }
		    }
			,{
		        field: 'status',
		        title: '用户状态',
		        sortable: true,
		        sortName: 'status',
                 formatter:function(value, row , index){
		            if(value==1){
		                return '<button onclick="updateStatus(\''+row.id+'\',0,\'拉入黑名单\')"  class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>拉入黑名单</button>';
                    } if(value==0){
                        return '<button onclick="updateStatus(\''+row.id+'\',1,\'移除黑名单\')" class="btn btn-info btn-xs"><i class="fa fa-minus"></i>移除黑名单</button>';
                     }
                  }
		    }]
		});


	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		  $('#wxFansTable').bootstrapTable("toggleView");
		}

	  $('#wxFansTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#addTag,#remove').prop('disabled', ! $('#wxFansTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#wxFansTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#synFans").click(function(){//导出Excel文件
         jp.confirm('确认要同步微信粉丝吗？', function(){
             jp.loading('正在同步，请耐心等待……');
             jp.get("${ctx}/weixin/wxFans/synFansList", function(data){
                 if(data.success){
                     $('#wxFansTable').bootstrapTable('refresh');
                     jp.success(data.msg);
                 }else{
                     jp.error(data.msg);
                 }
             })
         })
	  });


	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#wxFansTable').bootstrapTable('refresh');
		});

	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#wxFansTable').bootstrapTable('refresh');
		});


	});

  function getIdSelections() {
        return $.map($("#wxFansTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
  }
function getIdSelectionsOpenId() {
    return $.map($("#wxFansTable").bootstrapTable('getSelections'), function (row) {
        return row.openId
    });
}
  function deleteAll(){
		jp.confirm('确认要删除该微信粉丝记录吗？', function(){
			jp.loading();
			jp.get("${ctx}/weixin/wxFans/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#wxFansTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})

		})
  }

    //刷新列表
  function refresh(){
  	$('#wxFansTable').bootstrapTable('refresh');
  }

  function updateStatus(id,status,txt) {
      jp.confirm('确认要'+txt+'吗？', function(){
          jp.loading();
          jp.get("${ctx}/weixin/wxFans/updateStatus?status="+status+"&id="+id, function(data){
              if(data.success){
                  $('#wxFansTable').bootstrapTable('refresh');
                  jp.success(data.msg);
              }else{
                  jp.error(data.msg);
              }
          })
      })
  }
    function addTag() {
        jp.openSaveDialog('上标签', "${ctx}/weixin/wxFansTagMpping/form?openIds=" + getIdSelectionsOpenId(), '800px', '500px');
    }
   function edit(id){//没有权限时，不显示确定按钮
       if(id == undefined){
	      id = getIdSelections();
	}
	jp.openSaveDialog('设置备注', "${ctx}/weixin/wxFans/form?id=" + id, '300px', '300px');
  }


</script>