<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#wxMediaFilesTable').bootstrapTable({
		 
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
               url: "${ctx}/weixin/wxMediaVoice/data",
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
                 if($el.data("item") == "delete"){
                        jp.confirm('确认要删除音频素材吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/weixin/wxMediaVoice/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#wxMediaFilesTable').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	  })
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
                   field: 'title',
                   title: '标题'
               },{
                       field: 'introduction',
                       title: '简介说明'

                   }
                   ,{
                       field: 'updateDate',
                       title: '更新时间',
                       sortable: true,
                       sortName: 'updateDate'
                   },{
                       field: '',
                       title: '播放',
                       formatter:function(value, row , index){
                            var html='<audio id="audio_'+row.id+'" src="'+row.uploadUrl+'" loop="loop">你的浏览器不支持audio标签。</audio>';
                            html+='<a class="btn btn-primary btn-xs" id="a_'+row.id+'" onclick="javascript:play(\''+row.id+'\')"><i class="fa fa-volume-up"></i>播放</a>';
                        	return html;
                        }
                   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#wxMediaFilesTable').bootstrapTable("toggleView");
		}
	  
	  $('#wxMediaFilesTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#wxMediaFilesTable').bootstrapTable('getSelections').length);
        });

	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#wxMediaFilesTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#wxMediaFilesTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#wxMediaFilesTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除音频素材吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/weixin/wxMediaVoice/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#wxMediaFilesTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
		})
  }

    //刷新列表
  function refresh(){
  	$('#wxMediaFilesTable').bootstrapTable('refresh');
  }
  
   function add(){
	  jp.openSaveDialog('新增音频素材', "${ctx}/weixin/wxMediaVoice/form",'800px', '500px');
  }
    function play(id) {
        var music = $('#audio_' + id)[0];
        if (music.paused) {
            music.play();
            $('#a_' + id).html('<i class="fa fa-volume-off"></i> 暂停');
            $('#a_' + id).attr('class', 'btn btn-primary btn-xs');
        }
        else {
            music.pause();
            $('#a_' + id).html('<i class="fa fa-volume-up"></i>  播放');
            $('#a_' + id).attr('class', 'btn btn-info btn-xs');
        }
    }
</script>