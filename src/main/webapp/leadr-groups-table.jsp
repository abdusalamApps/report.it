<%@ page import="java.util.ArrayList" %>
<%@ page import="report.it.models.TimeReport" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: abdusalamyabrak
  Date: 2020-03-05
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HR>
<h2 >Your Groups Time Reports</h2>
<table>
    <tr>
        <th>Project</th>
        <th>Member</th>
        <th>Date Submitted</th>
        <th>Week Number</th>
        <th>Total Minutes</th>
        <th>Sign</th>
    </tr>
    <c:forEach items="${groupReports}" var="groupReport">
        <tr>
            <td>
                    ${groupReport.getProjectName()}
            </td>
            <td>
                    ${groupReport.getUsername()}
            </td>
            <td>
                    ${groupReport.getSubmitted()}
            </td>
            <td>
                    ${groupReport.getWeek()}
            </td>
            <td>
                    ${groupReport.getMinutes_sum()}
            </td>

            <td>
                <form action="TimeReporting" method="post">
                    <input type="hidden" name="groupReportId" value="${groupReport.getId()}"/>
                    <input type="hidden" name="action" value="sign">
                    <button>Sign</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>

<h2 style="color: aquamarine">Your Groups</h2>

<table>
    <tr>
        <th>Project Name</th>
        <th>Project Administrator</th>
        <th></th>
    </tr>
    <c:forEach items="${myGroups}" var="group">
        <tr>
            <td>
                    ${group.getName()}
            </td>
            <td>
                    ${group.getAdministrator()}
            </td>

            <td>
                <form action="TimeReporting" method="post">
                    <input type="hidden" name="myProject" value="${group.getId()}"/>
                    <input type="hidden" name="action" value="editProject">
                    <button>Edit</button>
                </form>
            </td>
        </tr>
    </c:forEach>

</table>


