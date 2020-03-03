<%--
  Created by IntelliJ IDEA.
  User: abdusalamapps
  Date: 2020-02-23
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h3>Add new user</h3>
<form action="Administration" method="post">
    <input type="text" name="username" placeholder="username"/>
    <input type="text" name="name" placeholder="name"/>
    <input type="text" name="email" placeholder="email"/>

    <input type="hidden" name="action" value="add">
    <button>Add</button>
</form>
