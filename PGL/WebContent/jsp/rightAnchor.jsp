
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<!--
	tab页
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.3/themes/base/jquery-ui.css" type="text/css" media="all" />
-->
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/css/jquery-ui-tab.css" />
<script src="<%=request.getContextPath()%>/js/jquery.min.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui.min.js"
	type="text/javascript"></script>
<style type="text/css">
.biaodancaozuo {
	margin-bottom: 10px;
	height: 60px;
	width: 262px;
	font-size: 12px;
	color: #51575E;
	line-height: 30px;
	padding-left: 20px;
	border-bottom: 1px solid #FFF;
	box-shadow: 0px 1px 0px #e0e2e5;
}

.biaodancaozuo span {
	display: inline-block;
	float: left;
	width: 30px;
	height: 60px;
	text-align: center;
	margin-right: 45px;
	color: #93A5BD
}

.biaodancaozuo a {
	display: block;
	width: 30px;
	height: 30px;
	background: url(<%=request.getContextPath()%>/css/img/icon.png)
		no-repeat;
}

.del-tale {
	border-top: 1px solid #dddfe1;
	padding: 12px 0px;
	margin-top: 20px;
}

.del-tale a {
	display: inline-block;
	background: url(<%=request.getContextPath()%>/css/img/button-right.png)
		no-repeat;
	width: 240px;
	height: 36px;
	cursor: pointer;
}

#c_save {
	background-position: 0 -265px;
}

#c_save_dark {
	background-position: 0 -232px;
}

#c_font {
	background-position: -30px -232px;
}

#c_paly {
	background-position: -60px -232px;
}

#c_go {
	background-position: -90px -232px;
}

.editor_input input {
	height: 28px;
	border: 1px solid #b0bbc8;
	padding: 0px 10px;
	width: 160px;
	color: #51575e;
	font-size: 12px;
	line-height: 30px;
	border-radius: 2px;
	margin-bottom: 4px;
	position: relative;
	background: #f0f2f4;
}

.editor_input select {
	height: 28px;
	border: 1px solid #b0bbc8;
	padding: 2px 2px 2px 10px;
	width: 160px;
	color: #51575e;
	font-size: 12px;
	line-height: 30px;
	border-radius: 2px;
	margin-bottom: 2px;
	background: #f0f2f4;
}

.editor_input table {
	font-size: 12px;
	color: #51575e;
}

.editor_input label, right_cen label {
	width: 70px;
	text-align: left;
	display: inline-block;
	font-size: 12px;
	color: #51575E;
}

.sch-con {
	width: 158px;
	border-bottom: 1px solid gray;
	border-left: 1px solid gray;
	border-right: 1px solid gray;
	background: #fff;
	position: relative;
	z-index: 0;
	left: 76px;
}

.sch-con ul li {
	padding-left: 10px;
	list-style: none;
	line-height: 30px;
}

.sch-con ul li:hover {
	background: #e7f1f3;
}
</style>
<script type="text/javascript">
$(function() {
   $("#tabs").tabs({
    //event: 'mouseover'
	event: 'click'
   });
});
</script>

