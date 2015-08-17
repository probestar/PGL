<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="page_setting.jsp" %>
<%
     User user = (User)request.getAttribute("user");
     String updateMes = (String)request.getAttribute("updateMes");
     
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>  
<head>
<link href="<%=request.getContextPath()%>/css/style.css" type="text/css" rel="stylesheet" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<title><s:text name="sitetitle" /></title>

<script type="text/javascript">
    var _m_newpasswordnull = '<s:text name="newpasswordnull" />';
    var _m_oldpasswordnull = '<s:text name="oldpasswordnull" />';
    var _m_confirmpasswordnull = '<s:text name="confirmpasswordnull" />';
    var _m_confirmpassworderror = '<s:text name="confirmpassworderror" />';
    var _m_oldpassworderror = '<s:text name="oldpassworderror" />'; 
    function checkForm()
    {
    	var oldpwd = document.getElementById('oldpwd').value;
    	var loginpwd = document.getElementById('loginpwd').value;
    	var againpwd = document.getElementById('againpwd').value;
    	var password = document.getElementById('password').value;
    	
    	if (oldpwd == null || oldpwd == '')
    	{
    		alert(_m_oldpasswordnull);
    		return;
    	}
    	if (loginpwd == null || loginpwd == '')
    	{
    		alert(_m_newpasswordnull);
    		return;
    	}
    	if (againpwd == null || againpwd == '')
    	{
    		alert(_m_confirmpasswordnull);
    		return;
    	}
    	if (loginpwd != againpwd)
    	{
    		alert(_m_confirmpassworderror);
    		document.getElementById('loginpwd').value = "";
    		document.getElementById('againpwd').value = "";
    		return;
    	}
    	
    	if (password != oldpwd)
    	{
    		alert(_m_oldpassworderror);
    		document.getElementById('oldpwd').value = "";
    		return;
    	}
    	document.getElementById('form1').submit();
    	
    }
    
    function goBack()
    {
    	window.location.href="getProject.action";
    }
</script>

<script type="text/javascript">
	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight() {  
            var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
            var bodyHeight = document.getElementById("box2"); //寻找ID为content的对象  
            bodyHeight.style.height = (h - 100) + "px"; //你想要自适应高度的对象

        }
		setInterval(windowHeight, 500);//每半秒执行一次windowHeight函

</script>

</HEAD>  
 
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
     
  <%
     if (user != null)
     {
   %>
   <form class="loginpwd_box" style=" padding:30px 0px 20px 20px;" name="form1" id="form1" method="post" action="updateUserMes.action" >
	     <input type="hidden" name="id" id="id" value="<%=user.getId() %>"/>
	     <s:hidden id="password" name="password"></s:hidden>
	     <label><s:text name="username" />：</label><%=user.getName() %><br/><br />
	     <label><s:text name="oldpassword" />：</label><input type="password" name="oldpwd" id="oldpwd" value=""/></br>
	     <label><s:text name="newpassword" />：</label><input type="password" name="loginpwd" id="loginpwd" value=""/></br>
	     <label><s:text name="confirmpassword" />：</label><input type="password" name="againpwd" id="againpwd" value=""/></br>
	     <input style="margin-left:120px;" class="button_3" type="button" value="<s:text name="confirm" />" onclick="checkForm()"/>
	     <input style="margin-left:30px;" class="button_3" type="button" value="<s:text name="cancel" />" onclick="goBack()"/>
     </form>
  <%
     }
  %>	
  
  <%
     if (updateMes != null && updateMes.length() > 0)
     {
  %>
      <div><span><%=updateMes %></span></div>
  <%
     }
  %>
 </div>
 </div>
</body>
</html>
