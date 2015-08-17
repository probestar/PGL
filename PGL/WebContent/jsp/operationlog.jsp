<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="page_setting.jsp" %>
<%
    String key = (String)request.getAttribute("key");
    String s_key = (String)request.getAttribute("searchkey");
    String searchuserid = String.valueOf(request.getAttribute("searchuserid"));
    String opertype = String.valueOf(request.getAttribute("opertype"));
    String operdate = (String)request.getAttribute("operdate");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>  
<HEAD>  
<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
<title><s:text name="sitetitle"/></title>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.9.2.custom.min.js"></script>
	<link href="<%=request.getContextPath()%>/css/jquery-ui-1.9.2.custom.min.css" type="text/css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/css/style.css" type="text/css" rel="stylesheet" />
<style type="text/css">
#search_div input{
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
#search_div select{
	height:28px;
	border:1px solid #b0bbc8;
	padding:2px 2px 2px 10px;
	width:180px;
	color:#51575e;
	font-size:12px;
	line-height:30px;
	border-radius:2px;
	margin-bottom:2px;
	background:#f0f2f4;
} 
#search_div table{
	font-size:12px;
	color:#51575e;
}
#search_div label,right_cen label{
	
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
#search_div .right_cen .checkbox1{
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
#search_div .right_cen .right_list_box label{
	line-height:16px;
	position:absolute;
	top:2px;
	left:0px;
	height:16px;
}
#search_div{
	padding-left:20px;
}
</style>
<script type="text/javascript">
$(function() {
  $( "#operdate" ).datepicker({ dateFormat: "yy-mm-dd" });
  
  $("#opertype").find("option[value='<%=opertype%>']").attr("selected",true);
});

    
    function searchLogger()
    {
    	var userid = document.getElementById("users").value;
    	var opertype = document.getElementById("opertype").value;
		var searchkey = document.getElementById("keyword").value;
		var operdate = document.getElementById("operdate").value;
		window.location.href="searchOperLogger.action?searchuserid=" + userid + "&opertype="+ opertype +"&searchkey=" + searchkey+"&operdate="+operdate;
    }
    
    document.onkeydown = function(e)
    {
        if(!e)
        {
        	e = window.event;
        }
    	if((e.keyCode || e.which) == 13){
    		searchLogger();
    	}
    };
    
    // 将请求放入脚本，防止每次都打开一个新的页面
    function ty(projectId, menuId)
    {
    	window.location.href="getCheck.action?projectid="+projectId+"&menuid="+menuId;
    }
    
</script>

<script type="text/javascript" >
	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight()
	{  
        var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
        var bodyHeight = document.getElementById("box-left"); //寻找ID为content的对象  
        bodyHeight.style.height = (h - 80) + "px"; //你想要自适应高度的对象
    }
	setInterval(windowHeight, 500);//每半秒执行一次windowHeight函
	
	window.onload = windowHeighta; //页面载入完毕执行函数  
    function windowHeighta()
	{  
		var a = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
        var bodyHeighta = document.getElementById("box"); //寻找ID为content的对象  
        bodyHeighta.style.height = (a - 108) + "px"; //你想要自适应高度的对象
    }
	setInterval(windowHeighta, 500);//每半秒执行一次windowHeight

</script>

</HEAD>  

<BODY>

<!--头部 -->
	<div class="head">
    <div class="wrapper">
        	<a href="getProject.action" class="logo">
            	<span class="logo_img"></span><span>Client management platform.</span>
            </a>
            <%@ include file="navmenu.jsp" %>
            
            <div class="top_rigt" onclick="backMain()"><s:text name="editmode" /><span></span></div> 
          <%@ include file="locales.jsp" %>
    <div style="clear:both"></div> 
    </div>                
        </div>
