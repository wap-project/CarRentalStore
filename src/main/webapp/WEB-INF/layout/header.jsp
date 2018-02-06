<%@include file="lib_bundle.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<div style="background-image: linear-gradient( to bottom right, #99CC33 0%, #99CC00 100%);">
    <table>
        <tr>
            <td class="col-md-6">
                <c:url value="/img/logo.jpg" var="headerImg"/>
                <img src="${headerImg}" style="width:100px; margin-left:40px"/>
            </td>
            <td class="col-md-6" style="vertical-align: middle; display: table-cell; text-align: right" >
                <h1 style="color: #088007; font-weight: bold">   <fmt:message key="HEADER_TITLE"/> </h1>
            </td>
        </tr>
    </table>

</div>