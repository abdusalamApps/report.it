<%@ page import="java.util.ArrayList" %>
<%@ page import="report.it.models.User" %><%--
  Created by IntelliJ IDEA.
  User: abdusalamapps
  Date: 2020-02-22
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h3>Registered users</h3>
<table>
    <tr>
        <th>username</th>
        <th>password</th>
    </tr>
    <%
        ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
        for (User user : users) {
    %>
    <tr>
        <td><%= user.getUsername()%>
        </td>
        <td><%= user.getPassword()%>
        </td>
    </tr>
    <%
        }
    %>
</table>


