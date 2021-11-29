<%-- 
    Document   : content
    Created on : Nov 11 2021, 11:19:47 AM
    Author     : rgaudion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
// request set in controller
//request.setAttribute("selectedPage", "home");
%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Properties</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <form action="./properties.jsp" method="POST">

        <p>URL Property <input type="text" name="url" value="${url}" required></p>
        <p>Username Property <input type="text" name="username" value="${username}" required></p>
        <p>Password Property <input type="text" name="password" value="${password}" required></p>
        <p>Shop Keeper Card Property <input type="text" name="shopKeeperCard" value="${shopkeepercard}" required></p>
        <input type="hidden" name="action" value="updateProperties">

        <button class="btn" type="submit" >Update Properties</button>
    </form> 


   


</main>
<jsp:include page="footer.jsp" />
