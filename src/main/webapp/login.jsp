<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Online Store</title>
</head>
<body>
<h1><%= "Online Store login form" %></h1>
<br>
<form name="loginForm" method="post" action="front">
    Username: <input type="text" name="username"/><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" value="Login">
</form>
</body>
</html>
