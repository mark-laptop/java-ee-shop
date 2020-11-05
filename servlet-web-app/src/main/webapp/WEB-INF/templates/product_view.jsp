<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

<%@ include file="head.jsp" %>

<body>

<%@ include file="header.jsp" %>
<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <input type="hidden" id="id" name="id" value="${product.id}">

            <div class="form-group">
                <label>Name</label>
                <input type="text" disabled class="form-control" id="name" name="name"
                       value="${product.name}" placeholder="Name">
            </div>

            <div class="form-group">
                <label>Description</label>
                <input type="text" disabled class="form-control" id="description" name="description"
                       value="${product.description}" placeholder="Description">
            </div>

            <div class="form-group">
                <label>Price</label>
                <input type="number" disabled step="any" class="form-control" id="price" name="price"
                       value="${product.price}">
            </div>
        </div>
    </div>
</div>

</body>

<%@ include file="footer_scripts.jsp" %>

</html>
