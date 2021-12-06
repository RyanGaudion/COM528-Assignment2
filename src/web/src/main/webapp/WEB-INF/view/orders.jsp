<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : rgaudion, cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.UserRole"%>
<jsp:include page="header.jsp" />
<!-- start of users.jsp selectedPage=${selectedPage}-->

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <h1>Manage Orders</h1>
        <p>showing ${ordersListSize} orders </p>
            
            <c:if test="${selectedPage =='adminOrders'}">
            <!--Search-->
            <div class="row">
                <form action="./orders" method="GET"> 
                    <div class="col-xs-6 col-md-4">
                        <button class="btn btn-primary" type="submit">
                          Clear Search
                        </button>
                    </div>
                </form>
                <form action="./orders" method="GET"> 
                    <div class="col-xs-6 col-md-4">
                      <div class="input-group">
                          <input type="text" class="form-control" placeholder="Search by username" name="searchQuery" id="searchQuery"/>            
                          <input type="hidden" name="action" value="search">
                        <div class="input-group-btn">
                          <button class="btn btn-primary" type="submit">
                            <svg xmlns="http://www.w3.org/2000/svg" style="height: 15px; width: 15px;" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                            </svg>
                          </button>
                        </div>
                      </div>
                    </div>
                </form>
            </div>
            </c:if>
        
        
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Date of Purchase</th>
                    <th scope="col">Total Cost</th>
                    <th scope="col">Number of Items</th>
                    <th scope="col">User</th>                    
                    <th scope="col">Order Status</th>

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
                        <td>${order.invoiceStatus}</td>

                        
                        <td>
                            <form action="./viewModifyOrder" method="GET">
                                <input type="hidden" name="orderid" value="${order.id}">
                                <button class="btn" type="submit" >View
                                    <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
                                        /Modify 
                                    </c:if>
                                    Order</button>
                            </form> 
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
        
    </div>
</main>

<jsp:include page="footer.jsp" />
