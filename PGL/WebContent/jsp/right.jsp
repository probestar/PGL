<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="org.pgl.Model.LanguageBean"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
<title><s:text name="sitetitle" /></title>
<style type="text/css">

.biaodancaozuo{
	margin-bottom:10px;
	height:60px;
	width:262px;
	font-size:12px;
	color:#51575E;
	line-height:30px;
	padding-left:20px;
	border-bottom:1px solid #FFF;
	box-shadow: 0px 1px 0px #e0e2e5;
}
.biaodancaozuo span{
	display:inline-block;
	float:left;
	width:30px;
	height:60px;
	text-align:center;
	margin-right:45px;
	color:#93A5BD
	}

.biaodancaozuo a{
	display:block;
	width:30px;
	height:30px;
	background:url(<%=request.getContextPath()%>/css/img/icon.png) no-repeat;
}
#c_save{
	background-position: 0 -265px;
}
#c_font{
	background-position:-30px -232px;
}
#c_paly{
	background-position:-60px -232px;
}
#c_go{
	background-position:-90px -232px;
}
.editor_input input{
	height:28px;
	border:1px solid #b0bbc8;
	padding:0px 10px;
	width:160px;
	color:#51575e;
	font-size:12px;
	line-height:30px;
	border-radius:2px;
	margin-bottom:4px;
	position:relative;
	background:#f0f2f4;
}
.editor_input select{
	height:28px;
	border:1px solid #b0bbc8;
	padding:2px 2px 2px 10px;
	width:160px;
	color:#51575e;
	font-size:12px;
	line-height:30px;
	border-radius:2px;
	margin-bottom:2px;
	background:#f0f2f4;
} 
.editor_input table{
	font-size:12px;
	color:#51575e;
}
.editor_input label,right_cen label{
	width:60px;
	text-align:left;
	display:inline-block;
	font-size:12px;
	color:#51575E;
}
.right_cen{
	position:absolute;
	top:0px;
	right:0px;
	padding-top:40px;
	z-index:999;
	background:#f0f2f4;
	width:270px;
	min-height:300px;
}
.button2,.right_cen .button2{
	background:url(<%=request.getContextPath()%>/css/img/button_2.png);
	width:50px;
	height:25px;
	text-align:center;
	font-size:12px;
	color:#51575e;
	margin-right:6px;
	border:none;
}
.editor_input .right_cen .checkbox1{
	width:16px;
	height:16px;
	margin-left:70px;
	
}
.right_cen .right_list_box{
	width:100%;
	height:30px;
	position:relative;
	margin-left:20px;
}
.editor_input .right_cen .right_list_box label{
	line-height:16px;
	position:absolute;
	top:2px;
	left:0px;
	height:16px;
}
.editor_input{
	padding-left:20px;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jScrollPane.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script>
<script type="text/javascript">
	//显示隐藏语言层
	var _m_noanchor = '<s:text name="noanchor" />';
	var _m_nopermission =  '<s:text name="nopermission" />'; 
	var _m_notselectlang = '<s:text name="notselectlang" />';
	var _m_addinfofirst = '<s:text name="addinfofirst" />';
	var _m_invalidpage = '<s:text name="invalidpage" />';
	var _m_keyexists = '<s:text name="keyexists" />';
	var _m_menuidnull = '<s:text name="menuidnull" />'; 
	function previewAnchor()
	{
		var proId = document.getElementById("projectId").value;
		var menuId = document.getElementById("menuId").value;
		var anchorId = document.getElementById("anchorId").value;
		
		if(proId==0 || menuId==0 || anchorId==0)
		{
			//没有获取到项目ID和菜单ID 或者没有锚点   什么也不做 文案不能预览
			alert(_m_noanchor);
			return;
		}
		
		$("#expertLanDiv").hide("slow");
		$("#languageDiv").toggle("slow");
	}
	//预览选择的语言
	function showing(obj)
	{
		var proId = document.getElementById("projectId").value;
		var meId = document.getElementById("menuId").value;
		var anId = document.getElementById("anchorId").value;
		var clientCode = document.getElementById("clientTypeCode").value;
		var fname = document.getElementById("fileName").value;
		
		if(proId == 0 || meId == 0 || anId == 0)
		{
			alert(_m_noanchor);
			return;
		}

		//selectLanguage
		window.parent.mainFrame.location ="previewAnchor.action?languageCode="+obj
		+"&projectId="+proId+"&clientTypeCode="+clientCode
		+"&menuId="+meId+"&fileName="+fname;
	}
	
	// 展示/隐藏 客户端类型div
	function showExpertDiv(role)
	{
		if(role==3)
		{
			alert(_m_nopermission);
			return;
		}
		$("#languageDiv").hide("slow");
		$("#expertLanDiv").toggle("slow");
	}
	
	// 导出
	function expertLanFile()
	{
		var clientCode;
		var r = document.getElementsByName("expert");
		
		//获取选择的语言
		var t="";
	    for(var i=0;i<r.length;i++)
	    {
	        if(r[i].checked)
	        {
	        	t+=r[i].value+",";
	    	}
	    }
	    if(t == "")
	    {
	    	alert(_m_notselectlang);
			return;
	    }
	    //获取选择的客户端
	    clientCode = document.getElementById("expert_clientCode").value;
	    //请求
	    window.location.href="expertFile.action?selectValue="+t+"&clientTypeCode="+clientCode;
	}
	
	// C标签的客户端改变请求
	function getAllLanguage()
	{
		var anchor__Key = document.getElementById("anchorKey").value;
		var writer_id = document.getElementById("copyId").value;
		
		if(anchor__Key == "")
			return;
		
		//文案
		if(writer_id!="" && writer_id != 0)
			return;
		
		var clientTypeCode = document.getElementById("clientTypeCode").value;
		var anchor__Id = document.getElementById("anchorId").value;
		var project__Id = document.getElementById("projectId").value;
		var menu__Id = document.getElementById("menuId").value;
		var file__name = document.getElementById("fileName").value;
		var an_x = document.getElementById("anchor_x").value;
		var an_y = document.getElementById("anchor_y").value;
		var an_w = document.getElementById("anchor_width").value;
		var an_h = document.getElementById("anchor_height").value;
		var lan_id = document.getElementById("languageDataId").value;
		var cp_id = document.getElementById("copyId").value;
		var cp_count = document.getElementById("copyWriterCount").value;
		//projectId  menuId 为了防止它们在刷新页面时丢失才传的 在本业务没用到
		window.parent.rightFrame.location = "getAllLanguage.action?clientTypeCode=" + clientTypeCode + 
			"&anchorId=" + anchor__Id + "&anchorKey=" + anchor__Key +
			"&projectId=" + project__Id + "&menuId=" + menu__Id + 
			"&fileName=" + file__name+"&anchor_x="+an_x+"&anchor_y="+an_y+"&anchor_width="+an_w+"&anchor_height="+an_h+
			"&languageDataId="+lan_id+"&id="+cp_id+"&copyWriterCount="+cp_count;
	}
	
	function checkAnchor()
	{
		if(document.getElementById("anchorKey").value=="")
		{
			alert(_m_addinfofirst); // 锚点或文案
			return false;
		}
		if(document.getElementById("projectId").value=="" || document.getElementById("menuId").value=="")
		{
			alert(_m_invalidpage);
			return false;
		}
		// 将选择的客户端类型赋给属性以传到后台
		document.getElementById("sel_clientCode").value = document.getElementById("clientTypeCode").value;
		return true;
	}
	
	function checkAnchorKey()
	{
		var project__id = document.getElementById("projectId").value;
		var client__code = document.getElementById("clientTypeCode").value;
		var anchor__key = document.getElementById("anchorKey").value;
		
		if(anchor__key=="")
			return;
		
		jQuery.ajax({
			type: "post",
			data: {"projectId":project__id,"clientTypeCode":client__code,"anchorKey":anchor__key},
			dataType: "text",
			url: "checkKey.action",
			success: function(data)
			{
				if(data.trim()=="true")
				{
					document.getElementById("anchorKey").value="";
					alert(_m_keyexists);
				}
			},
			error: function()
			{
				alert("Error");
			}
		});
	}
	
	function saveData()
	{
		var projectId = document.getElementById("projectId").value;
		var menuId = document.getElementById("menuId").value;
		var clientCode = document.getElementById("clientTypeCode").value;

		if(document.getElementById("anchorKey").value=="")
		{
			alert(_m_addinfofirst); // 锚点或文案
			return;
		}
		
		
		if(menuId==0)
		{
			alert(menuidnull);
			return;
		}
		if(projectId==0 || menuId==0)
		{
			alert(_m_invalidpage);
			return;
		}
		// 将选择的客户端类型赋给属性以传到后台
		document.getElementById("sel_clientCode").value = document.getElementById("clientTypeCode").value;
		
		window.document.forms[0].submit();
		
		//parent.mainFrame.location.href='loadAnchor.action?projectId='+projectId+'&menuId='+menuId+'&clientTypeCode='+clientCode;
	}
	
	function hideExpertDiv()
	{
		$("#expertLanDiv").hide("slow");
	}
	function hideLanguageDiv()
	{
		$("#languageDiv").hide("slow");
	}
</script>

	</HEAD>
	<BODY style="background-color:transparent;">
	<div id="btn_id" class="biaodancaozuo">
    	<span><a id="c_save" title="<s:text name="save" />" onclick="saveData()"></a><s:text name="save" /></span>
        <span><a id="c_paly" title="<s:text name="preview" />" onclick="previewAnchor()"></a><s:text name="preview" /></span>
        <span style=" margin-right:0px;"><a id="c_go" title="<s:text name="export" />" onclick="showExpertDiv('${loginuser.role}')"></a><s:text name="export" /></span>
    </div>
    <div class="editor_input">
    	<s:if test="%{menuId!=0}">
    	
		<s:form action="saveAnchorLanguage" method="post" onsubmit="return checkAnchor()">
			<s:hidden id="languageDataId" name="languageDataId"/>
			<s:hidden id="copyId" name="id"/>
			<s:hidden id="projectId" name="projectId"/>
			<s:hidden id="menuId" name="menuId"/>
			<s:hidden id="anchorId" name="anchorId"/>
			<s:hidden id="anchor_x" name="anchor_x"/>
			<s:hidden id="anchor_y" name="anchor_y"/>
			<s:hidden id="anchor_width" name="anchor_width"/>
			<s:hidden id="anchor_height" name="anchor_height"/>
			<s:hidden id="fileName" name="fileName"/>
			<s:hidden id="sel_clientCode" name="clientTypeCode"/>
			<s:hidden id="copyWriterCount" name="copyWriterCount"/>
			
			<table>
				<tr>
					<td>
						<c:if test="${anchorKey==null }">
							<s:textfield id="anchorKey" name="anchorKey" label="KEY" onblur="checkAnchorKey()" maxlength="100"/>
						</c:if>
						<c:if test="${anchorKey!=null }">
							<s:textfield id="anchorKey" name="anchorKey" label="KEY" maxlength="100" readonly="true"/>
						</c:if>
					</td>
					<font style="display:none; width: 300px;" id="mes_id" color="red">${message}</font>
				</tr>
				
				<tr>
					<td><label><s:text name="client" />:</label></td>
					<td>
						<select id="clientTypeCode" onchange="getAllLanguage()">
							<c:forEach items="${sessionScope.se_clientMap}" var="tmp">
								<option value="${tmp.key }" <c:if test="${tmp.key == clientTypeCode }">selected</c:if> >${tmp.value }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			
			
			<table>
			
			<!--<c:forEach var="item" items="${languageList}" varStatus="status">
					<tr>
					<td><label>${item.languageName}</label></td>
						<td>
							<input type="text"  value="${item.languageValue}"
							id="language_${item.languageCode}" name="${item.languageValue}"
							maxlength="500" <c:if  test="${fn:indexOf(loginuser.operforlang,item.languageCode)<0}" > readonly="true"</c:if> />
						</td>
					</tr>
				</c:forEach>-->
				<s:iterator value="languageList" status="stat">
					<tr>
						<td>
						<s:set name="aa" value="%{languageList[#stat.index].languageCode}"/> 
						<s:if test="#session.loginuser.operforlang.indexOf(#aa.toString())>=0">
							<s:textfield name="languageList[%{#stat.index}].languageValue" label="%{languageList[#stat.index].languageName}" maxlength="500"/>
						</s:if>
						<s:else>
						 	<s:textfield name="languageList[%{#stat.index}].languageValue" label="%{languageList[#stat.index].languageName}" maxlength="500" readonly="true"/>
						</s:else>
						</td>
					</tr>
				</s:iterator>
				<s:if test="%{languageList!=null}">
					<tr>
						<td><label><s:text name="remark" /></label><s:textfield name="remark" maxlength="200" /></td>
					</tr>
				</s:if>
			</table>
		</s:form>
		</s:if>
		<div class="right_cen" id="languageDiv" style="display: none;">
			<c:forEach items="${sessionScope.languageList}" var="languageBean" >
				<div class="right_list_box">
					<input name="lan" type="radio" value="${languageBean.languageCode}" onclick="showing(this.value)"/>  
					<label>${languageBean.languageName}</label>
				</div>
			</c:forEach>
			<input style="float:right;" class="button2" type="button" value="取消" onclick="hideLanguageDiv()"> 
			<br><br>
		</div>
		
		<div class="right_cen" id="expertLanDiv" style="display: none; ">
			<c:forEach items="${sessionScope.languageList}" var="languageBean" varStatus="ite">
				<div class="right_list_box">
	            	<label>${languageBean.languageName}</label>
					<input class="checkbox1" name="expert" type="checkbox" value="${languageBean.languageCode}"/>
                </div>
			</c:forEach>
	
			<label style="float:left; margin:2px 12px 0px 20px"><s:text name="client" /></label>
			<select style="height:25px; width:90px; float:left;" id="expert_clientCode">
				<c:forEach items="${sessionScope.se_noMixtrueMap}" var="tmp">
					<option value="${tmp.key }" <c:if test="${tmp.key == clientTypeCode }">selected</c:if> >${tmp.value }</option>
				</c:forEach>
			</select>
          
			<br><br>
            <div style=" height:1px; margin-bottom:10px; background:#CCC; border-bottom:#FFF solid 1px"></div>
			<input style="float:left; margin-left:20px" class="button2" type="button" value="<s:text name="confirm" />" onclick="expertLanFile()"> 
			<input style="float:left; margin-left:6px" class="button2" type="button" value="<s:text name="cancel" />" onclick="hideExpertDiv()"> 
			<br><br>
		</div>
		</div>
	</BODY>
	
<script type="text/javascript">
	$("#mes_id").toggle(3000);
</script>
</html>
