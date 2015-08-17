<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="page_setting.jsp"%>
<%
	List<ProjectModel> lstProject = (List<ProjectModel>)request.getAttribute("lstProject");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
<title><s:text name="sitetitle" /></title>
<meta content="" name="description"/>
<meta content="<s:text name="sitetitle" />" name="keywords"/>
<link href="css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script>
<style type="text/css">
.delimg
{
	cursor:pointer;
	position:absolute;
	right:4px;
	top:4px;
	overflow:hidden;
	height:9px;
	width:9px;
	display:none;
	z-index:100;
	background:url(<%=request.getContextPath()%>/img/del.png);
}

.depp
{
	width: 30px;
	height: 30px;
	position: absolute;
	right: -1px;
	top: -1px;
	z-index: 100; 
	display:none;
	background:url(<%=request.getContextPath()%>/img/del.png);
}
.Editor
{
        width: 30px;
        height: 30px;
        position: absolute;
        right: 36px;
        top: -1px;
        z-index: 100;
        display:none;
        background:url(<%=request.getContextPath()%>/img/Editor.png);
}
.project .Editor_box .engShort{
        font-size:22px;
        color:#FFF;
}
.project .Editor_box .chinaShort{
        font-size:14px;
        color:#FFF;
}
</style>
<script type="text/javascript">
	var _m_submitbefore = '<s:text name="submitbefore" />';
	var _m_namenull = '<s:text name="namenull" />';
	var _m_projectexists =  '<s:text name="projectexists" />';
	var _m_nopermission =  '<s:text name="nopermission" />'; 
	var _m_deleteconfirm = '<s:text name="deleteconfirm" />';
	var _info_confirm = '<s:text name="confirm" />';
	var _m_confirm = '<s:text name="confirm" />';
	var _updateprojectname = '<s:text name="updateprojectname" />';
	var _updateprojectshortname = '<s:text name="updateprojectshortname" />';
	
	function addProject() {
		if (document.getElementById("form1") != null) {
			alert(_m_submitbefore);
			return;
		}
		jQuery('#projectDiv').append("<form name='form1' id='form1' method='post' action='addProject.action'><div class='xinzeng'> <input type='text' placeholder='English name' id='engShort' class='engShort' name='engShort'/> <input type='text' placeholder='' id='chinaShort' class='chinaShort' name='chinaShort'/> <input type='submit' class='submit_button'  value='"+_info_confirm+"' onclick='checkForm()'/></div></form>");
		
		document.onkeydown = function(e){
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	checkForm();
		     }
		};
	}
	
	function checkForm() {
		var engShort = document.getElementById('engShort').value;
		var chinaShort = document.getElementById('chinaShort').value;
		if (engShort == null || engShort == '') {
			alert(_m_namenull);
			return;
		} else if (chinaShort == null || chinaShort == '') {
			alert(_m_namenull);
			return;
		} else {
			jQuery.ajax({
				type : "post",
				data : {
					"cname" : chinaShort,
					"enme" : engShort
				},
				url : "checkProjectExist.action",
				success : function(response) {
					if (response.trim() == "true") {
						alert(_m_projectexists);
						return;
					} else {
						document.getElementById("form1").submit();
					}
				}
			});

		}
	}
	
	// 删除项目
	function removeProject(proId)
	{
		var str='<%=request.getSession().getAttribute("role")%>';
		if(str>1)
		{
			alert(_m_nopermission);
			return false;
		}
		
		if(window.confirm(_m_deleteconfirm))
		{
			window.location.href="removeProject.action?projectId="+proId;
		}
		return false;
	}

	function showDel(imgId)
	{
		var str='<%=request.getSession().getAttribute("role")%>';
		if(str<3)
		{
			$("#span_"+imgId).toggle(); // 删除图标
			$("#sp_"+imgId).toggle(); // 修改图标
		}
	}
	
	//修改项目名称和简称--管理员权限
	function updateProjectName(projectId)
	{
		var role ='<%=request.getSession().getAttribute("role")%>';
		
        if(role!=1)
        {
        	return true;
        }

        if(typeof($("#en_"+projectId).attr("style"))!="undefined")
        {
        	 $("#en_"+projectId).removeAttr("style");
             $("#ch_"+projectId).removeAttr("style");
             
             $("#shortName_"+projectId).attr("style","display:none");
             $("#name_"+projectId).attr("style","display:none");
        }else
        {
        	 $("#en_"+projectId).attr("style","display:none;");
             $("#ch_"+projectId).attr("style","display:none;");
             
             $("#shortName_"+projectId).removeAttr("style");
             $("#name_"+projectId).removeAttr("style");
        }
        $("#sub_"+projectId).toggle(); // 保存按钮
        
        return false;
        
        // 以下注释掉
        /***
		var projectName="";
		var projectShortName="";
		projectShortName = window.prompt(_updateprojectname);
		projectName = window.prompt(_updateprojectshortname);
		
		if(projectName==null || projectShortName==null ||projectName=="null" || projectShortName=="null"||projectName==''||projectShortName=='')
		{
			return false;
		}
		else
		{
			window.location.href="upadteProject.action?projectId="+projectId+"&projectShortName="+projectShortName+"&projectName="+projectName;
			return false;
		}
        */
	}
	
	function checkUpdateProjectName(pid)
	{
		var en = $("#en_"+pid).val();
		var ch = $("#ch_"+pid).val();
		
		// 要trim
		if(ch==null || en==null ||ch=="null" || en=="null"||ch==''||en=='')
		{
			return false;
		}
		else
		{
			window.location.href="upadteProject.action?projectId="+pid+"&projectShortName="+en+"&projectName="+ch;
		}
		return false;
	}
