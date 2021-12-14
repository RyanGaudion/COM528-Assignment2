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
        <form action="./checkout" method="POST"> 
            <p>Card Number: <input type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" name="cardnumber" required value="${user.savedCard.cardnumber}"/>
            <p>Card Holder Name: <input type="text" name="cardname" required value="${user.firstName}"></p>            
            <p>Card End Date: (in format MM/yy) <input type="text" name="cardenddate" required value="${user.savedCard.endDate}"></p>            
            <p>Card Issue Number: <input type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" name="cardissuenumber" value="${user.savedCard.issueNumber}"></p>
            <p>Card CVV:  <input type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" name="cardcvv" required></p>
            <c:if test="${sessionUser.userRole !='ANONYMOUS' && !message.toLowerCase().startsWith('success') }">
                <div class="row">
                        <div class="col-xs-6 col-md-4">
                            <button class="btn btn-primary" type="submit">
                              Pay
                            </button>
                        </div>
                </div>
            </c:if>
        </form>
        <c:if test="${sessionUser.userRole =='ANONYMOUS'}">
            <div class="row">
                <form action="./checkout" method="GET"> 
                    <div class="col-xs-6 col-md-4">
                        <p style="color: red;"><strong>Sign in to pay</strong></p>
                    </div>
                </form>
            </div>
        </c:if>
    </div>
    
    <div class="col-xs-6 col-md-6">
            <table class="table">
            <tr>
                <th>Item Name</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>

            <c:forEach var="orderItem" items="${shoppingCartItems}">

                <tr>
                    <td>${orderItem.item.name}</td>
                    <td>${orderItem.item.price}</td>
                    <td>${orderItem.quantity}</td>
                </tr>
            </c:forEach>
            <tr>
                <td>TOTAL</td>
                <td>${shoppingcartTotal}</td>
            </tr>
        </table>
    </div>
    
    
    



    



</main>
<jsp:include page="footer.jsp" />
