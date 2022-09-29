<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Online Store</title>
    <style><%@include file="/resources/style.css"%></style>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h2>Online Store - Cart</h2>
    </div>
</div>
<br/>
<div id="container">
    <div id="content">
        <table>
            <h4>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </h4>
            <c:forEach var="product" items="${productsByCart}">
                <c:url var="deleteLink" value="/deleteProduct">
                    <c:param name="productId" value="${product.id}"/>
                </c:url>
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.desc}</td>
                    <td><a href="${deleteLink}">Delete</a>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div style="clear: both"></div>
<p>
    <a href="/catalog">Back to Catalog</a>
</p>
</body>
</html>
