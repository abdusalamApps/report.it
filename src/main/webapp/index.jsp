<%--
  Created by IntelliJ IDEA.
  User: abdusalamyabrak
  Date: 2020-02-22
  Time: 09:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel='stylesheet' type='text/css' href='login.css'>
    <link rel="stylesheet" type="text/css" href="global.css">
    <link href="https://fonts.googleapis.com/css?family=ABeeZee&display=swap" rel="stylesheet">

    <title>Report It</title>
</head>
<body>
<div class="container">
    <h1>Report It</h1>
    <form class="login-form" action="LogIn" method="post">
        <input placeholder="Username" type="text" name="user">
        <input placeholder="Password" type="password" name="password">

        <button class="text-button">Log In</button>
    </form>

</div>
</body>
</html>
