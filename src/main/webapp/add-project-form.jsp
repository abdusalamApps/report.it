<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2020-03-05
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h3>Add new user</h3>
<form action="Administration" method="post">

    <input type="text" name="projectname" placeholder="projectname"/>


    <input type="hidden" name="action" value="addProject">
    <button>Add</button>
</form>
