<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : rgaudion
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.UserRole"%>
<c:set var = "selectedPage" value = "users" scope="request"/>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <H1>Modify Item: ${modifyItem.name} </H1>
        <!-- print error message if there is one -->
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>

        <form action="./viewModifyItem" method="POST" enctype="multipart/form-data">
            <table class="table">
                <thead>
                </thead>
                <tbody>
                    <tr>
                        <td>Item ID</td>
                        <td>${modifyItem.id}</td>
                    </tr>
                    <tr>
                        <td>Name</td>
                        <td><input type="text" name="name" value="${modifyItem.name}" /></td>
                    </tr>
                    <tr>
                        <td>Category</td>
                        <td><input type="text" name="category" value="${modifyItem.category}" /></td>
                    </tr>
                    <tr>
                        <td>Price</td>
                        <td><input type="text" name="price" value="${modifyItem.price}" /></td>
                    </tr>
                    <tr>
                        <td>Quantity</td>
                        <td><input type="text" name="quantity" value="${modifyItem.quantity}" /></td>
                    </tr>
                    <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
                        <tr>
                            <td>File name</td>
                            <td>${modifyItem.filename}"</td>
                        </tr>
                        <tr>
                           <td>Pick Image File</td>
                           <td><input type="file" name="file" size="50" /></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>Item Deactivated</td>
                        <td>
                            <select class="form-control" name="deactivated" >
                                <option value="true" <c:if test="${modifyItem.deactivated}"> selected  </c:if> >true</option>
                                <option value="false" <c:if test="${!modifyItem.deactivated}"> selected  </c:if> >false</option>
                            </select>
                        </td>
                    </tr>

                </tbody>

            </table>

            <c:if test="${sessionUser.userRole =='ADMINISTRATOR' && modifyItem.id != null}">
                <input type="hidden" name="id" value="${modifyItem.id}"/>
                <button class="btn" type="submit" >Update Item: ${modifyItem.name}</button>
            </c:if>
            <c:if test="${sessionUser.userRole =='ADMINISTRATOR' && modifyItem.id == null}">
                <button class="btn" type="submit" >Add Item</button>
            </c:if>

        </form>
        <c:if test="${sessionUser.userRole =='ADMINISTRATOR' && modifyItem.id != null}">
            <form action="./viewModifyItem" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${modifyItem.id}"/>                
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="name" value="${modifyItem.name}"/>
                <button class="btn btn-warning" type="submit" >Delete Item: ${modifyItem.name}</button>     
            </form>
        </c:if>             
                            
                            
        <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
            <BR>
            <form action="./catalog">
                <button class="btn" type="submit" >Return To Catalog</button>
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
