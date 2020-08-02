<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-03-28
  Time: 오후 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageCondition = (String)session.getAttribute("pageCondition");
    String pageNumber = (String)request.getAttribute("pageNumber");
%>
<html>
<head>
    <title>로그아웃 체크</title>
</head>
<script type="text/javascript">
    window.history.forward(1);
</script>
<center>
    <h2>정말 로그아웃 하시겠습니까?</h2>
    <button type="submit" form="logout">예</button>
    <button type="submit" form="getBoardList">아니오</button>

    <form action="logout" method="get" id="logout"></form>
    <% if (pageCondition.equals("condition")) { %>
    <form action="main" method="get" id="getBoardList">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <% } else { %>
    <form action="<%= pageCondition %>" method="get" id="getBoardList">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <% } %>
</center>
</body>
</html>
