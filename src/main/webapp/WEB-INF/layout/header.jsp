<%@include file="lib_bundle.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<div style="background-image: linear-gradient( to bottom right, #F8961C 0%, #FFD007 100%);">
    <table>
        <tr>
            <td class="col-md-3">
                <c:url value="/img/logo.jpg" var="headerImg"/>
                <img src="${headerImg}" style="max-width:320px;"/>
            </td>
            <td class="col-md-9" style="vertical-align: middle; display: table-cell; text-align: right" >
                <h1 style="color: #1C75BC; font-weight: bold">   <fmt:message key="HEADER_TITLE"/> </h1>
            </td>
        </tr>
    </table>

</div>