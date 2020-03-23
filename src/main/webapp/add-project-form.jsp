<%--
  Created by IntelliJ IDEA.
  User: Aml Abbas
  Date: 2020-03-05
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!-- A container for projects title and new project form -->
<div id="title-form-container">
    <a class="title">Projects</a>
    <form action="Administration" method="post" id="new-project-form">
        <input type="text" placeholder="Project Name" name="project-name">
        <input type="hidden" name="action" value="addProject">
        <button class="text-button">Create</button>
    </form>
</div>
