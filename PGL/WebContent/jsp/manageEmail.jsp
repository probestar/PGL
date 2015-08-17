<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="sitetitle" /></title>
<link href="<%=request.getContextPath()%>/css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js" ></script>
<script type="text/javascript" >
	var _m_deleteconfirm = '<s:text name="deleteconfirm" />';
	var _m_emailerro = '<s:text name="emailerro" />';
	function showClientBox()
	{
		$("#btn_client_show").toggle("slow");
		$("#client_add").toggle("slow");
	}

	function deleteEmail(id)
	{
		if(window.confirm(_m_deleteconfirm))
		{
			window.location.href="removeEmail.action?id="+id;
		}
	}
	
	function tt()
	{
		// 正则校验邮箱地址
		var email = $("#email").val();
		var filter=/^\w+([-\.]\w+)*@\w+([\.-]\w+)*\.\w{2,4}$/;
		if (filter.test(email))
		{
			return true;
		}
		else
		{
			$("#email").attr("value","");
			alert(_m_emailerro);
			return false;
		}
	}
	
</script>
<script type="text/javascript">
	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight()
	{  
    	var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
        var bodyHeight = document.getElementById("pane2"); //寻找ID为content的对象  
        bodyHeight.style.height = (h - 100) + "px"; //你想要自适应高度的对象
    }
	setInterval(windowHeight, 500);//每半秒执行一次
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
<s:form action="addEmail" onsubmit="return tt()">
	<!-- 客户端管理 -->
	<c:if test="${emailList!=null}">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr id="list_title">
			<th width="5%"><span><s:text name="serialnumber" /></span></th>
			<th width="25%"><span><s:text name="email" /></span></th>
			<th width="10%"><span><s:text name="ineffect" /></span></th>
			<th width="10%"><span><s:text name="operation" /></span></th>
		</tr>
		<c:forEach var="bean" items="${emailList}" varStatus="t">
			<tr>
				<td ><span>${t.index+1}</span></td>
				<td >${bean.email }</td>
				<td ><span>
					<c:if test="${bean.state==0}"><s:text name="no" /></c:if>
					<c:if test="${bean.state==1}"><s:text name="yes" /></c:if></span>
				</td>
				<td >
					<span><a href="#" onclick="deleteEmail('${bean.id }')"><s:text name="delete" /></a></span>
				</td>
			</tr>
		</c:forEach>
		<tr id="client_add"  style="display: none">
			<td><span><s:text name="add" /></span></td>
			<td colspan="1" style="display:block"><span>
				<input name="email" id="email" type="text"></span>
			</td>
			<td><span>
				<select id="state" name="state">
					<option value="0"><s:text name="no" /></option>
					<option value="1"><s:text name="yes" /></option>
				</select></span>
			</td>
			<td align="center" width="25%">
            <span>
				<input class="button2" type="submit" value="<s:text name="confirm" />">
				<input class="button2" type="button" value="<s:text name="cancel" />" onclick="showClientBox()" ></span>
			</td>
		</tr>
		<tr>
			<td class="button_td" colspan="4"><span><input class="button_3" id="btn_client_show" type="button" value="<s:text name="add" />" onclick="showClientBox()"></span></td>
		</tr>
	</table>
	</c:if>

</s:form>
 </div>
 </div>
 </div>
</body>
</html>