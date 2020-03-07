<%@ page import="java.util.ArrayList" %>
<%@ page import="report.it.models.TimeReport" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: abdusalamyabrak
  Date: 2020-03-05
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h2 style="color: aquamarine">Your Time Reports</h2>

<table>
    <tr>
        <th>Date Submitted</th>
        <th>Week Number</th>
        <th>Total Minutes</th>
        <th>Project</th>
        <th>Edit</th>
    </tr>

    <c:forEach items="${timeReports}" var="timeReport">
        <tr>
            <td>
                    ${timeReport.getSubmitted()}
            </td>
            <td>
                    ${timeReport.getWeek()}
            </td>
            <td>
                    ${timeReport.getMinutes_sum()}
            </td>
            <td>
                    ${timeReport.getProjectName()}
            </td>

            <td>
                <form action="TimeReporting" method="post">
                    <input type="hidden" name="timeReportId" value="${timeReport.getId()}"/>
                    <input type="hidden" name="action" value="editTimeReport">
                    <button>Edit</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>

<h2 style="color: aquamarine">New Report</h2>


<form action="TimeReporting" method="post">
    <input type="text" name="week" placeholder="Week Number"/>
    <input type="text" name="time" placeholder="Total Minutes"/>

    <select name="projectName">
        <c:forEach items="${projects}" var="project">
            <option  value="${project}">${project}</option>
        </c:forEach>
    </select>
    <input type="hidden" name="action" value="submit">
    <button>Submit Report</button>
</form>


<br>
<br>