<script type="text/javascript">
	//显示隐藏语言层
	var _m_noanchor = '<s:text name="noanchor" />';
	var _m_nopermission =  '<s:text name="nopermission" />'; 
	var _m_notselectlang = '<s:text name="notselectlang" />';
	var _m_addinfofirst = '<s:text name="addinfofirst" />';
	var _m_notselectanchor = '<s:text name="notselectanchor" />';
	var _m_keyexists = '<s:text name="keyexists" />';
	var _m_deleteconfirm = '<s:text name="deleteconfirm" />';
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
		var id= $(".ui-tabs-selected").children().attr("id");
		var proId = document.getElementById("projectId").value;
		var meId = document.getElementById("menuId").value;
		var anId = document.getElementById("anchorId").value;
		var clientCode = $("#clientTypeCode_"+id).val();
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
		// 翻译角色
		if(role==3)
		{
			alert(_m_nopermission);
			return;
		}
		$("#languageDiv").hide("slow");
		$("#expertLanDiv").toggle("slow");
		$("#tabs").hide("slow");
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
	
	function hideExpertDiv()
	{
		$("#expertLanDiv").hide("slow");
	}
	
	function hideLanguageDiv()
	{
		$("#languageDiv").hide("slow");
	}
	
	function tt(role)
	{
		// 为防止重复点击导致多次保存
		document.getElementById("sa_data").style.display="none";
		document.getElementById("sa_data_dark").style.display="block";
		// window.setTimeout(showSave,3000);//3秒后执行showSave 
		
		var str="";// 非NEW TAB数据
		var newStr=""; //NEW TAB数据
		//遍历所有的tab页(不包含NEW Tab)
		var tabsCount = $("#tabs").children("div").size();
		if(role!=3)
		{
			// 非翻译角色有newTab需要-1
			tabsCount = tabsCount-1;
		}
		
		for(var i=0;i<tabsCount;i++)
		{
			// 0.取ID
			var tabId = $("#tabs").children("div:eq("+i+")").attr("id");
			var dataId = tabId.substring(5);

			// 1.取填写的KEY  没填写KEY的数据一律不保存
			var anchorKey = $("#anchorKey_"+dataId).attr("value");
			if(anchorKey!="" && anchorKey.trim()!="")
			{
				str += "id`·="+dataId;
				str += "`·!anchorKey`·="+anchorKey;
				
				// 2.取选取的ClientCode
				var clientTypeCode = $("#clientTypeCode_"+dataId).val();
				str += "`·!clientTypeCode`·="+clientTypeCode;
				
				// 3.取语言
				// var languageCount = $("#tabs_"+dataId).children(".lan").size();
				$($("#tabs_"+dataId).children(".lan")).each(function(i,val)
				{
					str += "`·!"+val.id+"`·="+val.value;
				});
				
				// 4.取备注
				var remark = $("#remark_"+dataId).attr("value");
				str += "`·!remark`·="+remark+"`·#";
			}
		}
		
		// 处理NewTab
		// A.不填写KEY 不保存改数据
		if(role!=3)
		{
			var newAnchorKey = $("#newTab_anchorKey").val();
			newAnchorKey = newAnchorKey.replace(/(^\s*)|(\s*$)/g, "");;
			if(newAnchorKey!="")
			{
				// B KEY
				newStr += "anchorKey`·="+newAnchorKey;
				
				// C ClientTypeCode
				var newClientTypeCode = $("#newTab_clientTypeCode").val();
				newStr += "`·!clientTypeCode`·="+newClientTypeCode;
				
				// D Language
				$($("#tabs_new").children(".newTab_lan")).each(function(i,val)
				{
					newStr += "`·!"+val.id+"`·="+val.value;
				});
				// E Remark
				var newRemark = $("#newTab_remark").val();
				newStr += "`·!remark`·="+newRemark;
			}
		}
		
		if(str == "" && newStr == "" )
		{
			alert(_m_addinfofirst);
			return;
		}
		// 异步提交数据
		var anchor__Id = document.getElementById("anchorId").value;
		var project_Id = document.getElementById("projectId").value;
		var menu__Id = document.getElementById("menuId").value;
		var an_x = document.getElementById("anchor_x").value;
		var an_y = document.getElementById("anchor_y").value;
		var an_w = document.getElementById("anchor_width").value;
		var an_h = document.getElementById("anchor_height").value;
		var atId = document.getElementById("anchorTableId").value;
		var quote = document.getElementById("quote").value;
		var quoteLanguageDataId = document.getElementById("quoteLanguageDataId").value;
		var newTabClientCode = $("#newTab_clientTypeCode").val();
		if(project_Id == 0 || menu__Id == 0)
		{
			alert(_m_notselectanchor);
			return;
		}
		
		jQuery.ajax({
			type: "post",
			data: {"projectId":project_Id,"menuId":menu__Id,"anchorId":anchor__Id,"anchor_x":an_x,"anchor_y":an_y,"anchor_width":an_w,"anchor_height":an_h,
				"lanData":str,"newTabData":newStr,"anchorTableId":atId,"quote":quote,"quoteLanguageDataId":quoteLanguageDataId,"newTabClientCode":newTabClientCode},
			dataType: "text",
			url: "saveAnchorInfo.action",
			success: function(data)
			{
				$("#mes_id_ok").show();
				$("#mes_id_error").hide();
			},
			error: function()
			{
				$("#mes_id_error").show();
				$("#mes_id_ok").hide();
			}
		});
	}
	
	// 校验KEY的重复性
	function checkAnchorKey(anchorKeyId, clientId)
	{
		var project__id = document.getElementById("projectId").value;
		var client__code = document.getElementById(clientId).value;
		var anchor__key = $("#"+anchorKeyId).val();
		
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
					document.getElementById(anchorKeyId).value="";
					alert(_m_keyexists);
				}
			},
			error: function()
			{
				alert("Error");
			}
		});
	}
	
	// 根据输入的KEY引用相同的KEY的语言信息
	function getInfoByKey(inputKey)
	{
		var project__id = document.getElementById("projectId").value;
		
		// 做trim()处理
		if(inputKey == "" || inputKey.replace(/(^\s*)|(\s*$)/g, "") == "")
			return;
		
		inputKey = inputKey.replace(/(^\s*)|(\s*$)/g, "");
		
		// 为了减少不必要的请求非GlobalKey格式的输入不查库--'General_'
		if(inputKey.charAt(0).toLowerCase()=='g')
		{
			jQuery.ajax({
				type: "post",
				data: {"projectId":project__id,"anchorKey":inputKey},
				dataType: "json",
				url: "quoteAnchorKey.action",
				success: function(data)
				{
					$("#displayProbe").empty();
		        	$.each(data.list,function(i,item)
					{
						$("#displayProbe").attr("style","display:block; max-height:200px;overflow:auto;margin-top: -4px;");
						$("#displayProbe").append("<div id='xila_"+item.languageDataId+"' onmouseover='chbackgroundon(&quot;xila_"+item.languageDataId+"&quot;)' "
						+ " onclick='changeProbeName(&quot;"+item.languageDataId+"&quot;,&quot;"+item.anchorKey+"&quot;)' "
						+ " style='height:25px;font-size:18;' onmouseout='chbackgroundout(&quot;xila_"+item.languageDataId+"&quot;)' >"+item.anchorKey+"</div>");
					});
				},
				error: function()
				{
				}
			});
		}
	}
	
	//---
	function chbackgroundon(id)
	{
		document.getElementById(id).style.background="#E2EAFF";
	}
	
	function chbackgroundout(id)
	{
		document.getElementById(id).style.background="white";
	}
	
	function changeProbeName(languageDataId,name)
	{
		document.getElementById("newTab_anchorKey").value=name;
		$("#quote").attr("value", 1);// 1表示引用  0表示不引用
		$("#quoteLanguageDataId").attr("value", languageDataId);
		$("#displayProbe").attr("style","display:none;");
		
		// 根据选中的anchorKey为其他项赋值
		jQuery.ajax({
			type: "post",
			data: {"quoteLanguageDataId":languageDataId},
			dataType: "json",
			url: "quoteAnchorKeyById.action",
			success: function(data)
			{
				// 外层each只会走一次
	        	$.each(data.list, function(m, it)
				{
	        		$("#newTab_remark").attr("value",it.remark);
					$("#newTab_clientTypeCode").attr("value",it.clientTypeCode);
					$.each(it.languageList,function(n,tmp)
					{
						$("#newTab_language_"+tmp.languageCode).attr("value",tmp.languageValue);
					});
				});
			},
			error: function()
			{
			}
		});
		
	}
	function changeQuote()
	{
		$("#quote").attr("value", 0);// 1表示引用  0表示不引用
		$("#quoteLanguageDataId").attr("value", 0);
	}
	// 删除TAB页
	function deleteTab(dataId)
	{
		var tabCount = $("#tabs").children("div").size();
		
		var id= document.getElementById("anchorTableId").value;
		if(window.confirm(_m_deleteconfirm))
		{
			window.location.href="deleteTab.action?anchorTableId="+id+"&languageDataId="+dataId+"&tabCoount="+tabCount;
		}
		return false;
	}
	function showSave()
	{
		document.getElementById("sa_data").style.display="block";
		document.getElementById("sa_data_dark").style.display="none";
	}
	// --
