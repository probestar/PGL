<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="page_setting.jsp"%>
<%
	List<MenuModel> lstMenu = (List<MenuModel>)request.getSession().getAttribute("menuList");
    String projectid = (String)request.getSession().getAttribute("projectid");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE><s:text name="sitetitle" /></TITLE>
<link rel="StyleSheet" type="text/css" href="../js/dtree.css" />
<link rel="StyleSheet" type="text/css"
	href="../css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="../js/jquery-1.8.2.js"></script>
<script type="text/javascript"
	src="../js/ztreejs/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="../js/dtree.js"></script>
<link href="../css/style.css" type="text/css" rel="stylesheet" />
<style type="text/css">
html {
	background: none;
}

body {
	background: none;
}

/*解span内容过长后后面添加删除重命名操作按钮换行问题
span
{
	white-space:nowrap;
	float: left;
}*/
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
</style>
<script type="text/javascript">
	var _m_deleteconfirm = '<s:text name="deleteconfirm" />';
	var _m_deletefailed =  '<s:text name="deletefailed" />';
	var _m_treenodenull =  '<s:text name="treenodenull" />';
	var _m_treeupdatefailed = '<s:text name="treeupdatefailed" />';
	var treedata = [];
	<%if (lstMenu != null && lstMenu.size() > 0)
	{
		 MenuModel objMenu = null;
		 for (int i = 0; i < lstMenu.size(); i++)
		 {
			 objMenu = lstMenu.get(i);
			 if (objMenu == null)
			 {
				 continue;
			 }%>
	var data = {id:<%=objMenu.getId()%>,projectid:<%=objMenu.getProjectid()%>,pId:<%=objMenu.getParentid()%>,name:"<%=objMenu.getName()%>",click:"parent.mainFrame.location.href='loadAnchor.action?projectId=<%=projectid%>&menuId=<%=objMenu.getId()%>'"};
	treedata.push(data);
	
	<%}
	}%>	
	
	
	function clickNode(event,treeId,treeNode){
		parent.mainFrame.location.href="loadAnchor.action?projectId=<%=projectid%>&menuId="+treeNode.id;
		parent.rightFrame.location.href='getAnchorKey.action';
	}
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	function beforeRemove(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		if(confirm(_m_deleteconfirm)){
			//调用删除Action
			$.ajax({
					type : "post",
					data : {
						"projectid" : treeNode.projectid,
						"id" : treeNode.id
					},
					url : "deleteMenu.action",
					success : function(response) {
						if (response.trim() == "true") {
							return true;
						}else{
							alert(_m_deletefailed);
							return false;
						}
					}
				});
			return true;
		}else{
			return false;
		}
	}		
	function beforeRename(treeId, treeNode, newName) {
		if (newName.length == 0) {
			alert(_m_treenodenull);
			return false;
		}
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		if(newName!=treeNode.name){
		//调用一个方法保存到数据库
			$.ajax({
					type : "post",
					data : {
						"projectid" : treeNode.projectid,
						"updatename" : newName,
						"updateid" : treeNode.id
					},
					url : "editMenu.action",
					success : function(response) {
						if (response.trim() == "true") {
							//zTree.reAsyncChildNodes(null, "refresh");
							//zTree.expandAll(true);
							return true;
						}else{
							alert(_m_treeupdatefailed);
							return false;
						}
					}
				});
		}
		return true;
	}
	
	///拖拽回调
	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) 
		{
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	
	///拖拽完成回调
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		//updateMenuPid
		//更新节点Pid
		//调用一个方法节点Pid
		
		var newPid = targetNode==null ? -1 : targetNode.id;
		if(targetNode==null){
			newPid = -1;
		}else{
			if(moveType=="inner"){
				newPid = targetNode.id;
			}else{
				newPid = targetNode.pId==null ? -1 : targetNode.pId;
			}
		}
			$.ajax({
					type : "post",
					data : {
						"parentid" : newPid,
						"updateid" : treeNodes[0].id
					},
					url : "updateMenuPid.action",
					success : function(response) {
						if (response.trim() == "true") {
							//alert("项目已存在，请重新输入...");
							return true;
						}else{
							alert(_m_treeupdatefailed);
							return false;
						}
					}
				});
		return targetNode ? targetNode.drop !== false : true;
	}
	
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if ($("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.id + "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		
		var btn = $("#addBtn_"+treeNode.id);
		if (btn) btn.bind("click", function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var newNode = zTree.addNodes(treeNode, {id:(100 + newCount),projectid:treeNode.projectid, pId:treeNode.id, name:"new node" + (newCount++)});
			///先添加到数据库
			$.ajax({
					type : "post",
					data : {
						"parentid" : treeNode.id,
						"projectid" : treeNode.projectid,
						"name" : newNode[0].name
					},
					url : "addMenu.action",
					success : function(response,data) {
						var response_json = jQuery.parseJSON(response);
						
						if (response_json.result) 
						{
							newNode[0].id=response_json.id;
						}else{
							alert(_m_treeupdatefailed);
						}
					}
				});
			if(newNode)
				zTree.editName(newNode[0]);
		});
		
	};
	
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.id).unbind().remove();
	};
	
	$(document).ready(function(){
		//$.fn.zTree.init($("#treeDemo"), setting, treedata);
		var str='<%=request.getSession().getAttribute("role")%>';

		// role=3 产品编辑角色
		if (str == 3) {
			var setting = {
				edit : {
					enable : false
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};
			$.fn.zTree.init($("#treeDemo"), setting, treedata);
		} else {
			var setting = {
				async : {
					enable : true,
					url : "getMenuListAsync.action",
					otherParam : {
						"projectid" :
<%=projectid%>
	},
					dataFilter : filter
				},
				view : {
					expandSpeed : "fast",
					addHoverDom : addHoverDom,
					removeHoverDom : removeHoverDom,
					selectedMulti : false
				},
				edit : {
					enable : true,
					drag : {
						isMove : true,
						prev : true,
						next : true,
						inner : true
					}
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback : {
					beforeDrag : beforeDrag,
					beforeDrop : beforeDrop,
					beforeRemove : beforeRemove,
					beforeRename : beforeRename,
					onClick : clickNode
				}
			};
			$.fn.zTree.init($("#treeDemo"), setting, treedata);
		}
	});
</script>
<script type="text/javascript">
	function addMenu() {
		
		document.getElementById("add_submitbutton").value=_c;
		document.getElementById("add_cancelbutton").value=_cl;
		if (document.getElementById("addMenu").style.display == "none") {
			var id = -1;
			document.getElementById("addMenu").style.display = "block";
			document.getElementById("parentid").value = id;
		} else {
			document.getElementById("addMenu").style.display = "none";
		}
	}

	function checkForm() {
		var name = document.getElementById("name").value;
		if (name == null || name == '') {
			alert(_m_treenodenull);
			return false;
		} else {

		}
		document.getElementById("form1").submit();
	}
	function canceladd() {
		document.getElementById("name").value = "";
		document.getElementById("addMenu").style.display = "none";
	}
</script>
<script type="text/javascript">
	var _c = '<s:text name="confirm" />';
	var _cl = '<s:text name="cancel" />';
	window.onload = windowHeight; //页面载入完毕执行函数  
	function windowHeight() {
		var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
		var bodyHeight = document.getElementById("box-left"); //寻找ID为content的对象  
		bodyHeight.style.height = (h - 86) + "px"; //你想要自适应高度的对象
	}
	setInterval(windowHeight, 500);//每半秒执行一次windowHeight函
</script>
</HEAD>
<BODY>
	<div class="box_left">
		<div class="box_left_top"></div>
		<div id="box-left" class="dtree dtree_box">
			<div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree">
					<div style="clear: both"></div>
				</ul>
				<div style="clear: both"></div>
			</div>
		</div>
		<div id="addMenu" style="display: none;">
			<form class="left_form" name="form1" id="form1" method="post"
				action="addRootMenu.action">
				<input type="hidden" id="projectid" name="projectid"
					value="<%=projectid%>" /> <input type="hidden" id="parentid"
					name="parentid" value="-1" /> <input
					placeholder="<s:text name="treeaddrootnode" />" id="name"
					name="name" type="text" /> <input id="add_submitbutton" class="button2" type="button"
					value="<s:text name="confirm" />" onclick="return checkForm();" />
				<input class="button2" type="button"  id="add_cancelbutton" 
					value="<s:text name="cancel" />" onclick="canceladd();" />
			</form>
		</div>

		<div class="tool">
			<span id="tool_add"> <c:if test="${role<3 }">
					<a title="<s:text name="treeaddrootnode" />"
						href="javascript:addMenu();"></a>
				</c:if>
			</span>
		</div>
	</div>
</BODY>
</HTML>
