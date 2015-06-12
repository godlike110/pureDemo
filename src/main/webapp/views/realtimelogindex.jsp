<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="yui3-js-enabled" lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="A layout example with a side menu that hides on mobile, just like the Pure website.">
    <title>实时日志</title>
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
            <a class="pure-menu-heading" href="#">服务平台</a>
            <ul>
                <li ><a href="${ctx}/index">首页</a></li>
                <li ><a href="${ctx}/airchina/log">国航出票失败日志</a></li>
                 <li ><a href="${ctx}/qunar/ordersearch">各旗舰店订单查询</a></li>
                 <li ><a href="${ctx}/log/phonecode">取手机验证码</a></li>
                 <li ><a href="${ctx}/log/bclog">各旗舰店订单行为</a></li>
                <li class="menu-item-divided pure-menu-selected" ><a href="${ctx }/realtime/log">实时线上日志</a></li>
            </ul>
        </div>
    </div>
 <div id="main">
    
<div class="splash-container">
    <div class="splashchina1">

<form action="${ctx}/realtime/log" method="post" class="pure-form">

                        <input type="text" class="user login" name="site" placeholder="请输入航司（例如：tjair）" value="" autofocus>
                        <input type="text" class="user login" name="len"  placeholder="请输入日志步长，可以不输入（例如：０）">
                        <button type="submit" class="pure-button pure-button-primary">查询</button>
</form>

${logs}

<p><font color=blue size=5>旗舰店site如下：<br></font></p>
<p><font size=2>首航旗舰店:capair<br>国航旗舰店:airchina<br>深圳航空旗舰店:shenzhenair<br>昆明航空旗舰店:kmair<br>东航旗舰店:ceair<br>邮政东航航空旗舰店:bjpostair<br>西部航空旗舰店:westair<br>南航旗舰店:csair<br>天津航空旗舰店:tjair<br>海航旗舰店:hnair<br>吉祥航空旗舰店:jxair<br>九元航空旗舰店:air9<br>川航旗舰店:scal<br>祥鹏航空旗舰店:luckyair<br>长龙航空旗舰店:loongair<br></font></p>



    </div>
</div>
    
    </div>
</div>
<script src="${ctx}/static/ui.js"></script>
</body>
</html>
