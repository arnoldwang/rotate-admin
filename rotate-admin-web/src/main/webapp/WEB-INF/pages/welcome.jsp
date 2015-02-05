<%--
  Created by IntelliJ IDEA.
  User: dev_wzhang
  Date: 15-1-19
  Time: 下午3:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<html>
<head>
    <title>Welcome for test</title>
</head>
<body>
this is welcome page!

<%--<form action="/territory/delete" method="post">--%>
    <%--战区id <input name="territoryId" >--%>
    <%--<input type="submit" value="删除战区" title="删除战区" name="submit"/>--%>
<%--</form>--%>

<form action="/territory/queryTerritoryBreadCrumbs" method="post">
    战区id <input name="territoryId" >
    <input type="submit" value="面包屑" na me="submit"/>
</form>

</body>
</html>
