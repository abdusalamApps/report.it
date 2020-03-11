<%@ page import="report.it.models.Administrator" %>
<%--
  Created by IntelliJ IDEA.
  User: abdusalamyabrak
  Date: 2020-02-24
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="global.css">

    <title>Modify Project</title>
</head>
<body>
<h2>Members</h2>

<table>
    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
    </tr>

    <c:forEach items="${ProjectMember}" var="Member">
        <tr>
            <td>
                    ${Member.getUsername()}
            </td>
            <td>
                    ${Member.getProjectName()}
            </td>
            <td>
                    ${Member.getEmail()}
            </td>
            <td>
                    ${Member.getRole()}
            </td>

            <td>
            <td class="button-col">
                <form action="GroupModifier" method="post">
                    <input type="hidden" name="Member" value="${Member.getUsername()}"/>
                    <input type="hidden" name="action" value="--">
                    <button id="delete-button">
                        <i class="material-icons">--</i>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>


<h2>Edit Member</h2>


<form action="editMember" method="post">
    <input type="text" name="Member" placeholder="Member Name"/>
    <input type="text" name="role" placeholder="Role"/>
    <input type="hidden" name="action" value="update">
    <button>Add</button>
    <input type="hidden" name="action" value="delete">
    <button>Delete</button>

</form>

<h2 style="color: aquamarine">New Member</h2>


<form action="addMember" method="post">
    <input type="text" name="Member" placeholder="Member Name"/>
    <input type="text" name="role" placeholder="Role"/>
    <input type="hidden" name="action" value="addMember">
    <button>Add</button>

</form>
<br>

</body>
</html>