<!--头部结束 -->
<div class="wrapper"> 
  <!--内容top -->

  <!--内容left -->
  <div class="box_left">
    <div class="box_left_top"></div>
	
  <div id="box-left" class="dtree dtree_box">
    <div class="zTreeDemoBackground left" >
	    <div id="search_div" >
	    	<label><s:text name="username" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
		    <s:select id="users" name="users" list="#request.usermap" listKey="key" listValue="value" headerKey="0" headerValue="All"  value="#request.searchuserid"></s:select>
			<label><s:text name="operationtype" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
		    <select name="opertype" id="opertype" >
			    <option value="0" selected="selected"><s:text name="all" /></option>
			    <option value="1"><s:text name="insert" /></option>
			    <option value="2"><s:text name="update" /></option>
			    <option value="3"><s:text name="delete" /></option>
			    <option value="4"><s:text name="query" /></option>
			</select>
		    <label for="keyword" class="label"><s:text name="keyword" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
		    <input type="text" name="keyword" maxlength="100" value="<%=key == null || key.length() <= 0 ? "" : key %>" id="keyword"  autocomplete="off">
		    
		    <label><s:text name="date" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
		    <input type="text" name="datepicker" maxlength="100" id="operdate" value="<%=operdate == null || operdate.length()<=0 ? "" : operdate %>"  autocomplete="off">
		    <button class="button_3" id="btn_client_show" type="button"  value="Search" onclick="searchLogger()" ><s:text name="search" /></button>
		</div>
	</div>
  </div>
  
      <!--tool -->
 
  </div>
  <!--内容left结束 -->
	<div class="box_right">
    	<div class="box_left_top"></div>

      <div id="box" style="overflow:auto;">
        <div class="edit_list">
          <!--标题 -->
          <div id="list_title"> 
          	  <span class="w_95"><s:text name="serialnumber" /> </span> 
	          <span class="w_95"><s:text name="username" /> </span> 
	          <span class="w_95"><s:text name="operation" /> </span> 
	          <span class="w_1000"><s:text name="detailed" /></span> 
	          <span class="w_150"><s:text name="createdatetime" /></span>
           </div>
           <div style="clear: both;height: 0px;"></div>

          <!--list内容 -->
         
          <s:iterator value="listLogger" id="bean" status="stat">
              
              <s:if test="#stat.odd">
	          <div class="list_a" style="height:60px;">
	          	<div class="w_95"  style="height:60px;"> 
	            	<span><s:label value="%{listLogger[#stat.index].id}" /></span>
	            </div>
	            <div class="w_95"  style="height:60px;"> 
	            	<span> <s:label value="%{listLogger[#stat.index].username}" /></span>
	            </div>
	            <div class="w_95"  style="height:60px;"> 
	            	<span> <s:label value="%{listLogger[#stat.index].opertype}" /></span>
	            </div>
	            <div class="w_1000" style="height:60px;"> 
	            	<span><s:label title="%{listLogger[#stat.index].operation}"  value="%{listLogger[#stat.index].operation}" /></span>
	            </div>
	            <div class="w_150" style="height:60px;"> 
	           		<span><label><s:date name="%{listLogger[#stat.index].createdatetime}" format="yyyy-MM-dd HH:mm:ss" /></label></span>
	            </div>
	          </div>
	          </s:if>
	          <s:if test="!#stat.odd">
	            <div class="list_b" style="height:60px;">
		            <div class="w_95" style="height:60px;"> 
		            	<span><s:label value="%{listLogger[#stat.index].id}" /></span>
		            </div>
		            <div class="w_95" style="height:60px;"> 
		            	<span> <s:label value="%{listLogger[#stat.index].username}" /></span>
		            </div>
		            <div class="w_95" style="height:60px;"> 
		            	<span> <s:label value="%{listLogger[#stat.index].opertype}" /></span>
		            </div>
		            <div class="w_1000" style="height:60px;"> 
		            	<span><s:label  title="%{listLogger[#stat.index].operation}"  value="%{listLogger[#stat.index].operation}" /></span>
		            </div>
		            <div class="w_150" style="height:60px;"> 
		           		<span><label><s:date name="%{listLogger[#stat.index].createdatetime}" format="yyyy-MM-dd HH:mm:ss" /></label></span>
		            </div>
	            </div>
	          </s:if>
          </s:iterator>
        </div>
        </div>
        <div class="page_a">
        	<a href="javascript:getList(${totlePage},1)"><s:text name="pageindex" /></a>
        	<a href="javascript:getPreiviewList(${totlePage},${currentPage})"><s:text name="pageprevious" /></a>
        	<a href="javascript:getNextList(${totlePage},${currentPage})"><s:text name="pagenext" /></a>
        	<a href="javascript:getList(${totlePage},${totlePage})"><s:text name="pagelast" /></a>
        	<s:text name="pagecurrent" /> ${currentPage} <s:text name="page" /> &nbsp;&nbsp;
        	<s:text name="pagetotle" />  ${totlePage} <s:text name="page" />
            <div style="clear:both"></div>
            </div>
            <div style="clear:both"></div>
   
  </div>
  <div style="clear:both"></div>
</div>
</BODY>

<script type="text/javascript">
	
	function backMain()
	{
	}

	function getNextList(totlePage, startPage)
	{
		var searchkey = $("#searchkey").val();
		if(searchkey=='')
		{
			searchkey="<%=s_key%>";
		}
		
		var tp = startPage+1;
		if(tp>totlePage || tp<1)
		{
			return;
		}

		window.location.href="getOperListPagging.action?&startPage="+tp+"&totlePage="+totlePage+"&searchuserid=<%=searchuserid%>&opertype=<%=opertype%>&searchkey=<%=s_key%>&operdate=<%=operdate%>";

	}
	
	function getPreiviewList(totlePage, startPage)
	{
		var tp = startPage-1;
		if(tp>totlePage || tp<1)
		{
			return;
		}
		window.location.href="getOperListPagging.action?startPage="+tp+"&totlePage="+totlePage+"&totlePage="+totlePage+"&searchuserid=<%=searchuserid%>&opertype=<%=opertype%>&searchkey=<%=s_key%>&operdate=<%=operdate%>";
	}
	function getList(totlePage, startPage)
	{
		window.location.href="getOperListPagging.action?startPage="+startPage+"&totlePage="+totlePage+"&searchuserid=<%=searchuserid%>&opertype=<%=opertype%>&searchkey=<%=s_key%>&operdate=<%=operdate%>";
	}
</script>
</html>
