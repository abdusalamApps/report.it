<%--
  Created by IntelliJ IDEA.
  User: Ehsan
  Date: 2020-03-19
  Time: 07:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a class="title">Edit ProjectName</a>
<form class="new-member-form" action="GroupModifier" method="post">
    <div class="role-radio-buttons-wrapper">
        <input type="radio" class="stv-radio-button" name="member-role" value="1" id="button3" />
        <label for="button3">newProjectName</label>
    </div>
    <input type="text" placeholder="projectName" name="projectName" value="${projectName}">
    <input type="hidden" name="action" value="confirmUpdate">
    <button class="text-button">Confirm</button>
</form>
