<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String projectid = (String)request.getSession().getAttribute("projectid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>  
<HEAD>  
<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
<title><s:text name="sitetitle" /></title>
<meta content="" name="description"/>
<meta content="<s:text name="sitetitle" />" name="keywords"/>
<link href="css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
   function check()
   {
	   window.parent.window.location.href = "getProject.action";
   }
   
   function checkMode()
   {
	   window.location.href = "getCheck.action?projectid=" + <%=projectid %>;
   }
</script>
<script type="text/javascript">
	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight() {  
            var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
            var bodyHeight = document.getElementById("box-a"); //寻找ID为content的对象  
            bodyHeight.style.height = (h - 0) + "px"; //你想要自适应高度的对象

        }
		setInterval(windowHeight, 500);//每半秒执行一次windowHeight函
		
	window.onload = windowHeighta; //页面载入完毕执行函数  
    function windowHeighta() {  
            var a = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
            var bodyHeighta = document.getElementById("box"); //寻找ID为content的对象  
            bodyHeighta.style.height = (a - 70) + "px"; //你想要自适应高度的对象

        }
		setInterval(windowHeighta, 500);//每半秒执行一次windowHeight函
		

</script>
</HEAD>  

<BODY >

<!--头部 -->
 
    	<div class="head">
        <div class="wrapper">
        	<a href="getProject.action" class="logo">
            	<span class="logo_img"></span><span>Client management platform</span>
            </a>
            
            <%@ include file="navmenu.jsp" %>
            <div class="top_rigt" onclick="checkMode()"> Check mode<span></span></div> 
            <%@ include file="locales.jsp" %>
             <div style="clear:both"></div> 
             
            </div>                  
        </div>
<!--头部结束 -->
<div class="wrapper"> 

  
  <!--内容left -->
  <iframe id="box-a" src="jsp/menu_left.jsp" name="leftFrame" allowTransparency="true" frameborder="0" style="float:left;" width="260px" scrolling="no" target="mainFrame"></iframe>
  
  <!--内容left结束 -->
	<div class="box_right">
    	<div class="box_left_top"></div>
        <div style="overflow:auto;" id="box" >
		<iframe id="center_height" src="loadAnchor.action?projectId=<%=request.getAttribute("projectid") %>&menuId=<%=request.getAttribute("menuId") %>&id=<%=request.getAttribute("id") %>&clientTypeCode=<%=request.getAttribute("clientTypeCode") %>" name="mainFrame"  width="560"  allowTransparency="true" frameBorder="0" scrolling="no"  target="rightFrame"></iframe>
        </div>
      
        <div style="clear:both"></div>
        <div class="editor_ringt_box">
		<c:choose>
			<c:when test="${id==0}">
	      		<iframe id="right_height" src="getAnchorKey.action?projectId=<%=request.getAttribute("projectid") %>&menuId=<%=request.getAttribute("menuId") %>&anchorId=<%=request.getAttribute("anchorId") %>&clientTypeCode=<%=request.getAttribute("clientTypeCode") %>" name="rightFrame" frameborder="0" style=" height:800px; float: right" width="280px" allowTransparency="true" scrolling="no"></iframe>
	      	</c:when>
	      	<c:otherwise >
	      		<iframe src="getCopyWriter.action?id=<%=request.getAttribute("id") %>&clientTypeCode=<%=request.getAttribute("clientTypeCode") %>" name="rightFrame" frameborder="0" style=" height:800px; float: right" width="280px"  allowTransparency="true" scrolling="no"></iframe>
	     	 </c:otherwise>
		</c:choose>
      <div style="clear:both"></div>
   	</div>
  </div>
  <div style="clear:both"></div>
</div>
</BODY>

</html>
