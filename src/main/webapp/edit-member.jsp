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
        <input type="radio" class="stv-radio-button" name="member-role" value="1" id="button6" />
        <label for="button6">Leader</label>
        <input type="radio" class="stv-radio-button" name="member-role" value="2" id="button7" />
        <label for="button7">Member</label>
        <input type="radio" class="stv-radio-button" name="member-role" value="3" id="button8" />
        <label for="button8">SG</label>
        <input type="radio" class="stv-radio-button" name="member-role" value="4" id="button9" />
        <label for="button9">UG</label>
        <input type="radio" class="stv-radio-button" name="member-role" value="5" id="button10" />
        <label for="button10">TG</label>
    </div>
    <input type="text" placeholder="Username" name="memberUsername" value="${memberUsername}">
    <input type="hidden" name="action" value="confirmUpdate">
    <button class="text-button">Confirm</button>
</form>


