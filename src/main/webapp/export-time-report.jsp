<%@ page import="java.util.ArrayList" %>
<%@ page import="report.it.models.TimeReport" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: li
  Date: 2020-03-25
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exported Time Reports</title>
</head>
<body>

<table>
    <c:forEach items="${groupReports}" var="groupReport">
        <tr>
            <td>
                    ${groupReport.getUsername()}
            </td>
            <td>
                    ${groupReport.getProjectName()}
            </td>
            <td>
                    ${groupReport.getWeek()}
            </td>

            <td>
                    ${groupReport.getMinutes_sum()}
            </td>

        </tr>
    </c:forEach>
</table>