<%@ include file="../layout/lib_bundle.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1 align="center"><fmt:message key="${message}"/></h1>
<%--My order data start--%>
<table class="table table-striped table-bordered table-hover table-condensed">
  <tbody>
  <tr>
    <th><fmt:message key="ORDER_ID" /></th>
    <th><fmt:message key="CAR_ID" /></th>
    <th><fmt:message key="USER_ID" /></th>
    <th><fmt:message key="CAR_MODEL" /></th>
    <th><fmt:message key="CAR_BRAND" /></th>
    <th><fmt:message key="BEGIN_DATE" /></th>
    <th><fmt:message key="END_DATE" /></th>
    <th><fmt:message key="TOTAL_RENT" /></th>
    <th><fmt:message key="REASON" /></th>
    <th><fmt:message key="PENALTY" /></th>
    <th><fmt:message key="EDIT" /></th>
  </tr>
  <c:forEach items="${orders}" var="order">
    <tr>
      <td><fmt:formatNumber value="${order.orderId}" /> </td>
      <td><fmt:formatNumber value="${order.carId}"/> </td>
      <%--<td><a href="/user?id=${order.userId}"><fmt:formatNumber value="${order.userId}"/></a></td>--%>
      <td><button class="btn btn-primary form-control" value="${order.userId}" onclick="getUserInfo(this.value)"/>${order.userId}</td>
      <td>${order.model}</td>
      <td>${order.brand}</td>
      <td><fmt:formatDate value="${order.dateStart}" type="date"/></td>
      <td><fmt:formatDate value="${order.dateEnd}" type="date"/></td>
      <td><fmt:formatNumber value="${order.rentTotal}"/></td>
      <td>${order.reason}</td>
      <td><fmt:formatNumber value="${order.penalty}"/></td>
      <td><a href="/order?id=${order.orderId}"><button type="submit" class="btn btn-primary form-control" name="command" value="order"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> <fmt:message key="EDIT"/></button></a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<%--My order data end--%>

<table id="userInfoTable" class="table  table-bordered table-hover" style="display: none">
  <tbody>
  <tr>
    <th><fmt:message key="USER_ID" /></th>
    <th><fmt:message key="FIRST_NAME" /></th>
    <th><fmt:message key="LAST_NAME" /></th>
    <th><fmt:message key="PASSPORT" /></th>
    <th><fmt:message key="EMAIL" /></th>
    <th>Close</th>
  </tr>
</table>

