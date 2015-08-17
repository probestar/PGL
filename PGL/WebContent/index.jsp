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
		if(name=="" || pwd =="")
		{
			return false;
		}
		return true;
	}
</script>
</head>  
 
<body>
<div class="body_bg">
<s:include value="locales.jsp"/>
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
