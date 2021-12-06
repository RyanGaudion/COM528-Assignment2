<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : rgaudion, cgallen
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
    <H1>Home</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    


    <!--Search-->
    <div class="row">
        <form action="./home" method="GET"> 
            <div class="col-xs-6 col-md-4">
                <button class="btn btn-primary" type="submit">
                  Clear Search
                </button>
            </div>
        </form>
        <form action="./home" method="GET"> 
            <div class="col-xs-6 col-md-4">
              <div class="input-group">
                  <input type="text" class="form-control" placeholder="Search" name="searchQuery" id="searchQuery"/>            
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

    <H1>Available Items</H1>
    <table class="table">
        <c:forEach var="item" items="${availableItems}">
            <div class ="col-md-4" style="min-height: 550px; max-height: 550px">
                <div class="panel panel-default">
                    <div class="panel-heading"><h4>${item.name}</h4></div>
                    <div class="panel-body">
                        <c:if test="${item.filename != null && item.filename.length() > 0}">
                            <img style="width:auto; max-height:250px; margin-left: auto; margin-right: auto;" class="img-responsive img-rounded"  src="images/${item.filename}"/>
                        </c:if>
                        <c:if test="${item.filename  == null || item.filename.length() == 0}">
                            <img style="width:auto; max-height:250px; margin-left: auto; margin-right: auto;" class="img-responsive img-rounded"  src="https://thumbs.dreamstime.com/b/transparent-designer-must-have-fake-background-39672616.jpg"/>
                        </c:if>
                    </div>
                    <div class="panel-footer">
                        <p>Price: ${item.price}</p>

                        <c:if test="${item.quantity > 0}">
                            <p>Quantity ${item.quantity}</p>
                            <form action="./home" method="get">
                                <input type="hidden" name="itemName" value="${item.name}">
                                <input type="hidden" name="action" value="addItemToCart">
                                <button type="submit" >Add Item</button>
                            </form>
                        </c:if>
                        <c:if test="${item.quantity <= 0}">
                            <p style="color:red;">Item out of Stock</p>
                        </c:if>
                            <form action="./viewModifyItem" method="get">
                                <input type="hidden" name="itemID" value="${item.id}">
                                <button type="submit" >View Item</button>
                            </form>
                    </form> 
                    </div>
                </div>
            </div>
        </c:forEach>
    </table>


</main>
<jsp:include page="footer.jsp" />
