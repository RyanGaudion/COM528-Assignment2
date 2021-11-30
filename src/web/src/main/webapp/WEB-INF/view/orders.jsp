<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.UserRole"%>
<c:set var = "selectedPage" value = "admin" scope="request"/>
<jsp:include page="header.jsp" />
<!-- start of users.jsp selectedPage=${selectedPage}-->

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <h1>Manage Orders</h1>
        <p>showing ${ordersListSize} orders </p>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Date of Purchase</th>
                    <th scope="col">Total Cost</th>
                    <th scope="col">Number of Items</th>
                    <th scope="col">User</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="order" items="${ordersList}">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.dateOfPurchase}</td>                       
                        <td>${order.amountDue}</td>
                        <td>${order.purchasedItems.size()}</td>                        
                        <td>${order.purchaser.username}</td>
                        
                        <td>
                            <form action="./viewModifyOrder" method="GET">
                                <input type="hidden" name="orderid" value="${order.id}">
                                <button class="btn" type="submit" >View/Modify Order</button>
                            </form> 
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
</main>

<jsp:include page="footer.jsp" />