</script>
</head>

<body onload="turnOffAutoComplete();">
	<div id="btn_id" class="biaodancaozuo">
		<span id="sa_data"><a id="c_save"
			title="<s:text name="save" />" onClick="tt(${loginuser.role})"></a> <s:text
				name="save" /></span> <span id="sa_data_dark" style="display: none"><a
			id="c_save_dark" title="<s:text name="save" />"></a> <s:text
				name="save" /></span> <span><a id="c_paly"
			title="<s:text name="preview" />" onClick="previewAnchor()"></a> <s:text
				name="preview" /></span> <span style="margin-right: 0px;"><a
			id="c_go" title="<s:text name="export" />"
			onClick="showExpertDiv('${loginuser.role}')"></a> <s:text
				name="export" /></span>
	</div>

	<font style="display: none; width: 300px;" id="mes_id_ok" color="red"><s:text
			name="savesuccess" /></font>
	<font style="display: none; width: 300px;" id="mes_id_error"
		color="red"><s:text name="savefail" /></font>
	<s:form action="saveAnchorInfo" method="post">
		<s:hidden id="projectId" name="projectId" />
		<s:hidden id="menuId" name="menuId" />
		<s:hidden id="anchorId" name="anchorId" />
		<s:hidden id="anchor_x" name="anchor_x" />
		<s:hidden id="anchor_y" name="anchor_y" />
		<s:hidden id="anchor_width" name="anchor_width" />
		<s:hidden id="anchor_height" name="anchor_height" />
		<s:hidden id="anchorTableId" name="anchorTableId" />
		<s:hidden id="quote" name="quote" />
		<s:hidden id="quoteLanguageDataId" name="quoteLanguageDataId" />

	</s:form>
	<c:if test="${anchorId!=null}">
		<div id="tabs" class="editor_input"
			style="position: absolute; height: 400px; overflow: auto;">
			<ul>
				<s:iterator value="anchorInfoBeanList" status="stat">
					<li><a href="#tabs_<s:property value='languageDataId'/>"
						id="<s:property value='languageDataId'/>"><s:property
								value="anchorKey" /></a></li>
				</s:iterator>
				<c:if test="${loginuser.role!=3 }">
					<li><a style="font-weight: bold; font: 20px;" href="#tabs_new">&nbsp;&nbsp;&nbsp;&nbsp;+&nbsp;&nbsp;&nbsp;&nbsp;</a>
					</li>
				</c:if>
			</ul>
			<s:iterator value="anchorInfoBeanList" status="stat2" id="m">
				<div id="tabs_<s:property value='languageDataId'/>">
					<s:textfield id="anchorKey_%{languageDataId }" name="anchorKey"
						label="KEY" maxlength="100" readonly="true"></s:textfield>
					<label><s:text name="clienttype" /></label>
					<s:select disabled="true" id="clientTypeCode_%{languageDataId }"
						list="%{#session.se_clientMap}" listValue="value" listKey="key"
						name="clientTypeCode" />
					<c:forEach var="item" items="${languageList}" varStatus="status">
						<label>${item.languageName}</label>
						<input type="text" class="lan"
							value="<c:out value="${item.languageValue}"></c:out>"
							id="language_${item.languageCode}"
							name="<c:out value="${item.languageValue}"></c:out>"
							maxlength="500"
							<c:if  test="${fn:indexOf(loginuser.operforlang,item.languageCode)<0}" > readonly="true"</c:if> />
					</c:forEach>
					<label><s:text name="remark" /></label>
					<s:textfield id="remark_%{languageDataId }" name="remark"
						maxlength="200" />
					<c:if test="${loginuser.delTabRole==1}">
						<div class="del-tale">
							<a
								style="color: #fff; padding: 8px 0px 0px 0px; text-align: center; vertical-align: middle; font-size: 16px;"
								onClick="deleteTab(<s:property value='languageDataId'/>)"><s:text
									name="deletetab"></s:text></a>
						</div>
					</c:if>
				</div>
			</s:iterator>

			<c:if test="${loginuser.role!=3 }">
				<!-- NEW Tab Page -->
				<div id="tabs_new">
					<s:textfield id="newTab_anchorKey" name="anchorKey" label="KEY"
						maxlength="100"
						onchange="value=value.replace(/[\W]/g,''),changeQuote()"
						onblur="checkAnchorKey('newTab_anchorKey','newTab_clientTypeCode')"
						onkeyup="getInfoByKey(this.value)" />
					<div id="displayProbe" class="sch-con" style="display: none;"></div>
					<label><s:text name="clienttype" /></label>
					<s:select id="newTab_clientTypeCode"
						list="%{#session.se_clientMap}" listValue="value" listKey="key"
						name="clientTypeCode"
						onchange="checkAnchorKey('newTab_anchorKey','newTab_clientTypeCode')" />
					<!--<s:iterator value="newLanguageList" status="st">
						<s:textfield cssClass="newTab_lan"
							id="newTab_language_%{newLanguageList[#st.index].languageCode}"
							name="newLanguageList[%{#st.index}].languageValue"
							label="%{newLanguageList[#st.index].languageName}"
							maxlength="500" />
					</s:iterator>-->

					<c:forEach var="item" items="${newLanguageList}" varStatus="status">
						<label>${item.languageName}</label>
						<input type="text" class="newTab_lan"
							id="newTab_language_${item.languageCode}"
							name="${item.languageValue}" maxlength="500"
							<c:if  test="${fn:indexOf(loginuser.operforlang,item.languageCode)<0}" > readonly="true"</c:if> />
					</c:forEach>

					<label><s:text name="remark" />:</label>
					<s:textfield name="newTab_remark" maxlength="200" />
				</div>
			</c:if>

		</div>
	</c:if>

	<!-- 预览层 -->
	<div class="right_cen" id="languageDiv"
		style="display: none; position: absolute; top: 74px; display: block; width: 230px; padding: 15px; border-top-left-radius: 4px; border-top-right-radius: 4px; border-bottom-right-radius: 4px; border-bottom-left-radius: 4px; border: 1px solid rgb(204, 204, 204); background-color: rgb(255, 255, 255);">
		<c:forEach items="${sessionScope.languageList}" var="languageBean">
			<div class="right_list_box">
				<input name="lan" type="radio" value="${languageBean.languageCode}"
					onClick="showing(this.value)" /> <label>${languageBean.languageName}</label>
			</div>
		</c:forEach>
		<input style="float: right;" class="button2" type="button"
			value="<s:text name="cancel" />" onClick="hideLanguageDiv()">
		<br> <br>
	</div>

	<!-- 导出层 -->
	<!--<div class="right_cen" id="expertLanDiv" style="display: none;position:absolute; height:400px; overflow:auto;">-->
	<div class="right_cen" id="expertLanDiv"
		style="display: none; position: absolute; top: 74px; display: block; width: 230px; padding: 15px; border-top-left-radius: 4px; border-top-right-radius: 4px; border-bottom-right-radius: 4px; border-bottom-left-radius: 4px; border: 1px solid rgb(204, 204, 204); background-color: rgb(255, 255, 255);">
		<c:forEach items="${sessionScope.languageList}" var="languageBean"
			varStatus="ite">
			<div class="right_list_box">
				<label>${languageBean.languageName}</label> <input class="checkbox1"
					name="expert" type="checkbox" value="${languageBean.languageCode}" />
			</div>
		</c:forEach>

		<label style="float: left; margin: 2px 12px 0px 20px"><s:text
				name="client" /></label> <select
			style="height: 25px; width: 90px; float: left;"
			id="expert_clientCode">
			<c:forEach items="${sessionScope.se_noMixtrueMap}" var="tmp">
				<option value="${tmp.key }"
					<c:if test="${tmp.key == clientTypeCode }">selected</c:if>>${tmp.value
					}</option>
			</c:forEach>
		</select> <br> <br>
		<div
			style="height: 1px; margin-bottom: 10px; background: #CCC; border-bottom: #FFF solid 1px"></div>
		<input style="float: left; margin-left: 20px" class="button2"
			type="button" value="<s:text name="confirm" />"
			onClick="expertLanFile()"> <input
			style="float: left; margin-left: 6px" class="button2" type="button"
			value="<s:text name="cancel" />" onClick="hideExpertDiv()"> <br>
		<br>
	</div>

</body>

<script type="text/javascript">
/*
 * 禁止在输入框输入数据后显示历史记录
 */
function turnOffAutoComplete()
{  
    var inputKey = document.getElementById("newTab_anchorKey");  
    $(inputKey).attr("autocomplete", "off");
}
</script>
</html>