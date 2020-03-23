<%@ page import="java.util.ArrayList" %>
<%@ page import="report.it.models.Project" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
  Created by IntelliJ IDEA.
  User: Aml Abbas
  Date: 2020-03-18
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>


    <a class="title">Your Profile</a>
    <p>${userName}</p>
    <p>${userUsername}</p>
    <p>${userEmail}</p>


<a class="title">Your Groups</a>


    <c:forEach items="${projects}" var="project">

                <h4><b>${project.getName()}</b></h4>
    </c:forEach>


<a class="title">Edit Profile</a>
<form class="edit-profile-form" action="Profile" method="POST">
    <input type="password" name="password" placeholder="Your current password">
    <input type="password" name="newPassword1" placeholder="Your new password">
    <input type="password" name="newPassword2" placeholder="Your new password">
    <input type="hidden" name="username" value="${user.getUsername()}"/>
    <input type="hidden" name="action" value="changePassword">
    <button  class="text-button">Change Password</button>
    <p>${newPasswordMessage}</p>
</form>

