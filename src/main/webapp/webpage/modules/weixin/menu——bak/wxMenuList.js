<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $wxMenuTreeTable=null;  
		$(document).ready(function() {
			$wxMenuTreeTable=$('#wxMenuTreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/weixin/wxMenu/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#wxMenuTreeTableTpl").html();
		            	 item.dict = {};

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($wxMenuTreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $wxMenuTreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($wxMenuTreeTable, id) {   
		            },  
		            afterExpand : function($wxMenuTreeTable, id) {  
		            },  
		            beforeClose : function($wxMenuTreeTable, id) {    
		            	
		            }  
		        });
		        
		        $wxMenuTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除自定义菜单吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/weixin/wxMenu/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$wxMenuTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
		}
		function  publish() {
            jp.confirm('确认要同步自定义菜单吗？', function(){
                jp.loading('正在同步，请稍等……');
                $.get("${ctx}/weixin/wxMenu/publish", function(data){
                    if(data.success){
                        jp.success(data.msg);
                    }else{
                        jp.error(data.msg);
                    }
                })

            });
        }
		
		function refreshNode(data) {//刷新节点
            var current_id = data.body.wxMenu.id;
			var target = $wxMenuTreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.wxMenu.parentId;
			var current_parent_ids = data.body.wxMenu.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$wxMenuTreeTable.refreshPoint(-1);
				}else{
					$wxMenuTreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$wxMenuTreeTable.del(current_id);//刷新删除旧节点
				$wxMenuTreeTable.initParents(current_parent_ids, "0");
			}
        }
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$wxMenuTreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="wxMenuTreeTableTpl">
			<td>
			<c:choose>
			      <c:when test="${fns:hasPermission('weixin:wxMenu:edit')}">
				    <a  href="#" onclick="jp.openSaveDialog('编辑自定义菜单', '${ctx}/weixin/wxMenu/form?id={{d.row.id}}','800px', '500px')">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>
			      </c:when>
			      <c:when test="${fns:hasPermission('weixin:wxMenu:view')}">
				    <a  href="#" onclick="jp.openViewDialog('查看自定义菜单', '${ctx}/weixin/wxMenu/form?id={{d.row.id}}','800px', '500px')">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>
			      </c:when>
			      <c:otherwise>
							{{d.row.name === undefined ? "": d.row.name}}
			      </c:otherwise>
			</c:choose>
			</td>
			<td>
							{{d.row.sort === undefined ? "": d.row.sort}}
			</td>
			<td>
							{{d.row.updateDate === undefined ? "": d.row.updateDate}}
			</td>
			<td>
				<div class="btn-group">
			 		<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog"></i>
						<span class="fa fa-chevron-down"></span>
					</button>
				  <ul class="dropdown-menu" role="menu">
					<shiro:hasPermission name="weixin:wxMenu:view">
						<li><a href="#" onclick="jp.openViewDialog('查看自定义菜单', '${ctx}/weixin/wxMenu/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="weixin:wxMenu:edit">
						<li><a href="#" onclick="jp.openSaveDialog('修改自定义菜单', '${ctx}/weixin/wxMenu/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="weixin:wxMenu:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="weixin:wxMenu:add">
						<li><a href="#" onclick="jp.openSaveDialog('添加下级自定义菜单', '${ctx}/weixin/wxMenu/form?parent.id={{d.row.id}}','800px', '500px')"><i class="fa fa-plus"></i> 添加下级自定义菜单</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>