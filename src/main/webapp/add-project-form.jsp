<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2020-03-05
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
<div class="projHeader">
    <h3>Projects</h3>
    <form action="Administration" method="post">
        <input type="text" name="project-name" placeholder="name"/>
        <input type="hidden" name="action" value="addProject">
        <button>Create project</button>
    </form>
</div>
--%>

<!-- A container for projects title and new project form -->
<div id="title-form-container">
    <a class="title">Projects</a>
    <form action="Administration" method="post" id="new-project-form">
        <input type="text" placeholder="Project Name">
        <button class="text-button">Create</button>
    </form>
</div>
