<%@ page import="java.util.ArrayList" %>
<%@ page import="report.it.models.Project" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2020-03-05
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h3>Registered Projects</h3>
<table>
    <tr>
        <th>Project Name</th>
    </tr>
    <c:forEach items="${projects}" var="project">
        <tr>

            <td>
                    ${project.getName()}

            </td>

            <td>
                <form action="Administration" method="post">
                    <input type="hidden" name="projectname" value="${project.getName()}"/>
                    <input type="hidden" name="action" value="editProject">
                    <button>Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
