<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String ctx = request.getParameter("page_title");
%>

    <div id="menu">
        <div class="pure-menu pure-menu-open">
            <a class="pure-menu-heading" href="#">日志平台</a>
            <ul>
                <li ><a href="<%=ctx%>/index">首页</a></li>
                <li ><a href="<%=ctx%>/airchina/log">国航出票失败日志</a></li>
                <li class="menu-item-divided pure-menu-selected" ><a href="<%=ctx%>/realtime/log">实时线上日志</a></li>
            </ul>
        </div>
    </div>