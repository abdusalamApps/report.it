<%--
  Created by IntelliJ IDEA.
  User: abdo
  Date: 3/18/2020
  Time: 9:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a class="title">Edit Member</a>
<form class="new-member-form" action="GroupModifier" method="post">
    <div class="role-radio-buttons-wrapper">
        <input type="radio" class="stv-radio-button" name="member-role" value="1" id="button3" />
        <label for="button3">Leader</label>
        <input type="radio" class="stv-radio-button" name="member-role" value="2" id="button4" />
        <label for="button4">Member</label>
    </div>
    <input type="text" placeholder="Username" name="memberUsername" value="${memberUsername}">
    <input type="hidden" name="action" value="confirmUpdate">
    <button class="text-button">Confirm</button>
</form>


