<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="sitetitle"/></title>
<link href="<%=request.getContextPath()%>/css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js" ></script>
<script type="text/javascript" >
	var _m_clientnameexists = '<s:text name="clientnameexists" />';
	var _m_deleteconfirm = '<s:text name="deleteconfirm" />';
	var _m_infonull = '<s:text name="infonull" />';
	var _m_filenameexists = '<s:text name="filenameexists" />';
	function showClientBox()
	{
		$("#btn_client_show").toggle("slow");
		$("#client_add").toggle("slow");
	}

	function showInputBox(clientInputId,clientOKId,clientCancleId)
	{
		$("#"+clientInputId).toggle("slow");
		$("#"+clientOKId).toggle("slow");
		$("#"+clientCancleId).toggle("slow");
	}
	
	function saveNewClient()
	{
		var newName = $("#newClientType").attr("value").trim();
		var newMixType = $("#newClientMixType").val();
		
		if(newName=="")
			return;
		
		//校验重复性
		$.ajax({
			type: "post",
			data:{"clientTypeName":newName},
			url: "checkClientName.action",
			success: function(data)
			{
				if(data.trim()=="true")
				{
					document.getElementById("newClientType").value="";
					alert(_m_clientnameexists);
					return;
				}
				else
				{
					// 保存新客户端名称
					window.location.href="addClient.action?clientTypeName=" + newName+"&mixtrueType="+newMixType;
				}
			}
		});
	}
	
	function update(clientOKId,clientId,clientCancleId,id)
	{
		$("#"+clientId).toggle("slow");
		$("#"+clientOKId).toggle("slow");
		$("#"+clientCancleId).toggle("slow");
		
		var newName = $("#"+clientId).attr("value");
		if(newName=="")
			return;
		//校验重复性
		$.ajax({
			type: "post",
			data:{"clientTypeName":newName},
			url: "checkClientName.action",
			success: function(data)
			{
				if(data.trim()=="true")
				{
					document.getElementById(obj).value="";
					alert(_m_clientnameexists);
					return;
				}
				else
				{
					//更新客户端
					window.location.href="updateClient.action?clientId="+id+"&clientTypeName="+newName;
				}
			}
		});
	}
	
	function deleteClient(obj_id,index)
	{
		//删除
		if(window.confirm(_m_deleteconfirm))
		{
			window.location.href="deleteClient.action?clientId="+obj_id;
		}
	}
	
	//-----------------------LANGUAGE------------
	
	var lan_DBID=0; // 0表示添加 ;非0修改
	
	function toggleLanguageBox()
	{
		lan_DBID=0;
		document.getElementById("lan_name").value="";
		document.getElementById("ios_name").value="";
		document.getElementById("android_name").value="";
		
		$("#trId_addLan").toggle("slow");
	}
	
	function saveLanguage()
	{
		var lanName = document.getElementById("lan_name").value;
		var iosName = document.getElementById("ios_name").value;
		var androidName = document.getElementById("android_name").value;

		if(lanName.trim()==""||iosName.trim()==""||androidName.trim()=="")
		{
			alert(_m_infonull);
			return;
		}
		
		window.location.href="saveLanguage.action?languageName="+lanName+"&ios_fileName="+iosName+"&android_fileName="+androidName+"&lan_id="+lan_DBID;
	}
	
	function setLanguage(lanId, lanName, iosName, andoridName)
	{
		lan_DBID=lanId;
		$("#lan_name").attr("value",lanName);
		$("#ios_name").attr("value",iosName);
		$("#android_name").attr("value",andoridName);
		$("#trId_addLan").show("slow");
	}
	
	function deleteLanguage(obj,code)
	{
		if(window.confirm(_m_deleteconfirm))
		{
			window.location.href="deleteLanguage.action?lan_id="+obj+"&languageCode="+code;
		}
	}
	
	function checkLanName(objId,lanName)
	{
		var _m_langexists = '<s:text name="langexists" />';
		if(lanName.trim()=="")
			return;
		
		$.ajax({
			type: "post",
			data:{"languageName":lanName},
			url: "checkLanguageName.action",
			success: function(data)
			{
				if(data.trim()=="true")
				{
					$("#"+objId).attr("value","");
					alert(_m_langexists);
				}
			}
		});
	}
	
	function checkIOSName(objId,iosName)
	{
		if(iosName.trim()=="")
			return;
		
		$.ajax({
			type: "post",
			data:{"ios_fileName":iosName},
			url: "checkIOSName.action",
			success: function(data)
			{
				if(data.trim()=="true")
				{
					$("#"+objId).attr("value","");
					alert(_m_filenameexists);
				}
			}
		});
	}
	function checkAndroidName(objId,androidName)
	{
		if(androidName.trim()=="")
			return;
		
		$.ajax({
			type: "post",
			data:{"android_fileName":androidName},
			url: "checkAndroidName.action",
			success: function(data)
			{
				if(data.trim()=="true")
				{
					$("#"+objId).attr("value","");
					alert(_m_filenameexists);
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
<s:form>
	<!-- 客户端管理 -->
	<c:if test="${clientList!=null}">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr id="list_title">
			<th width="5%"><span><s:text name="serialnumber" /></span></th>
			<th width="25%"><span><s:text name="clientname" /></span></th>
			<th width="10%"><span><s:text name="mixedtype" /></span></th>
			<th width="10%"><span><s:text name="operation" /></span></th>
		</tr>
		<c:forEach var="bean" items="${clientList}" varStatus="t">
			<tr>
				<td ><span>${t.index+1}</span></td>
				<td ><span>
					${bean.clientTypeName }
					<input id='client${bean.id }' type="text" style="display:none; ">
					<input id='client_btn_ok${bean.id }' type="button" value="<s:text name="confirm" />" style="display:none;" onclick="update(this.id,'client${bean.id }','client_btn_cancle${bean.id }','${bean.id }')">
					<input id='client_btn_cancle${bean.id }' type="button" value="<s:text name="cancel" />" style="display:none;" onclick="showInputBox('client${bean.id }','client_btn_ok${bean.id }',this.id)"></span>
				</td>
				<td ><span>
					<c:if test="${bean.mixtrueType<1}"><s:text name="no" /></c:if>
					<c:if test="${bean.mixtrueType>0}"><s:text name="yes" /></c:if></span>
				</td>
				<td ><span>
					<a href="#" onclick="showInputBox('client${bean.id }','client_btn_ok${bean.id }','client_btn_cancle${bean.id }')"><s:text name="edit" /></a>
					<a href="#" onclick="deleteClient('${bean.id }','${t.index+1}')"><s:text name="delete" /></a></span>
				</td>
			</tr>
		</c:forEach>
		<tr id="client_add"  style="display: none">
			<td><span><s:text name="add" /></span></td>
			<td colspan="1" style="display:block"><span>
				<input id="newClientType" type="text"></span>
			</td>
			<td><span>
				<select id="newClientMixType">
					<option value="0"><s:text name="no" /></option>
					<option value="1"><s:text name="yes" /></option>
				</select></span>
			</td>
			<td align="center" width="25%">
            <span>
				<input class="button2" type="button" value="<s:text name="confirm" />" onclick="saveNewClient()" >
				<input class="button2" type="button" value="<s:text name="cancel" />" onclick="showClientBox()" ></span>
			</td>
		</tr>
		<tr>
			<td class="button_td" colspan="4"><span><input class="button_3" id="btn_client_show" type="button" value="<s:text name="add" />" onclick="showClientBox()"></span></td>
		</tr>
	</table>
	</c:if>
	
	<!-- 语言管理 -->
	<c:if test="${lan_List!=null}">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr id="list_title" >
			<td width="10%"><span><s:text name="serialnumber" /></span></td>
			<td width="25%"><span><s:text name="langname" /></span></td>
			<td width="25%"><span>iOS<s:text name="exportfilename" /></span></td>
			<td width="25%"><span>android<s:text name="exportfilename" /></span></td>
			<td width="15%" ><span><s:text name="operation" /></span></td>
		</tr>
		<c:forEach var="bean" items="${lan_List}" varStatus="t">
			<tr>
				<td ><span>${t.index+1 }</span></td>
				<td><span>${bean.languageName }</span></td>
				<td ><span>${bean.ios_fileName }</span></td>
				<td ><span>${bean.android_fileName }</span></td>
				<td  ><span>
					<a href="#" onclick="setLanguage('${bean.id}','${bean.languageName}','${bean.ios_fileName}','${bean.android_fileName}')"><s:text name="edit" /></a>
					<a href="#" onclick="deleteLanguage('${bean.id}','${bean.languageCode }')"><s:text name="delete" /></a></span>
				</td>
			</tr>
		</c:forEach>
		<tr style="display: none" id="trId_addLan" >
			<td><span><s:text name="languagemanagement" /></span></td>
			<td><span><input type="text" id="lan_name" onblur="checkLanName(this.id,this.value)"></span></td>
			
			<!-- onblur="checkIOSName(this.id,this.value)"  -->
			<td><span><input type="text" id="ios_name" readonly></span></td>
			
			<!-- onblur="checkAndroidName(this.id,this.value)" -->
			<td><span><input type="text" id="android_name" readonly></span></td>
			<td><span><input class="button2" type="button" value="<s:text name="confirm" />" onclick="saveLanguage()">
			<input class="button2" type="button" value="<s:text name="cancel" />" onclick="toggleLanguageBox()"></span>
			</td>
		</tr>
		<!-- 
		<tr>
			<td colspan="5">
				<input type="button" value="添加" onclick="toggleLanguageBox()">
			</td>
		</tr>
		 -->
	</table>
	</c:if>
</s:form>
 </div>
 </div>
 </div>
</body>
</html>