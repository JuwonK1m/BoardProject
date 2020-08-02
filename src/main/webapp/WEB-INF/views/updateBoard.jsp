<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-03-29
  Time: 오후 10:04
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
    <title>글 수정</title>
</head>
<body>
<center>
    <h1>글 수정</h1>
    <br>
    <form action="<%= session.getAttribute("currentBoardNumber") %>" method="post">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <input type="hidden" name="_method" value="put"/>
        <table border="1" cellpadding="0" cellspacing="0">
            <tr>
                <td bgcolor="burlywood" width="70">제목</td>
                <td align="left"><input type="text" name="title" value="<%= session.getAttribute("currentBoardTitle")%>"/></td>
            </tr>
            <tr>
                <td bgcolor="burlywood" width="70">내용</td>
                <td align="left"><textarea name="content" cols="40" rows="10"><%= session.getAttribute("currentBoardContent")%></textarea></td>
            </tr>
            <tr>
                 <td colspan="2" align="center">
                     <input type="submit" value="수정 완료"/>
                     <button type="submit" form="getBoard">수정 취소</button>
                 </td>
            </tr>
        </table>
    </form>
    <br>
    <button type="submit" form="getBoardList">게시물 목록</button>

    <% if ((pageCondition.equals("main")) || (pageCondition.equals("sort-recent")) || (pageCondition.equals("sort-hits")) ||
            (pageCondition.equals("sort-comments")) || (pageCondition.equals("sort-recommendations")) ||
            (pageCondition.equals("sort-decommendations")) || (pageCondition.equals("ownership")) ||
            (pageCondition.equals("board-favorite"))) { %>
    <form action="../<%= session.getAttribute("pageCondition") %>" method="get" id="getBoardList">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <% } else { %>
    <form action="../main" method="get" id="getBoardList">
        <input type="hidden" name="pageNumber" value="1">
    </form>
    <% } %>
    <form action="<%= session.getAttribute(" method="get" id="getBoard">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
</center>
</body>
</html>