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


                <c:set var = "signed" scope = "session" value = "${timeReport.getSigned()}"/>
                <c:if test = "${!signed}">
                    <form action="TimeReporting" method="post">
                        <input type="hidden" name="timeReportId" value="${timeReport.getId()}"/>
                        <input type="hidden" name="action" value="editTimeReport">
                        <button>Edit</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<br>

<c:set var = "editable" scope = "session" value = "${editable}"/>
<c:if test = "${!editable}">
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
</c:if>

<c:if test = "${editable}">
    <h2 >Edit Your Time Report</h2>
    <form action="TimeReporting" method="post">
        <input type="text" name="week" placeholder="${editReport.getWeek()}"/>
        <input type="text" name="time" placeholder="${editReport.getMinutes_sum()}"/>

    <select placeholder="${editReport.getProjectName()}">
    <c:forEach items="${projects}" var="project">
        <option  value="${project}">${project}</option>
    </c:forEach>
    </select>
        <input type="hidden" name="reportId" value="${editReport.getId()}">
        <input type="hidden" name="action" value="update">
        <button>Uppdate</button>

        <input type="hidden" name="reportId" value="${editReport.getId()}">
        <input type="hidden" name="action" value="delete">
        <button>Delete</button>
    </form>
</c:if>

<br>
<br>