<%--
  Created by IntelliJ IDEA.
  User: abdo
  Date: 3/11/2020
  Time: 10:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <script type="text/javascript">

function logOut() {

location.replace("http://localhost:8080/report.it/LogIn");
}

var autoLogTime = 2000;
var timer;

function startTimer() {
    timer = window.setTimeout(autoLogOut, autoLogTime);
}

function resetTimer() {
    window.clearTimeout(timer);
    startTimer();
}

function autoLogOut() {
    logOut();
    alert("you are logged out because of inactivity")
}

function setupTimers() {
    document.addEventListener("mousemove", resetTimer, false);
    document.addEventListener("mousedown", resetTimer, false);
    document.addEventListener("keypress", resetTimer, false);
    document.addEventListener("touchmove", resetTimer, false);
    document.addEventListener("onscroll", resetTimer, false);
    startTimer();
}

setupTimers();
</script>
<div class="navbar-container">
    <div class="topnav">
        <a href="">${navbarTitle}</a>
        <div class="topnav-right">
            <a href="">Home</a>
            <a href="Profile">Profile</a>
<a href="javascript:logOut()">Log out</a>

        </div>
    </div>
</div>

