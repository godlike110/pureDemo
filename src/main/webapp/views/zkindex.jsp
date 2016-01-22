<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Zk Config</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/s2/treeView.css" />
		<link rel="stylesheet" href="${ctx}/static/pure-min.css">
		<link rel="stylesheet" href="${ctx}/static/side-menu.css">
		<link rel="stylesheet" href="${ctx}/static/grids-responsive-min.css">
		<link rel="stylesheet" href="${ctx}/static/font-awesome.css">
		<link rel="stylesheet" href="${ctx}/static/marketing.css">
		<script type="text/javascript" src="${ctx}/static/analytics.js" async="">		
		<script type="text/javascript" src="${ctx}/static/ui.js"></script>
	</head>
	<body>
		<div id="layout">
		    <a href="#menu" id="menuLink" class="menu-link">
	        <!-- Hamburger icon -->
	        <span></span>
	    </a>
	    <div id="menu">
	        <div class="pure-menu pure-menu-open">
	            <a class="pure-menu-heading" href="#">Platform</a>
	            <ul>
	                <li ><a href="${ctx}/api/index">公告栏</a></li>
	                <li ><a href="${ctx}/tair/find">tair查询</a>
	                <li ><a href="${ctx}/tair/set">tair赋值</a>
	                <li class="menu-item-divided pure-menu-selected"><a href="${ctx}/zk/index">zk 开关</a></li>
	            </ul>
	        </div>
	    </div>
	
	
		<div class="treeView">
		
		</div>


	</div>
	
	
<div class="mydiv1" id="mydiv1" style="left: 300px;background: #fff;border: 1px solid #666;position: absolute;box-shadow: 1px 1px 2px 3px #999;z-index: 1000">
<form style="
    width: 800px;/* margin-left: 200px; */margin-top: 10px;/* margin-right: 301px; */background-color: black;" class="pure-form pure-form-aligned">
    <fieldset>
        <div class="pure-control-group">
            <label for="name">KEY</label>
            <input id="key" type="text" placeholder="key">
        </div>

        <div class="pure-control-group">
            <label for="name">Password</label>
            <input id="value" type="text" placeholder="value">
        </div>

        <div class="pure-controls">
            <button type="submit" class="pure-button pure-button-primary">Submit</button>
        </div>
    </fieldset>
</form>
</div>	
		<script type="text/javascript" src="${ctx}/static/s2/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/s2/treeView.js"></script>
	</body>
</html>
