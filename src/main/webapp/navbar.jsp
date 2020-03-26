<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: abdo
  Date: 3/11/2020
  Time: 10:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar-container">
    <div class="topnav">
        <a href="">${navbarTitle}</a>
        <div class="topnav-right">
            <c:set var="isAdmin" scope="session" value="${isAdmin}"/>
            <c:choose>
                <c:when test="${isAdmin}">
                    <a href="Administration">Home</a>
                </c:when>
                <c:otherwise>
                    <a href="TimeReporting">Home</a>
                </c:otherwise>
            </c:choose>
            <a href="Profile">Profile</a>
            <a href="Logout">Log out</a>

        </div>
    </div>
</div>

