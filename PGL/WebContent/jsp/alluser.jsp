<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>  
<HEAD>  
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<title><s:text name="sitetitle" /></title>
<meta content="" name="description">
<link href="<%=request.getContextPath()%>/css/style.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
    
  	
  	
  	
    function deleteUser(id)
    {
    	var _m_delete = '<s:text name="deleteconfirm" />';
    	if (confirm(_m_delete))
    	{
    		window.location.href = "deleteUser.action?id=" + id;
    	}
    }
    
    var lan_DBID=0; // 0表示添加 ;非0修改
	
	function toggleUserBox()
	{
		lan_DBID=0;
		document.getElementById("name").value="";
		document.getElementById("password").value="";
		document.getElementById("role").value="";
		
		$("#trId_addUser").toggle("slow");
		$("#btn_addUser").toggle("slow");
	}
    
	function editUser(userId, name, password, role, delTabRole)
	{
		lan_DBID=userId;
		$("#name").attr("value",name);
		$("#password").attr("value",password);// 回显用户密码
		$("#role").attr("value",role);
		if(delTabRole=='0')
		{
			$("#delTab_radio_y").remove("checked");
			$("#delTab_radio_n").attr("checked","checked");
		}else
		{
			$("#delTab_radio_n").remove("checked");
			$("#delTab_radio_y").attr("checked","checked");
		}
		$("#trId_addUser").show("slow");
		$("#btn_addUser").hide();
	}
	
	function saveUser()
	{
		var _m_infonull = '<s:text name="infonull" />';
		var name = document.getElementById("name").value;
		var password = document.getElementById("password").value;
		//var nickname = document.getElementById("nickname").value;
		var role = document.getElementById("role").value;
		//if(name.trim()==""||password.trim()==""||nickname.trim()==""||role.trim()=="")
		if(name.trim()==""||password.trim()==""||role.trim()=="")
		{
			alert(_m_infonull);
			return;
		}
		var delTab = 0;
		if($("#delTab_radio_y").attr("checked")=="checked")
		{
			delTab = 1;
		}
		window.location.href="saveUser.action?name="+name+"&password="+password+"&id="+lan_DBID+"&role=" + role+"&delTabRole="+delTab;
	}
	
	function checkName(objId,name)
	{
		var _m_infoexists = '<s:text name="infoexists" />';
		if(name.trim()=="")
			return;
		
		$.ajax({
			type: "post",
			data:{"name":name},
			url: "checkUserName.action",
			success: function(response)
			{
				if(response.trim()=="true")
				{
					//$("#"+objId).attr("value","");
					alert(_m_infoexists);
				}
			}
		});
	}
    
</script>
<script type="text/javascript">
	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight() {  
            var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
            var bodyHeight = document.getElementById("pane2"); //寻找ID为content的对象  
            bodyHeight.style.height = (h - 100) + "px"; //你想要自适应高度的对象

        }
		setInterval(windowHeight, 500)//每半秒执行一次windowHeight函

</script>
</HEAD>  
 
<BODY>

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
 <c:if test="${lstUser!=null}">
    <table width="100%" cellpadding="0" cellspacing="0">
       <tr id="list_title">
			<td width="5%"><span><s:text name="serialnumber" /></span></td>
            <td width="16%"><span><s:text name="username" /></span></td>
            <td width="16%"><span><s:text name="password" /></span></td>
            <td width="16%"><span><s:text name="role" /></span></td>
            <td width="16%"><span><s:text name="allowdelete" /></span></td>
            <td width="16%" ><span><s:text name="operation" /></span></td>
		</tr>
		<c:forEach var="bean" items="${lstUser}" varStatus="status" >
		    <tr>
				<td ><span>${status.index + 1}</span></td>
				<td ><span>${bean.name }</span></td>
				<td ><span>******</span></td>
				<td >
					<span>
					   <c:if test="${bean.role == 1}"><s:text name="administrator" /></c:if>
					   <c:if test="${bean.role == 2}"><s:text name="editor" /></c:if>
					   <c:if test="${bean.role == 3}"><s:text name="translator" /></c:if>
					   <c:if test="${bean.role == 4}"><s:text name="developers" /></c:if>
				   </span>
				</td>
				<td >
					<span>
						<c:if test="${bean.delTabRole == '0' }"><s:text name="no" /></c:if>
						<c:if test="${bean.delTabRole == '1' }"><s:text name="yes" /></c:if>
					</span>
				</td>
				<td><span>
					<a href="javascript:editUser(${bean.id},'${bean.name }','${bean.password }', ${bean.role },${bean.delTabRole })"><s:text name="edit" /></a>
					<a href="javascript:deleteUser(${bean.id})"><s:text name="delete" /></a></span>
				</td>
			</tr>
		</c:forEach>
		<tr style="display: none" id="trId_addUser" >
			<td><span><s:text name="user" /></span></td>
			<td><span><input type="text" id="name" onblur="checkName(this.id,this.value)" onkeyup="value=value.replace(/[\W]/g,'')"></span></td>
			<td><span><input type="text" id="password"></span></td>
			<!-- <td><input type="text" id="nickname"></td> -->
			<td>
				<span>
					<select id="role" name="role">
					   <option value ="1"><s:text name="administrator" /></option>
					   <option value ="2"><s:text name="editor" /></option>
					   <option value ="3"><s:text name="translator" /></option>
					   <option value ="4"><s:text name="developers" /></option>
					</select>
				</span>
			</td>
			<td>
				&nbsp;&nbsp;
				<input type="radio" value="1" name="delTab" id="delTab_radio_y"><s:text name="yes" /></input>
				&nbsp;&nbsp;&nbsp;
				<input type="radio" value="0" name="delTab" id="delTab_radio_n"><s:text name="no" /></input>
			</td>
			<td ><span><input class="button2" type="button" value="<s:text name="confirm" />" onclick="saveUser()">
			<input class="button2" type="button" value="<s:text name="cancel" />" onclick="toggleUserBox()"></span>
			</td>
		</tr>
		<tr>
			<td  class="button_td" colspan="5">
            <span>
				<input class="button_3" id="btn_addUser" type="button" value="<s:text name="add" />" onclick="toggleUserBox()" /></span>
			</td>
		</tr>
    </table>
 </c:if>
 </div>
 </div>
 </div>
</BODY>
</html>
