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


<%--
<div class="Project">

    <h4>Registered Projects</h4>
    <table>
        <tr>
            <th>Project Name</th>
            <th>Administrator</th>
            <th></th>
        </tr>
        <c:forEach items="${projects}" var="project">
            <tr>

                <td class="info-col">
                        ${project.getName()}

                </td>
                <td class="info-col">
                        ${user.getName()}
                </td>

                <td class="button-col">
                    <form action="Administration" method="post">
                        <input type="hidden" name="edit-project-name" value="${project.getName()}"/>
                        <input type="hidden" name="edit-project-id" value="${project.getId()}"/>
                        <input type="hidden" name="action" value="editProject">
                        <button id="edit-button">
                            <i class="material-icons">edit</i>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
--%>

<div class="cards-container">
    <c:forEach items="${projects}" var="project">
    <div class="card">
        <div class="card-main-content">
            <h4><b>${project.getName()}</b></h4>
<%--            <p>${project.getMembers()}</p>--%>
        </div>
        <div class="card-bottom">
            <form action="Administration" method="post">
                <input type="hidden" name="edit-project-name" value="${project.getName()}"/>
                <input type="hidden" name="edit-project-id" value="${project.getId()}"/>
                <input type="hidden" name="action" value="editProject">
                <button id="delete-button">
                    <i class="material-icons">edit</i>
                </button>
            </form>
        </div>
    </div>
    </c:forEach>
</div>