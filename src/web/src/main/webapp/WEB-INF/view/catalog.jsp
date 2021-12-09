<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : rgaudion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.UserRole"%>
<c:set var = "selectedPage" value = "catalog" scope="request"/>
<jsp:include page="header.jsp" />
<!-- start of catalog.jsp selectedPage=${selectedPage}-->

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <h1>Manage Catalog</h1>
        <p>showing ${catalogListSize} items </p>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>    
                    <th scope="col">Category</th>
                    <th scope="col">Deactivated</th>                    

                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${catalogList}">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.price}</td>
                        <td>${item.quantity}</td>                        
                        <td>${item.category}</td>
                        <td>${item.deactivated}</td>
                        <td>
                            <form action="./viewModifyItem" method="GET">
                                <input type="hidden" name="itemID" value="${item.id}">
                                <button class="btn" type="submit" >Modify Item</button>
                            </form> 
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
        <form action="./createItem" method="GET">
            <button class="btn" type="submit" >Add Item</button>
        </form> 
    </div>
</main>

<jsp:include page="footer.jsp" />
