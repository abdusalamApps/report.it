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
            <a href="">Home</a>
            <a href="">Profile</a>
            <form class="login-form" action="LogIn" method="post">
<button type="submit" name="button" value="logout">Log out</button>
            </form>
        </div>

    </div>
</div>

