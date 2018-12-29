<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $treeeTreeTable=null;  
		$(document).ready(function() {
			$treeeTreeTable=$('#treeeTreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/tree/tree/treee/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#treeeTreeTableTpl").html();
		            	 item.dict = {};

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($treeeTreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $treeeTreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($treeeTreeTable, id) {   
		            },  
		            afterExpand : function($treeeTreeTable, id) {  
		            },  
		            beforeClose : function($treeeTreeTable, id) {    
		            	
		            }  
		        });
		        
		        $treeeTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除树的测试吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/tree/tree/treee/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$treeeTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function refreshNode(data) {//刷新节点
            var current_id = data.body.treee.id;
			var target = $treeeTreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.treee.parentId;
			var current_parent_ids = data.body.treee.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$treeeTreeTable.refreshPoint(-1);
				}else{
					$treeeTreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$treeeTreeTable.del(current_id);//刷新删除旧节点
				$treeeTreeTable.initParents(current_parent_ids, "0");
			}
        }
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$treeeTreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="treeeTreeTableTpl">
			<td>
			<c:choose>
			      <c:when test="${fns:hasPermission('tree:tree:treee:edit')}">
				    <a  href="#" onclick="jp.openSaveDialog('编辑树的测试', '${ctx}/tree/tree/treee/form?id={{d.row.id}}','800px', '500px')">
							{{d.row.remarks === undefined ? "": d.row.remarks}}
					</a>
			      </c:when>
			      <c:when test="${fns:hasPermission('tree:tree:treee:view')}">
				    <a  href="#" onclick="jp.openViewDialog('查看树的测试', '${ctx}/tree/tree/treee/form?id={{d.row.id}}','800px', '500px')">
							{{d.row.remarks === undefined ? "": d.row.remarks}}
					</a>
			      </c:when>
			      <c:otherwise>
							{{d.row.remarks === undefined ? "": d.row.remarks}}
			      </c:otherwise>
			</c:choose>
			</td>
			<td>
							{{d.row.name === undefined ? "": d.row.name}}
			</td>
			<td>
							{{d.row.sheng === undefined ? "": d.row.sheng}}
			</td>
			<td>
							{{d.row.shi === undefined ? "": d.row.shi}}
			</td>
			<td>
							{{d.row.xian === undefined ? "": d.row.xian}}
			</td>
			<td>
							{{d.row.zhen === undefined ? "": d.row.zhen}}
			</td>
			<td>
				<div class="btn-group">
			 		<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog"></i>
						<span class="fa fa-chevron-down"></span>
					</button>
				  <ul class="dropdown-menu" role="menu">
					<shiro:hasPermission name="tree:tree:treee:view">
						<li><a href="#" onclick="jp.openViewDialog('查看树的测试', '${ctx}/tree/tree/treee/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="tree:tree:treee:edit">
						<li><a href="#" onclick="jp.openSaveDialog('修改树的测试', '${ctx}/tree/tree/treee/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="tree:tree:treee:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="tree:tree:treee:add">
						<li><a href="#" onclick="jp.openSaveDialog('添加下级树的测试', '${ctx}/tree/tree/treee/form?parent.id={{d.row.id}}','800px', '500px')"><i class="fa fa-plus"></i> 添加下级树的测试</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>