<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.List"%>
<%@ page import="org.pgl.Model.AnchorBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title><s:text name="sitetitle" /></title>
<meta content="" name="description" />
<meta content="<s:text name="sitetitle"/>" name="keywords" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>

<style type="text/css">
html {
	background: none;
}

body {
	background: none;
	margin: 0px;
	padding: 0px;
}

.delimg {
	/*** absolute relative 
		border:1px solid red;
	**/
	cursor: pointer;
	position: absolute;
	right: 4px;
	top: 4px;
	overflow: hidden;
	height: 9px;
	width: 9px;
	display: none;
	background: url(<%=request.getContextPath()%>/img/popup.png) 0 -97px
		no-repeat;
}

.fontColor {
	color: #FFF;
	padding: 6px 12px;
	display: inline-block;
}

.biaodancaozuo span {
	display: inline-block;
	float: left;
	width: 30px;
	height: 60px;
	text-align: center;
}

.ui-draggable {
	background: url(<%=request.getContextPath()%>/css/img/miandian_bg.png)
		repeat;
	border-radius: 3px;
	border: 1px solid #3a87d8;
	top: 68px;
	left: 61px;
}
</style>
</head>
<s:form action="fileUpload" method="POST" enctype="multipart/form-data">
	<s:hidden id="projectId" name="projectId" />
	<s:hidden id="menuId" name="menuId" />
	<s:hidden id="fileName" name="fileName" />
	<s:hidden id="copyWriterCount" name="copyWriterCount" />
	<div class="picture_bg">
		<div class="edit_tool">
			<a href="javascript:void(0);" class="add_img" style="cursor: pointer"><span
				id="Replace"></span> <s:text name="addimage" /> <input
				style="cursor: pointer;" class="filePrew" onchange="uploadFile()"
				type="file" name="upFile" /> </a> <a
				href="javascript:addAnchor('','',0,0,95,40,1)"
				title="<s:text name="addanchor" />"><span id="Addition"></span>
				<s:text name="addanchor" /></a> <a href='#' style="margin-right: 0px"
				onclick="delpicture()" title="<s:text name="deleteimage" />"><span
				id="Delete"></span> <s:text name="deleteimage" /></a>

			<div class="clr"></div>
		</div>
		<div class="picture_box">
			<div class="picture_box2">
				<span class="picture_top"></span>
				<div id="add_picture">
					<div id="add_picture_div">
						<c:if test="${fileName!=null }">
							<img id="dragable_div"
								src="<%=request.getContextPath()%>/images/${fileName }?time="
								+${time }></img>
						</c:if>
						<c:if test="${fileName==null }">
							<img id="dragable_div"
								src="<%=request.getContextPath()%>/img/anchorImg.png"></img>
						</c:if>
					</div>
				</div>
				<div style="clear: both"></div>
				<div class="picture_bottom"></div>

			</div>
		</div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
	<div class="add_button" style="height: 1000px; overflow-y: scroll;">
		<ul class="qunzu_list">

			<!-- 文案列表 -->
			<c:if test="${ copyWriterMap != null}">
				<c:forEach items="${copyWriterMap}" var="cp">
					<li><label> <input type="radio" name="copyWriter"
							onclick="getCopyWriter(this.value)" value="${cp.key}"
							<c:if test="${cp.key == id }">checked</c:if> /> <span>${cp.value}</span>
							<img title="<s:text name="deletecopy" />"
							onclick="removeCopyWriter('${cp.key}','${loginuser.role}',this)"
							align="bottom" style="float: right;"
							src="<%=request.getContextPath()%>/css/img/delete.jpg" alt="" />
					</label></li>
				</c:forEach>
			</c:if>
			<li id="writer_li" style="display: none">
				<div>
					<input class="input2" type="text" style="display: none"
						id="newWriter" maxlength="100" /> <input class="button2"
						type="button" style="display: none" id="newWriter_btn"
						value=" <s:text name="confirm" /> " onclick="saveWirter()" /> <input
						class="button2" type="button" style="display: none"
						id="newWriter_btn_cancle" value=" <s:text name="cancel" /> "
						onclick="showBox(0)" />
					<div style="clear: both"></div>
					<font id="mes_id" color="red">${message}</font>
				</div>
			</li>
			<div style="clear: both"></div>
		</ul>
		<!--添加按钮 -->
		<div class="left_wihte">
			<a href="javascript:void(0)" onclick="showBox('${loginuser.role}')"
				style="cursor: pointer" class="blue_btton"> <span>&nbsp;&nbsp;&nbsp;<s:text
						name="addtext" /></span>
			</a>
		</div>
	</div>
