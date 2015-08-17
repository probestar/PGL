<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="page_setting.jsp" %>
<%
    User user = (User)request.getSession().getAttribute("loginuser");
	String loginuser = (String)user.getName();
%>

<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/effects.js"></script>
<script type="text/javascript" src="js/side-bar.js"></script>

<style>

/****************************************/

a{
	outline: none;
}

a:active{
	outline: none;
}

#sideBar{
text-align:left;
}

#sideBar h2{
	color:#FFFFFF;
	font-size:110%;
	font-family:arial;
	margin:10px 10px 10px 10px;
	font-weight:bold !important;
}

#sideBar h2 span{
	font-size:125%;
	font-weight:normal !important;
}

#sideBar ul{
	margin:0px 0px 0px 0px;
	padding:0px 0px 0px 0px;
}

#sideBar li{
	margin:0px 10px 3px 10px;
	padding:2px;
	list-style-type:none;
	display:block;
	background-color:#DA1074;
	width:177px;
	color:#FFFFFF;
}

#sideBar li a{
	width:100%;
}

#sideBar li a:link,
#sideBar li a:visited{
	color:#FFFFFF;
	font-family:verdana;
	font-size:100%;
	text-decoration:none;
	display:block;
	margin:0px 0px 0px 0px;
	padding:0px;
	width:100%;
}

#sideBar li a:hover{
	color:#FFFFFF;
	text-decoration:underline;
}

#sideBar{
	position: absolute;
	width: auto;
	height: auto;
	top: 140px;
	right:0px;
	background-image:url(img/background.gif);
	background-position:top left;
	background-repeat:repeat-y;
}

#sideBarTab{
	float:left;
	height:137px;
	width:28px;
}

#sideBarTab img{
	border:0px solid #FFFFFF;
}

#sideBarContents{
	float:left;
	overflow:hidden !important;
	width:200px;
	height:145px;
}

#sideBarContentsInner{
	width:200px;
}
</style>

	<div id="sideBar">
	<a href="#" id="sideBarTab"><img src="img/slide-button.gif" alt="sideBar" title="sideBar" /></a>
	<div id="sideBarContents" style="display:none;">
		<div id="sideBarContentsInner">
			<h2><span><%=loginuser %></span></h2>
			<ul>
				<li><a href="getUserMes.action"><s:text name="modifypassword" /></a></li>
				<c:if test="${loginuser.role==1 }">
	            	<li><a href="getAllUser.action"><s:text name="usermanagement" /></a></li>
	                <li><a href="manageClient.action"><s:text name="clientmanagement" /></a></li>
	                <li><a href="manageLanguage.action"><s:text name="languagemanagement" /></a></li>
	                <li><a href="manageEmail.action"><s:text name="emailmanagement" /></a></li>
	                <li><a href="getOperList.action"><s:text name="operationlog" /></a></li>
                </c:if>
                <c:if test="${loginuser.role<3 }">
                     	<li><a href="getAllGlobalKey.action"><s:text name="globalkeymanagement" /></a></li>
                    </c:if>
				<li><a href="javascript:void(0);" onclick="javascript:if(confirm('<s:text name="logoutconfirm" />')) location.href='userQuit.action';else return;"><s:text name="logout" /></a></li>
			</ul>
		</div>
	</div>
	
</div>
