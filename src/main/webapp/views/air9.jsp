<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="../static/pure-min.css">
<link rel="stylesheet" href="../static/side-menu.css">
<link rel="stylesheet" href="../static/grids-responsive-min.css">
<link rel="stylesheet" href="../static/font-awesome.css">
<link rel="stylesheet" href="../static/marketing.css">
<script src="../static/analytics.js" async=""/>
<script>
String relativelyPath=System.getProperty("user.dir");
</script>
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','/static/analytics.js','ga');

ga('create', 'UA-41480445-1', 'purecss.io');
ga('send', 'pageview');
</script>
<script src="../static/yui-min.js"></script><script async="" src="http://yui.yahooapis.com/combo?3.17.2/oop/oop-min.js&amp;3.17.2/event-custom-base/event-custom-base-min.js&amp;3.17.2/event-base/event-base-min.js&amp;3.17.2/dom-core/dom-core-min.js&amp;3.17.2/dom-base/dom-base-min.js&amp;3.17.2/selector-native/selector-native-min.js&amp;3.17.2/selector/selector-min.js&amp;3.17.2/node-core/node-core-min.js&amp;3.17.2/dom-style/dom-style-min.js&amp;3.17.2/node-base/node-base-min.js&amp;3.17.2/event-delegate/event-delegate-min.js&amp;3.17.2/node-event-delegate/node-event-delegate-min.js" id="yui_3_17_2_1_1420015944705_2" charset="utf-8"></script>
<title>航班动态</title>
</head>
<body>

<div id="layout">

    <a href="#menu" id="menuLink" class="menu-link">
        <!-- Hamburger icon -->
        <span></span>
    </a>
    <div id="menu">
        <div class="pure-menu pure-menu-open">
            <a class="pure-menu-heading" href="#">服务平台</a>
            <ul>
                <li ><a href="${ctx}/api/index">首页</a></li>
                <li class="menu-item-divided pure-menu-selected"><a href="${ctx}/qunar/air9">航空公司航班查询</a></li>
                <li ><a href="${ctx}/tair/find">tair查询</a>
                <li ><a href="${ctx}/tair/set">tair赋值</a>
            </ul>
        </div>
    </div>
    
    

<p align= center><font color=red size=4>航班动态</font></p>

<form action="${ctx}/qunar/air9" method="post" class="pure-form">

                        <input type="text" class="user login" name="date" placeholder="请输入日期（例如20150511）" value="" autofocus>
                        <input type="text" class="user login" name="flightNo"  placeholder="请输入航班号">
                        <button type="submit" class="pure-button pure-button-primary">查询</button>

</form>


<table class="pure-table">
${title }
${data }
</table>
</div>


<script src="../static/ui.js"></script>
</body>
</html>