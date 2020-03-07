<%@ page import="report.it.models.Administrator" %>
<%--
  Created by IntelliJ IDEA.
  User: abdusalamyabrak
  Date: 2020-02-24
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2 style="color: aquamarine">Members</h2>

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
                     <form action="GroupModifier" method="post">
                         --
                         --
                        <button>Role</button>
                     </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>


<h2 style="color: aquamarine">New Member</h2>


<form action="GroupModifier" method="post">
    <input type="text" name="member" placeholder="Member Name"/>
    <input type="text" name="role" placeholder="Role"/>


</form>
<br>
