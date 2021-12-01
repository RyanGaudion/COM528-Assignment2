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
    <H1>Cart</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

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
                <td>
                    <!-- post avoids url encoded parameters -->
                    <form action="./home" method="post">
                        <input type="hidden" name="itemUUID" value="${orderItem.item.uuid}">
                        <input type="hidden" name="itemName" value="${orderItem.item.name}">
                        <input type="hidden" name="action" value="removeItemFromCart">
                        <button type="submit" >Remove Item</button>
                    </form> 
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td>TOTAL</td>
            <td>${shoppingcartTotal}</td>
        </tr>
    </table>

    <c:if test="${sessionUser.userRole !='ANONYMOUS' && shoppingCartItems.size() > 0}">
        <div class="row">
            <form action="./checkout" method="GET"> 
                <div class="col-xs-6 col-md-4">
                    <button class="btn btn-primary" type="submit">
                      Checkout
                    </button>
                </div>
            </form>
        </div>
    </c:if>
    <c:if test="${sessionUser.userRole =='ANONYMOUS'}">
        <div class="row">
            <form action="./checkout" method="GET"> 
                <div class="col-xs-6 col-md-4">
                    <p style="color: red;"><strong>Sign in to proceed to checkout</strong></p>
                </div>
            </form>
        </div>
    </c:if>
    



</main>
<jsp:include page="footer.jsp" />
