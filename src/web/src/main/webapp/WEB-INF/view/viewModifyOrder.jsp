<%-- 
    Document   : content
    Created on : Nov 11, 2021, 14:24
    Author     : rgaudion
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.Invoice"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.InvoiceStatus"%>
<c:set var = "selectedPage" value = "users" scope="request"/>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <H1>Modify Order ${modifyOrder.id} </H1>
        <!-- print error message if there is one -->
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>
        
        <form action="./viewModifyOrder" method="POST">
        <table class="table">
            <thead>
            </thead>
            <tbody>
                <tr>
                    <td>Order ID: </td>
                    <td>${modifyOrder.id}</td>
                </tr>
                <tr>
                    <td>Date of Purchase: </td>
                    <td>${modifyOrder.dateOfPurchase}</td>
                </tr>
                <tr>
                    <td>Total Cost: </td>
                    <td>${modifyOrder.amountDue}</td>
                </tr>
                <tr>
                    <td>Quantity of Items: </td>
                    <td>${modifyOrder.purchasedItems.size()}</td>
                </tr>
                <tr>
                    <td>Username</td>
                    <td>${modifyOrder.purchaser.username}</td>
                </tr>
                
                <tr>
                    <td>Order Status</td>
                    <td>
                         <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
                            <select class="form-control" name="orderStatus" >
                                <c:forEach var="value" items="${InvoiceStatus.values()}">
                                    <option value="${value}" <c:if test="${modifyOrder.invoiceStatus == value}"> selected  </c:if>>${value}</option>
                                </c:forEach>
                            </select>  
                        </c:if>
                        <c:if test="${sessionUser.userRole !='ADMINISTRATOR'}">
                            ${modifyOrder.invoiceStatus}
                        </c:if>
  
                    </td>
                </tr>
                
                <tr>
                    <td>Is Refunded</td>
                    <td>${modifyOrder.refunded}</td>
                </tr>
                
            </tbody>
        </table>
             
        <h4>Items from Order: </h4>
        <!-- Items -->
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Item Name</th>
                    <th scope="col">Item Cost</th>
                    <th scope="col">Quantity Brought</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="orderItem" items="${modifyOrder.purchasedItems}">
                    <tr>
                        <td>${orderItem.item.name}</td>
                        <td>${orderItem.item.price}</td>                       
                        <td>${orderItem.quantity}</td>                    
                    </tr>
                </c:forEach>

            </tbody>
        </table>


        <c:if test="${sessionUser.userRole =='ADMINISTRATOR' && modifyOrder.id != null}">
            <input type="hidden" name="id" value="${modifyOrder.id}"/>            
            <input type="hidden" name="action" value="update"/>
            <button class="btn" type="submit" >Update Order</button>
        </c:if>
        </form>
                
        <c:if test="${sessionUser.userRole =='ADMINISTRATOR' && modifyOrder.id != null && modifyOrder.refunded == false}">
            <BR>
            <form action="./viewModifyOrder" method="POST">
                <input type="hidden" name="id" value="${modifyOrder.id}"/>            
                <input type="hidden" name="action" value="refund"/>
                <button class="btn" type="submit" >Refund Order</button>
            </form>
        </c:if>

        <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
            <BR>
            <form action="./orders">
                <button class="btn" type="submit" >Return To Orders</button>
            </form> 
        </c:if> 
            
        <c:if test="${sessionUser.userRole !='ADMINISTRATOR'}">
            <BR>
            <form action="./home">
                <button class="btn" type="submit" >Return To Home</button>
            </form> 
        </c:if> 

        </div>

    </main>

<jsp:include page="footer.jsp" />
