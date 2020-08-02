<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-04-15
  Time: 오후 3:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String strPageNumber = (String)request.getAttribute("pageNumber");
    int pageNumber = Integer.parseInt(strPageNumber);
    String pageCondition = (String)session.getAttribute("pageCondition");
%>
<html>
<head>
    <title>회원 탈퇴</title>
</head>
<script type="text/javascript">
    window.history.forward(1);

    var information = '${information}';

    if (information == 'passwordIncorrect')
        alert('비밀번호가 틀렸습니다.');
</script>
<body>
<% if ((pageCondition.equals("main")) || (pageCondition.equals("sort-recent")) || (pageCondition.equals("sort-hits")) ||
        (pageCondition.equals("sort-comments")) || (pageCondition.equals("sort-recommendations")) ||
        (pageCondition.equals("sort-decommendations")) || (pageCondition.equals("ownership")) ||
        (pageCondition.equals("board-favorite"))) { %>
<form action="../board-list/<%= pageCondition %>" method="get">
    <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    <input type="submit" value="게시판으로 돌아가기">
</form>
<% } else { %>
<form action="../board-list/main" method="get">
    <input type="hidden" name="pageNumber" value="1">
    <input type="submit" value="게시판으로 돌아가기">
</form>
<% } %>

<center>
    <h3>정말 탈퇴하려면 비밀번호를 입력하십시오</h3>
    <form action="../user" method="post">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <input type="hidden" name="_method" value="delete">
        <tr>
            <td><input type="password" name="password"></td>
            <td><input type="submit" value="탈퇴"></td>
        </tr>
    </form>
</center>

</body>
</html>
