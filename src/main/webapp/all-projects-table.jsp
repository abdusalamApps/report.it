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


<div class="projHeader">
      <h3>Projects</h3>
          <form action="Administration" method="post">
                  <input type="text" name="projectname" placeholder="Project name"/>


                  <input type="hidden" name="action" value="addProject">
                  <button>Create project</button>
          </form>
</div>

<div class="Project">

    <h4>Registered Projects</h4>
    <table>
        <tr>
            <th>Project Name</th>
            <th></th>
        </tr>
        <c:forEach items="${projects}" var="project">
            <tr>

                <td class="info-col">
                        ${project.getName()}

                </td>

                 <td class="button-col">
                    <form action="Administration" method="post">
                        <input type="hidden" name="projectname" value="$project.getName()}"/>
                        <input type="hidden" name="action" value="edit">
                        <button id="edit-button">
                            <i class="material-icons">edit</i>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>