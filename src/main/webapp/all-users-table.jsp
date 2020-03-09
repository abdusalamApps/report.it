<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: abdusalamapps
  Date: 2020-02-22
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

h3>Users</h3>
<table>
    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>Email</th>
        <th></th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td class="info-col">
                    ${user.getUsername()}
            </td>
            <td class="info-col">
                    ${user.getName()}
            </td>
            <td class="info-col">
                    ${user.getEmail()}
            </td>
            <td class="button-col">
                <form action="Administration" method="post">
                    <input type="hidden" name="username" value="${user.getUsername()}"/>
                    <input type="hidden" name="action" value="delete">
                    <button id="delete-button">
                        <i class="material-icons">delete</i>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>



