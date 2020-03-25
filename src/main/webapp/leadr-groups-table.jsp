<%@ page import="java.util.ArrayList" %>
<%@ page import="report.it.models.TimeReport" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Li Zhu
  Date: 2020-03-05
  Time: 10:43
  @author Li Zhu, Nils Olen & Fatima Doussi
  @version: 0.3
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HR>
<div class="container">
<h2 >Your Groups Time Reports</h2>


    <button ><a href="export-time-reports" target="_blank">Export Time Reports</a></button>



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
                    ${groupReport.getUserFullName()}
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
                <c:set var = "isSigned" scope = "session" value = "${groupReport.getSigned()}"/>
                <c:choose>
                    <c:when test="${!isSigned}">

                <form action="TimeReporting" method="post">
                    <input type="hidden" name="groupReportId" value="${groupReport.getId()}"/>
                    <input type="hidden" name="action" value="sign">
                    <button>Sign</button>

                </form>
                    </c:when>
                    <c:otherwise>
                        signed
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
</div>
    <div class="container">
<h2 >Your Groups</h2>

<table>
    <tr>
        <th>Project Name</th>
        <th></th>
    </tr>
    <c:forEach items="${myGroups}" var="group">
        <tr>
            <td>
                    ${group.getName()}
            </td>

            <td>
                <form action="TimeReporting" method="post">
                    <input type="hidden" name="myProject" value="${group.getId()}"/>
                    <input type="hidden" name="action" value="editProject">
                    <button><i class="material-icons">edit</i></button>
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

    </div>
