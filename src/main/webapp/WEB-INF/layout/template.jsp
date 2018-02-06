
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="lib_bundle.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <!-- Latest compiled and minified CSS -->
    <c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" var="bootsrapMin" />
    <link rel="stylesheet" href="${bootsrapMin}"/>
    <!-- Optional theme -->
    <c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css" var="bootsrapTheme" />
    <link rel="stylesheet" href="${bootsrapTheme}"/>
    <c:url value="https://code.jquery.com/jquery-3.2.1.slim.min.js" var="jquery" />
    <script src="${jquery}"> </script>
    <c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js" var="bootstrapJS" />
    <script src="${bootstrapJS}"> </script>
    <title><tiles:getAsString name="title"/></title>
    <c:url value="/img/favicon.ico.png" var="favicon"/>
    <link rel="shortcut icon" href="${favicon}" type="image/jpg">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='http://fonts.googleapis.com/css?family=Ubuntu+Condensed&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
    <style type="text/css">
        body{
            font-family: 'Ubuntu Condensed', sans-serif;
            font-size: large;
        }
    </style>
</head>
<body >
<div class="container">
<tiles:insertAttribute name="header"/>
<table class="table" >
    <tr>
        <td class="col-md-2" style="vertical-align:top"><tiles:insertAttribute name="menu" /></td>
        <td class="col-md-9"><center><tiles:insertAttribute name="body"/></center></td>
    </tr>
</table>
<tiles:insertAttribute name="footer"/>
</div>
</body>
</html>
