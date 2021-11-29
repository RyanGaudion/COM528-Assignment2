<%-- 
    Document   : content
    Created on : Nov 11, 2021, 14:24
    Author     : rgaudion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    
    <H1>Checkout</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    
    <div class="col-xs-6 col-md-6">
        
    </div>
    
    <div class="col-xs-6 col-md-6">
            <table class="table">
            <tr>
                <th>Item Name</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>

            <c:forEach var="item" items="${shoppingCartItems}">

                <tr>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                    <td>${item.quantity}</td>
                </tr>
            </c:forEach>
            <tr>
                <td>TOTAL</td>
                <td>${shoppingcartTotal}</td>
            </tr>
        </table>
    </div>
    
    
    


    <c:if test="${sessionUser.userRole !='ANONYMOUS'}">
        <div class="row">
            <form action="./checkout" method="POST"> 
                <div class="col-xs-6 col-md-4">
                    <button class="btn btn-primary" type="submit">
                      Pay
                    </button>
                </div>
            </form>
        </div>
    </c:if>
    <c:if test="${sessionUser.userRole =='ANONYMOUS'}">
        <div class="row">
            <form action="./checkout" method="GET"> 
                <div class="col-xs-6 col-md-4">
                    <p style="color: red;"><strong>Sign in to pay</strong></p>
                </div>
            </form>
        </div>
    </c:if>
    



</main>
<jsp:include page="footer.jsp" />