</script>
</HEAD>

<BODY>
	<div class="body_bg">
	
			<!--头部 -->
			<div class="head">
				<a class="logo">
					<span class="logo_img"></span>
					<span>Client management platform.</span>
				</a>
				<a class="Allstars"></a>
			<%@ include file="navmenu.jsp" %>
			
			     
        </div>
			<!--头部结束 -->
			<div class="mian">

				<h2 class="selct_title font60">Please select the Platform</h2>

				<div class="selct" id="projectDiv">
					<div class="clr"></div>
				
					<!--添加按钮 -->
					<c:if test="${role<2 }">
						<a id="add" href="javascript:addProject()"></a>
					</c:if>
 					<div class="container">
                		<div class="project">

					<%
						if (lstProject != null && lstProject.size() > 0)
						{
							for (int i = 0; i < lstProject.size(); i++)
							{
								ProjectModel objProject = lstProject.get(i);
								if (objProject == null)
								{
									continue;
								}
					%>
						<div onmouseover="showDel(<%=objProject.getId()%>)" onmouseout="showDel(<%=objProject.getId()%>)">
							<a href="edit.action?projectid=<%=objProject.getId()%>">
								<span id="span_<%=objProject.getId()%>" class="depp" onclick="return removeProject(<%=objProject.getId()%>)"></span>
								<span id="sp_<%=objProject.getId()%>" class="Editor" onclick="return updateProjectName(<%=objProject.getId()%>)"></span>
								
								<input onclick="return false;" type='text' id='en_<%=objProject.getId()%>' class='engShort' name='engShort' style="display: none" value="<%=objProject.getShortname()%>"/>
								<input onclick="return false;" type='text' id='ch_<%=objProject.getId()%>' class='chinaShort' name='chinaShort' style="display: none" value="<%=objProject.getName()%>"/> 
		
								<h2 id="shortName_<%=objProject.getId()%>"><%=objProject.getShortname()%></h2>
								<h4 id="name_<%=objProject.getId()%>"><%=objProject.getName()%></h4>
								<input id="sub_<%=objProject.getId()%>" style="display:none;" type='button' class='submit_button' value='<s:text name="confirm"/>' onclick='return checkUpdateProjectName(<%=objProject.getId()%>)'/>
							</a>
						</div>
					<%
							}
						}
					%>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="js/hScrollPane.js"></script>
<script type="text/javascript">
$(".container").hScrollPane({
	mover:"div",
	moverW:function(){return $(".container a").length*188;}(),
	showArrow:true,
	handleCssAlter:"draghandlealter",
	mousewheel:{moveLength:188}
});

</script>
</BODY>
</html>
