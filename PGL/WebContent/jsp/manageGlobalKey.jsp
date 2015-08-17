<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管理页</title>
<link href="<%=request.getContextPath()%>/css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js" ></script>
<script type="text/javascript" >

	function saveData()
	{
		var _m_keynull = '<s:text name="keynull" />';
		var _m_langnull = '<s:text name="langnull" />';
		var anchor_key = document.getElementById("anchorKey").value;
		var remark = document.getElementById("remark").value;
		if(anchor_key=="")
		{
			alert(_m_keynull);
			return false;
		}
		
		var languageCount = $("[id^='language_']").size();
		var allLanguage="";
		for(var i=0;i<languageCount;i++)
		{
			allLanguage+=$("[id^='language_']").eq(i).attr("id")+"`·="+$("[id^='language_']").eq(i).val()+"`·!";
		}
		
		if(allLanguage=="")
		{
			alert(_m_langnull);
			return false;
		}
		document.getElementById("allLanguage").value=allLanguage;
		window.forms[0].submit();
	}
	
	function showClientBox()
	{
		$("#client_add").toggle();
	}
	
	function deleteGlobalKey(lan)
	{
		var _m_deleteconfirm =  '<s:text name="deleteconfirm" />';
		if(window.confirm(_m_deleteconfirm))
		{
			window.location.href="deleteGlobalKey.action?languageDataId="+lan;
		}
	}
</script>

<script type="text/javascript">
	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight() {  
            var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
            var bodyHeight = document.getElementById("pane2"); //寻找ID为content的对象  
            bodyHeight.style.height = (h - 100) + "px"; //你想要自适应高度的对象

        }
		setInterval(windowHeight, 500);//每半秒执行一次windowHeight函
</script>

</head>
<body>

    	<div class="head">
        	<a href="getProject.action" class="logo">
            	<span class="logo_img"></span><span>Client management platform.</span>
            </a>
            
           <%@ include file="navmenu.jsp" %>  
           <%@ include file="locales.jsp" %>
        </div>
         	<div class="mian">
     <div class="body_top"></div>
     <div id="box2" class="body_bottom">
     <div style="overflow:auto" id="pane2">
<s:form action="addGlobalKey"  onsubmit="saveData()">
	<s:hidden name="allLanguage" id="allLanguage"></s:hidden>
	<!-- 客户端管理 -->
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr id="list_title">
			<th width="8%"><span><s:text name="serialnumber" /></span></th>
			<th width="20%"><span>ANCHORKEY</span></th>
			<th width="15%"><span><s:text name="remark" /></span></th>
				<c:forEach var="tp" items="${list }">
					<th><span>${tp.languageName }</span></th>
				</c:forEach>
			<th width="15%"><span><s:text name="operation" /></span></th>
		</tr>
		<c:forEach var="bean" items="${globalKeyList}" varStatus="t">
			<tr>
				<td ><span>${t.index+1}</span></td>
				<td ><span>${bean.anchorKey }</span></td>
				<td ><span>${bean.remark }</span></td>
				<c:forEach var="tp" items="${bean.languageList }">
					<td ><span>${tp.languageValue }</span></td>
				</c:forEach>
				<td >
					<span>
						<!--<a href="#" onclick="showInputBox('client${bean.languageDataId }','client_btn_ok${bean.languageDataId }','client_btn_cancle${bean.languageDataId}')">修改</a>-->
						<a href="#" onclick="deleteGlobalKey('${bean.languageDataId }')"><s:text name="delete" /></a>
					</span>
				</td>
			</tr>
		</c:forEach>
		
		
		<tr id="client_add" style="display:none">
			<td><span><s:text name="add" /></span></td>
			<td><span><input name="anchorKey" id="anchorKey" type="text"></span></td>
			<td><span><input name="remark" id="remark" type="text"></span></td>
			<c:forEach items="${list}" var="bean">
				<td><span><input class="tmp" id="language_${bean.languageCode }"></span></td>
				
			</c:forEach>
			<td align="center" width="15%">
            	<span>
					<input class="button2" type="submit"" value="<s:text name="confirm" />">
					<input class="button2" type="button" value="<s:text name="cancel" />" onclick="showClientBox()" >
				</span>
			</td>
		</tr>
		<tr>
			<td class="button_td" colspan="6"><span><input class="button_3" id="btn_client_show" type="button" value="<s:text name="add" />" onclick="showClientBox()"></span></td>
		</tr>
	</table>
</s:form>
 </div>
 </div>
 </div>
</body>
</html>