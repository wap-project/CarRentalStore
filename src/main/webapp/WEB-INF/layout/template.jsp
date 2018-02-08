
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="lib_bundle.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <!-- Latest compiled and minified CSS -->
    <c:url value="/css/bootstrap.min.css" var="bootsrapMin" />
    <link rel="stylesheet" href="${bootsrapMin}"/>
    <!-- Optional theme -->
    <c:url value="/css/bootstrap-theme.css" var="bootsrapTheme" />
    <link rel="stylesheet" href="${bootsrapTheme}"/>
    <c:url value="/css/font-awesome.css" var="fontAwesome" />
    <link rel="stylesheet" href="${fontAwesome}"/>
    <c:url value="/js/jquery-3.2.1.js" var="jquery" />
    <script src="${jquery}"> </script>
    <c:url value="/js/bootstrap.min.js" var="bootstrapJS" />
    <script src="${bootstrapJS}"> </script>
    <title><tiles:getAsString name="title"/></title>
    <c:url value="/img/favicon.ico.png" var="favicon"/>
    <link rel="shortcut icon" href="${favicon}" type="image/png">
    <script src="/js/administrator.js" ></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style type="text/css">
        body{
            font-family: "Microsoft YaHei", sans-serif;
            /*font-size: large;*/
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
