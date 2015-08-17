<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
     String errorMes = (String)request.getAttribute("errorMes");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><s:text name="sitetitle"/></title>
<link href="css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	if(self.location!=top.location)
	{ 
		top.location.href=self.location.href;
	}
    
	function checkForm()
    {
    	document.getElementById("form").submit();
    }
	
	function check()
	{
		var name = document.getElementById("loginname").value;
		var pwd =  document.getElementById("loginpwd").value;
		//if(name=="" || pwd =="")
		//{
		//	alert("User name or password is empty, please re-enter.");
		//	return false;
		//}
		return true;
	}
	function langSelecter_onChanged() {
        document.langForm.submit();
    }
	function init(){
		document.getElementById("user_local").value = document.getElementById("langSelecter").value;
	}
</script>
</head>  
 
<body onload="init();">
<div class="body_bg">
<s:set name="SESSION_LOCALE" value="#session['WW_TRANS_I18N_LOCALE']"/>
<s:bean id="locales" name="org.pgl.util.Locales"/>
	<form action="<s:url value="login.action" includeParams="get" encode="true"/>" name="langForm" style="background-color: powderblue; padding-top: 4px; padding-bottom: 4px;">
    Language: <s:select label="Language" 
        list="#locales.locales" listKey="value"    listValue="key"
        value="#SESSION_LOCALE == null ? locale : #SESSION_LOCALE"
        name="request_locale" id="langSelecter" 
        onchange="langSelecter_onChanged()" theme="simple"/>
	</form>
	<div class="mian">
    	<div class="login">
        	<!--登陆标题 -->
        	<div class="login_title">
            </div>
            <form name="form" id="form" method="post" action="login.action" onsubmit="return check()">
        	<!--登陆标题结束 -->
            <div class="login_bg">
            	<!--用户名 -->
            	<div class="item">
                	<span class="font20"><s:text name="username"/>:</span>
                    <input type="text" id="loginname" name="loginname" class="font18_shuoming" onkeydown="if(event.keyCode==13) document.getElementById('loginpwd').focus();" />
                </div>
                <!-- 用户密码-->
            	<div class="item">
                	<span class="font20"><s:text name="password"/>:</span>
                    <input type="password" id="loginpwd" name="loginpwd" class="font18_shuoming" onkeydown="if(event.keyCode==13) checkForm();" />
                    <input type="hidden" id="user_local" name="user_local"  />
                </div> 
                 <!--登陆按钮 -->              
            	<div class="item">
                    <input type="submit" id="loginsubmit" value="<s:text name="login"/>" />
                    
                </div>                      
            </div>
            </form>
        </div>       
    </div>
</div>

</body>
</html>
<script type="text/javascript">
<%
    if (errorMes != null && errorMes.length() > 0)
    {
%>
    alert("<%=errorMes %>");
<%
    }
%>
</script>
