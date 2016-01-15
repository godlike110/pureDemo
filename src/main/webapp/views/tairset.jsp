<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="yui3-js-enabled" lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="A layout example with a side menu that hides on mobile, just like the Pure website.">
    <title>tair</title>
<link rel="stylesheet" href="${ctx}/static/pure-min.css">
<link rel="stylesheet" href="${ctx}/static/side-menu.css">
<link rel="stylesheet" href="${ctx}/static/grids-responsive-min.css">
<link rel="stylesheet" href="${ctx}/static/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/marketing.css">
<script src="${ctx}/static/analytics.js" async="">
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','/static/analytics.js','ga');
ga('create', 'UA-41480445-1', 'purecss.io');
ga('send', 'pageview');
</script>
</head>
<body>
<div id="layout">
    <!-- Menu toggle -->
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
                <li class="menu-item-divided pure-menu-selected"><a href="${ctx}/tair/set">tair赋值</a>
            </ul>
        </div>
    </div>
    <div id="main">
    
<div class="splash-container">
    <div class="splash">

<form action="${ctx}/tair/set" method="post" class="pure-form">

<!--                         <input type="text" class="user login" name="env" placeholder="请输入环境参数（例如dev 或者 qa）" value="" autofocus> -->
                        <select name="env" >
  							<option value="dev">dev环境</option>
  							<option value="qa">qa环境</option>
						</select>
                        <input type="text" class="user login" name="key"  placeholder="请输入key">
                        <input type="text" class="user login" name="value"  placeholder="请输入value">
                        <button type="submit" class="pure-button pure-button-primary">设置</button>

</form>


<table class="pure-table">
${result}
</table>
<br>

	 
    </div>
</div>
    
    </div>
</div>
<script src="${ctx}/static/ui.js"></script>
</body>
</html>
