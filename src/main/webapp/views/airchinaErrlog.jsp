<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:set var="ctx" value="${pageContext.request.contextPath}"/>
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
<title>国航未出票订单日志查询</title>
</head>
<body>
<p align= center><font color=red size=6>国航未出票订单错误日志查询</font></p>

<form action="${ctx }/airchina/log" method="post" class="pure-form">

                        <input type="text" class="user login" name="orderNo" placeholder="请输入国航订单号" value="" autofocus>
                        <button type="submit" class="pure-button pure-button-primary">查询</button>

</form>

<c:if test="${ flag == '2'}">
<p><font color=blue size=5>请求报文：</font></p>
${request} 

<p><font color=blue size=5>响应报文：</font></p>
${response} 
</c:if>

<c:if test="${ flag == '1' }">

<p><font color=blue size=5>暂未查询到订单${orderNo }相关报文！</font></p>

</c:if>

<c:if test="${ flag == '3' }">

<p><font color=blue size=5>暂未查询！</font></p>

</c:if>

<script src="../static/ui.js"></script>
</body>
</html>