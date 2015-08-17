<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="page_setting.jsp"%>
<%
	List<MenuModel> lstMenu = (List<MenuModel>) request.getAttribute("menuList");
	String projectid = (String) request.getAttribute("projectid");
	String key = (String) request.getAttribute("key");
	String s_key = (String) request.getAttribute("searchkey");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title><s:text name="sitetitle" /></title>
<link rel="StyleSheet" type="text/css"
	href="<%=request.getContextPath()%>/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ztreejs/jquery.ztree.all-3.5.js"></script>
<link href="<%=request.getContextPath()%>/css/style.css" type="text/css"
	rel="stylesheet" />

<script type="text/javascript">
/**
	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight() {  
        var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
        var bodyHeight = document.getElementById("box"); //寻找ID为content的对象  
        bodyHeight.style.height = (h - 140) + "px"; //你想要自适应高度的对象
    }
	setInterval(windowHeight, 500);//每半秒执行一次windowHeight函
	*/
</script>

<script type="text/javascript">
	var _m_invalidpage = '<s:text name="invalidpage" />';
    function checkLanguage(id)
    {
    	window.document.forms[id].submit();
    }
    
    function gotoCheck(id,menuid,anchor_id,clientcode)
    {
    	var project_id = document.getElementById("projectid").value;
		if(project_id=="")
		{
			alert(_m_invalidpage);
			return;
		}	
        if (anchor_id == null || anchor_id == '')
        {
            window.location.href="edit.action?projectid=" + project_id + "&menuId=" + menuid +"&id=" + id + "&clientTypeCode=" + clientcode;
        }
        else
        {
        	window.location.href="edit.action?projectid=" + project_id + "&menuId=" + menuid +"&anchorId=" + anchor_id + "&clientTypeCode=" + clientcode;
        }
    }
    
    function searchCheck()
    {
    	var project_id = document.getElementById("projectid").value;
		if(project_id=="")
		{
			alert(_m_invalidpage);
			return;
		}
		
		var searchkey = document.getElementById("searchkey").value;
		if (searchkey == "")
		{
			return;
		}
		window.location.href="searchCheck.action?projectid=" + project_id + "&searchkey=" + searchkey;
    }
    
    document.onkeydown = function(e)
    {
        if(!e)
        {
        	e = window.event;
        }
    	if((e.keyCode || e.which) == 13){
    		searchCheck();
    	}
    };
    
    // 将请求放入脚本，防止每次都打开一个新的页面
    function ty(projectId, menuId)
    {
    	window.location.href="getCheck.action?projectid="+projectId+"&menuid="+menuId;
    }
    
</script>

<script type="text/javascript">

	//var _m_treeroot = '<s:text name="treeroot" />';// 先取后用造成projectid丢失
	
	var treedata = [];
	
	var data_all = {id:-10,projectid:<%=projectid%>,pId:-1,name:'<s:text name="treeroot" />',target:"_self",url:"getCheck.action?projectid=<%=projectid%>",icon:"<%=request.getContextPath()%>/img/base.gif"};
	treedata.push(data_all);
	<%if (lstMenu != null && lstMenu.size() > 0) {
				MenuModel objMenu = null;
				for (int i = 0; i < lstMenu.size(); i++) {
					objMenu = lstMenu.get(i);
					if (objMenu == null) {
						continue;
					}%>
	var data = {id:<%=objMenu.getId()%>,projectid:<%=objMenu.getProjectid()%>,pId:<%=objMenu.getParentid()%>,name:"<%=objMenu.getName()%>",target:"_self",url:"javascript:ty(<%=projectid%>,<%=objMenu.getId()%>)"};
	treedata.push(data);
	<%}
			}%>	
	var setting = {
			edit: {
				enable: false
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, treedata);
	});
