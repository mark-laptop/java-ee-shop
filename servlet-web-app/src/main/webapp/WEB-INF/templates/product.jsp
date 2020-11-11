<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<jsp:include page="head.jsp"/>

<body>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="${requestScope.action}" var="productPostUrl"/>
            <form action="${productPostUrl}" method="post">
                <input type="hidden" id="id" name="id" value="${product.id}">

                <div class="form-group">
                    <label>Name</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="${product.name}" placeholder="Name">
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <input type="text" class="form-control" id="description" name="description"
                           value="${product.description}" placeholder="Description">
                </div>

                <div class="form-group">
                    <label>Price</label>
                    <input type="number" step="any" class="form-control" id="price" name="price"
                           value="${product.price}">
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>

<%@ include file="footer_scripts.jsp" %>

</html>