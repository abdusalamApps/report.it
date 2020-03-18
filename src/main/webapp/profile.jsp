<%--
  Created by IntelliJ IDEA.
  User: Aml
  Date: 2020-03-18
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="container">

<a class="title">Edit Profile</a>
<form class="edit-profile-form" action="Profile" method="POST">
    <input type="text" name="password" placeholder="Your current password">
    <input type="text" name="newPassword1" placeholder="Your new password">
    <input type="text" name="newPassword2" placeholder="Your new password">
    <input type="hidden" name="username" value="${user.getUsername()}"/>
    <input type="hidden" name="action" value="changePassword">
    <button  class="text-button">Change Password</button>
    <p>${newPasswordMessage}</p>
</form>