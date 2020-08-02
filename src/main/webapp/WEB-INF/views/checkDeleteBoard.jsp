<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-03-31
  Time: 오후 9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String strPageNumber = (String)request.getAttribute("pageNumber");
    int pageNumber = Integer.parseInt(strPageNumber);
%>
<html>
<head>
    <title>게시물 삭제 체크</title>
</head>
<body>
<center>
    <h2>정말 삭제하시겠습니까?</h2>
    <button type="submit" form="deleteBoard">예</button>
    <button type="submit" form="getBoard">아니오</button>

    <form action="<%= session.getAttribute("currentBoardNumber") %>" method="post" id="deleteBoard">
        <input type="hidden" name="_method" value="delete">
    </form>
    <form action="<%= session.getAttribute("currentBoardNumber") %>" method="get" id="getBoard">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
</center>
</body>
</html>