</script>
<script type="text/javascript">
	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight()
	{  
        var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
        var bodyHeight = document.getElementById("box-left"); //寻找ID为content的对象  
        bodyHeight.style.height = (h - 80) + "px"; //你想要自适应高度的对象
    }
	setInterval(windowHeight, 500);//每半秒执行一次windowHeight函
	
	window.onload = windowHeighta; //页面载入完毕执行函数  
    function windowHeighta()
	{  
		var a = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
        var bodyHeighta = document.getElementById("box"); //寻找ID为content的对象  
        bodyHeighta.style.height = (a - 108) + "px"; //你想要自适应高度的对象
    }
	setInterval(windowHeighta, 500);//每半秒执行一次windowHeight

</script>

</HEAD>

<BODY>

	<!--头部 -->
	<div class="head">
		<div class="wrapper">
			<a href="getProject.action" class="logo"> <span class="logo_img"></span><span>Client
					management platform.</span>
			</a>

			<%@ include file="navmenu.jsp"%>

			<div class="top_rigt" onclick="backMain()">
				<s:text name="editmode" />
				<span></span>
			</div>
			<div style="float: right;" class="check_search">
				<input type="text" class="text" accesskey="s" id="searchkey"
					name="searchkey"
					value="<%=key == null || key.length() <= 0 ? "" : key%>"
					autocomplete="off"> <span id="go"><a
					href="javascript:searchCheck()"></a></span>
			</div>
			<%@ include file="locales.jsp"%>
			<div style="clear: both"></div>
		</div>
	</div>
	<!--头部结束 -->
	<div class="wrapper">
		<!--内容top -->

		<!--内容left -->
		<div class="box_left">
			<div class="box_left_top"></div>

			<div id="box-left" class="dtree dtree_box">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>

			<!--tool -->

		</div>
		<!--内容left结束 -->
		<div class="box_right">
			<div class="box_left_top"></div>

			<div id="box" style="overflow: auto;">
				<div class="edit_list">
					<!--标题 -->
					<div id="list_title">
						<span class="w_95"><s:text name="columnname" /> </span> <span
							class="w_60"><s:text name="hierarchy" /> </span> <span
							class="w_101"><s:text name="key" /></span> <span class="w_101"><s:text
								name="clienttype" /></span>
						<c:forEach items="${sessionScope.languageList}" var="languageBean">
							<span class="w_92">${languageBean.languageName}</span>
						</c:forEach>
						<span class="w_98"><s:text name="operation" /></span>
					</div>
					<div style="clear: both; height: 0px;"></div>

					<!--list内容 -->

					<s:iterator value="lstCheck" id="bean" status="stat">
						<s:form action="saveCheckLanguage" method="post" theme="simple">
							<s:hidden id="projectid" name="projectid" />
							<s:hidden id="menuid" name="menuid" />
							<s:hidden id="id" name="id" value="%{lstCheck[#stat.index].id}" />
							<s:if test="#stat.odd">
								<div class="list_a">
									<div class="w_95">
										<span> <s:textfield
												name="lstCheck[%{#stat.index}].menuname"
												value="%{lstCheck[#stat.index].menuname}" readonly="true" /></span>
									</div>
									<div class="w_60">
										<span> <s:textfield
												name="lstCheck[%{#stat.index}].path"
												value="%{lstCheck[#stat.index].path}" readonly="true" /></span>
									</div>
									<div class="w_101">
										<span><s:textfield
												name="lstCheck[%{#stat.index}].anchor_key"
												value="%{lstCheck[#stat.index].anchor_key}" readonly="true" /></span>
									</div>
									<div class="w_101">
										<span><s:textfield
												name="lstCheck[%{#stat.index}].clientName"
												value="%{lstCheck[#stat.index].clientName}" readonly="true" /></span>
									</div>

									<c:forEach var="item" items="${bean.lstLanguage}"
										varStatus="status">
										<div class="w_92">
											<span> <input type="text"
												name="lstLanguage[${status.index}].languagevalue"
												value="<c:out value="${item.languagevalue}"></c:out>"
												maxlength="500"
												<c:if test="${fn:indexOf(loginuser.operforlang,item.languagecode)<0}" > readonly="true"</c:if> />
											</span>
										</div>
									</c:forEach>
									<div class="w_98">
										<a href="javascript:checkLanguage(${stat.count })"><s:text
												name="save" /></a> &nbsp; <a
											href="javascript:gotoCheck(${bean.id },${bean.menuid }, '${bean.anchor_id }',${bean.clientcode })"><s:text
												name="redirect" /></a> </span>
									</div>
								</div>
							</s:if>


							<s:if test="!#stat.odd">
								<div class="list_b">
									<div class="w_95">
										<span> <s:textfield
												name="lstCheck[%{#stat.index}].menuname"
												value="%{lstCheck[#stat.index].menuname}" readonly="true" /></span>
									</div>
									<div class="w_60">
										<span> <s:textfield
												name="lstCheck[%{#stat.index}].path"
												value="%{lstCheck[#stat.index].path}" readonly="true" /></span>
									</div>
									<div class="w_101">
										<span> <s:textfield
												name="lstCheck[%{#stat.index}].anchor_key"
												value="%{lstCheck[#stat.index].anchor_key}" readonly="true" /></span>
									</div>
									<div class="w_101">
										<span> <s:textfield
												name="lstCheck[%{#stat.index}].clientName"
												value="%{lstCheck[#stat.index].clientName}" readonly="true" /></span>
									</div>



									<c:forEach var="item" items="${bean.lstLanguage}"
										varStatus="status">
										<div class="w_92">
											<span> <input type="text"
												name="lstLanguage[${status.index}].languagevalue"
												value="${item.languagevalue}" maxlength="500"
												<c:if  test="${fn:indexOf(loginuser.operforlang,item.languagecode)<0}" > readonly="true"</c:if> />
											</span>
										</div>
									</c:forEach>

									<div class="w_98">
										<a href="javascript:checkLanguage(${stat.count })"><s:text
												name="save" /></a> &nbsp; <a
											href="javascript:gotoCheck(${bean.id },${bean.menuid }, '${bean.anchor_id }',${bean.clientcode })"><s:text
												name="redirect" /></a>
									</div>
								</div>
							</s:if>
						</s:form>
					</s:iterator>
				</div>
			</div>
			<div class="page_a">
				<a href="javascript:getList(${totlePage},1)"><s:text
						name="pageindex" /></a> <a
					href="javascript:getPreiviewList(${totlePage},${currentPage})"><s:text
						name="pageprevious" /></a> <a
					href="javascript:getNextList(${totlePage},${currentPage})"><s:text
						name="pagenext" /></a> <a
					href="javascript:getList(${totlePage},${totlePage})"><s:text
						name="pagelast" /></a>
				<s:text name="pagecurrent" />
				${currentPage}
				<s:text name="page" />
				&nbsp;&nbsp;
				<s:text name="pagetotle" />
				${totlePage}
				<s:text name="page" />
				<div style="clear: both"></div>
			</div>
			<div style="clear: both"></div>

		</div>
		<div style="clear: both"></div>
	</div>
</BODY>

<script type="text/javascript">
	
	function backMain()
	{
		window.location.href="edit.action?projectid="+<%=projectid%>;
	}

	function getNextList(totlePage, startPage)
	{
		var searchkey = $("#searchkey").val();
		if(searchkey=='')
		{
			searchkey="<%=s_key%>";
		}
		
		var tp = startPage+1;
		if(tp>totlePage || tp<1)
		{
			return;
		}

		window.location.href="getCheckList.action?&startPage="+tp+"&totlePage="+totlePage+"&searchkey="+searchkey;

	}
	
	function getPreiviewList(totlePage, startPage)
	{
		var tp = startPage-1;
		if(tp>totlePage || tp<1)
		{
			return;
		}
		window.location.href="getCheckList.action?startPage="+tp+"&totlePage="+totlePage+"&searchkey=<%=s_key%>
	";
	}
	function getList(totlePage, startPage) {
		window.location.href = "getCheckList.action?startPage=" + startPage
				+ "&totlePage=" + totlePage;
	}
</script>
</html>
