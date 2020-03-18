<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: abdo
  Date: 3/18/2020
  Time: 9:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">

    <a class="title">Members</a>
    <div class="cards-container">

        <c:forEach items="${members}" var="member">
            <div class="card">
                <div class="card-main-content">
                    <h4>${member.getName()}</h4>
                    <p>${member.getUsername()}</p>
                    <p>${member.getRole()}</p>
                </div>
                <div class="card-bottom">
                    <form action="">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="username" value="${member.getUsername()}">
                        <button id="edit-button">
                            <i class="material-icons">edit</i>
                        </button>
                    </form>
                    <form action="">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="username" value="${member.getUsername()}">

                        <button class="delete-button">
                            <i class="material-icons">delete</i>
                        </button>
                    </form>

                </div>
            </div>
        </c:forEach>

    </div>

    <a class="title">New Member</a>
    <form class="new-member-form" action="" method="doPost">
        <div class="role-radio-buttons-wrapper">
            <input type="radio" class="stv-radio-button" name="member-role" value="1" id="button1" />
            <label for="button1">Leader</label>
            <input type="radio" class="stv-radio-button" name="member-role" value="2" id="button2" />
            <label for="button2">Member</label>
        </div>
        <input type="text" placeholder="Username">
        <input type="hidden" name="action" value="addNewMember">
        <button class="text-button">Add Member</button>
    </form>
</div>