</s:form>
</body>

<script type="text/javascript">

//$("#mes_id").toggle(3000);
var _m_nopermission = '<s:text name="nopermission" />';
var _m_deleteconfirm = '<s:text name="deleteconfirm" />';
	//锚点的位置信息
	var anchor_x = 0;//距上传图片左侧距离
	var anchor_y = 0;//距上传图片上方距离
	
	//为锚点生成一个随机ID标识
	var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
	
	function generateMixed() 
	{
		var res = "";
		for ( var i = 0; i < 16; i++) {
			var id = Math.ceil(Math.random() * 35);
			res += chars[id];
		}
		return res;
	}
	
	// 添加锚点--实时保存
	function addAnchor(anchorId, anchorName, x, y, anchor_width, anchor_height, type) 
	{
		// type 1:手动添加锚点  0：页面初始化锚点
		var _m_selectdir = '<s:text name="selectdir" />';
		var _m_deletefailed = '<s:text name="deletefailed" />';
		if(type==1)
		{
			var str='<%=request.getSession().getAttribute("role")%>';
			
			// role=3 产品编辑角色
			if(str==3)
			{
				alert(_m_nopermission);
				return;
			}
		}
		// 为坐标全局变量赋值
		anchor_x = x;
		anchor_y = y;
		var proId = document.getElementById("projectId").value;
		var mId = document.getElementById("menuId").value;
		if(proId == 0 || mId == 0)
		{
			alert(_m_selectdir);
			return;
		}
		
		// 手机截屏图片的大小
		var imgWidth = $("#dragable_div").attr("width");
		var imgHight = $("#dragable_div").attr("height");

		// 锚点最好是图片
		var ele_div = document.createElement("div");
		
		if(anchorId != "")
		{
			$(ele_div).attr("id", anchorId);
		}
		else{
			$(ele_div).attr("id", generateMixed());//为锚点添加一个10位长度的随机数标识
		}
		
		// 可拖拽，拖拽范围限制在dragable_div的范围内
		$(ele_div).draggable({containment:"#dragable_div",scroll:false});
		$(ele_div).bind('dblclick', function(event) {
			on_dblclick(this, event);
		});
		
	//	ele_div.style.width = anchor_width;
	//	ele_div.style.height = anchor_height;
		ele_div.style.position = "absolute";//absolute   relative
	
		var ele_font = document.createElement("font");
		
		var ele_cls = document.createElement("div");
		
		$(ele_cls).attr("class","delimg");
		$(ele_cls).attr("id",$(ele_div).attr("id"));
		
		$(ele_div).live("mouseover",function(){
			$(ele_cls).show();
		});
		$(ele_div).live("mouseout",function(){
			$(ele_cls).hide();
		});
		$(ele_cls).live("mouseover",function(){
			$(ele_cls).show();
		});
		$(".delimg").die();
		$(".delimg").live('click', function(){
			var str='<%=request.getSession().getAttribute("role")%>';
			if(str>2)
			{
				alert(_m_nopermission);
				return;
			}
			if(window.confirm(_m_deleteconfirm))
			{
				$.ajax({
					type: "post",
					data:{"anchorId":$(this).attr("id"),"projectId":$("#projectId").attr("value"),"menuId":$("#menuId").attr("value")},
					url: "removeAnchor.action",
					success: function(data)
					{
						//alert("删除成功！");
					},
					error: function()
					{
						alert(_m_deletefailed);
					}
				});
				$(this).parent().hide();//隐藏删除的元素
			}
			$(this).hide();
        });
		
		$(ele_font).attr("class","fontColor");
		if(anchorName != "" && anchorName!="null")
		{
			ele_font.innerHTML = anchorName;
			$(ele_font).attr("title",anchorName);
		}else{
			ele_font.innerHTML = "NewAnchor";
			$(ele_font).attr("title","NewAnchor");
		}
		
		ele_div.appendChild(ele_cls);
		ele_div.appendChild(ele_font);
		// 锚点位置设定
		ele_div.style.zIndex = 1;
		//ele_div.style.left = (document.getElementById("dragable_div").offsetLeft + anchor_x);
		//ele_div.style.top = (document.getElementById("dragable_div").offsetTop + anchor_y);
		var an_left = (document.getElementById("dragable_div").offsetLeft + anchor_x);
		var an_top = (document.getElementById("dragable_div").offsetTop + anchor_y);
		$(ele_div).css({left:an_left+"px",top:an_top+"px",width:anchor_width+"px",height:anchor_height+"px"});
		
		jQuery(ele_div).resizable({ containment: "#dragable_div", minHeight:25, minWidth:25 });
		
		document.getElementById("add_picture_div").appendChild(ele_div);
	}

	function showimg()
	{
		document.getElementById("dragable_div").src = document.getElementById("uploadImg").value;
	}

	//----------------------------------------------------

	function on_dblclick(myobj, event) 
	{
		// 61 68是图片区域在整个页面中的(0,0)坐标
		anchor_x = parseInt($(myobj).position().left.toString());
		anchor_y = parseInt($(myobj).position().top.toString());
		anchor_width = parseInt($(myobj).width().toString());
		anchor_height = parseInt($(myobj).height().toString());

		//加载锚点KEY  刷新right.jsp数据
		window.parent.rightFrame.location = "getAnchorKey.action?anchorId="+$(myobj).attr("id")+"&anchor_x="+anchor_x
			+"&anchor_y="+anchor_y+"&anchor_width="+anchor_width+"&anchor_height="+anchor_height+"&projectId="+$("#projectId").attr("value")+"&menuId="+$("#menuId").attr("value")
			+"&fileName="+$("#fileName").attr("value")+"&copyWriterCount="+$("#copyWriterCount").attr("value");
	}
	
	<%List<AnchorBean> anchorList = (List<AnchorBean>) request.getAttribute("anchorList");

			if (anchorList != null) {
				for (int tmp = 0; tmp < anchorList.size(); tmp++) {%>

	addAnchor("<%=anchorList.get(tmp).getAnchorId()%>","<%=anchorList.get(tmp).getAnchorName()%>", <%=anchorList.get(tmp).getAnchor_x()%>, <%=anchorList.get(tmp).getAnchor_y()%>, <%=anchorList.get(tmp).getAnchor_width()%>, <%=anchorList.get(tmp).getAnchor_height()%>, 0);

	<%}
			}%>

	function uploadFile()
	{
		var _m_selectdirforimg = '<s:text name="selectdirforimg" />';
		var menu__Id = document.getElementById("menuId").value;
		var project__Id = document.getElementById("projectId").value;
		var str='<%=request.getSession().getAttribute("role")%>';
		if(str==3)
		{
			alert(_m_nopermission);
			return;
		}
		if(menu__Id == 0 || project__Id == 0)
		{
			alert(_m_selectdirforimg);
			return;
		}
		
		
		window.document.forms[0].submit();
	}
	
	// 点击获取文案信息
	function getCopyWriter(obj)
	{
		var project__Id = document.getElementById("projectId").value;
		var menu__Id = document.getElementById("menuId").value;
		var copyWriterCount = document.getElementById("copyWriterCount").value;
		
		window.parent.rightFrame.location = "getCopyWriter.action?projectId="+project__Id+"&menuId="+menu__Id+"&id="+obj+"&copyWriterCount="+copyWriterCount;
	}
	
	// 添加文案
	function addCopyWrite()
	{
		var _m_selectdirforcopy = '<s:text name="selectdirforcopy" />';
		var project__Id = document.getElementById("projectId").value;
		var menu__Id = document.getElementById("menuId").value;
		if(project__Id==0 || menu__Id==0)
		{
			alert(_m_selectdirforcopy);
			return;
		}
		window.parent.rightFrame.location = "addCopyWriter.action?projectId="+project__Id+"&menuId="+menu__Id;
	}
	
	// 删除文案
	function removeCopyWriter(obj,role,obj2)
	{
		if(role > 2)
		{
			alert(_m_nopermission);
			return;
		}
		if(window.confirm(_m_deleteconfirm))
		{
			var _m_invalidpage = '<s:text name="invalidpage" />';
			var project_id = document.getElementById("projectId").value;
			var menu_id = document.getElementById("menuId").value;
			var file_name = document.getElementById("fileName").value;
			var cpName = $(obj2).prev("span").text();

			if(obj=="" || project_id=="" || menu_id=="")
			{
				alert(_m_invalidpage);
				return;
			}	
			
			window.location.href="removeCopyWriter.action?projectId="+project_id+"&menuId="+menu_id+"&cpId="+obj+"&fileName="+file_name+"&cpName="+cpName;
		}
	}
	
	// 新设计方案保存文案
	function showBox(role)
	{
		if(role > 2)
		{
			alert(_m_nopermission);
			return;
		}
		$("#writer_li").toggle("slow");
		$("#newWriter").toggle("slow");
		$("#newWriter_btn").toggle("slow");
		$("#newWriter_btn_cancle").toggle("slow");
		document.getElementById("newWriter").value="";
		document.getElementById("newWriter").focus();
	}
	
	function saveWirter()
	{
		var _m_selectdirforcopy = '<s:text name="selectdirforcopy" />';
		var _m_inputcopywriter = '<s:text name="inputcopywriter" />';
		var project__id = document.getElementById("projectId").value;
		var menu__id = document.getElementById("menuId").value;
		var fileName = document.getElementById("fileName").value;
		var writer__name = document.getElementById("newWriter").value;
		
		if(writer__name.trim() == "")
		{
			alert(_m_inputcopywriter);
			return;
		}
		
		if(project__id==0 || menu__id==0)
		{
			alert(_m_selectdirforcopy);
			return;
		}
		// url中特殊字符的替换 全部替换
		writer__name=writer__name.replace(/\%/g,"%25");
		writer__name=writer__name.replace(/\+/g,"%2B");
		writer__name=writer__name.replace(/\ /g,"%20");
		writer__name=writer__name.replace(/\\/g,"%2F");
		writer__name=writer__name.replace(/\?/g,"%3F");
		writer__name=writer__name.replace(/\#/g,"%23");
		writer__name=writer__name.replace(/\&/g,"%26");
		writer__name=writer__name.replace(/\=/g,"%3D");
		
		window.location.href="addNewWriter.action?projectId="+project__id+"&menuId="+menu__id
			+ "&fileName="+fileName+"&copyWriterName="+writer__name;
	}
	
	function delpicture()
	{
		var str='<%=request.getSession().getAttribute("role")%>
	';
		if (str == 3) {
			alert(_m_nopermission);
			return;
		}

		var file_name = document.getElementById("fileName").value;
		var project_id = document.getElementById("projectId").value;
		var menu_id = document.getElementById("menuId").value;

		if (file_name == "" || project_id == "" || menu_id == "") {
			return;
		}
		if (window.confirm(_m_deleteconfirm)) {
			window.location.href = "deletePic.action?projectId=" + project_id
					+ "&menuId=" + menu_id + "&fileName=" + file_name;
		}
	}

	// 提示消息的隐藏
	$("#mes_id").toggle(2000);

	// 设置center,right页面的高度
	var count = document.getElementById("copyWriterCount").value;
	var tmp_center = window.parent.document.getElementById("center_height");
	var newHeight = 1200 + (count * 38);

	$(tmp_center).attr("style", "height:" + newHeight + "px;float:left");
</script>

</html>
