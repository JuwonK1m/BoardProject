<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-03-27
  Time: 오후 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageState = (String)session.getAttribute("pageState");
    String pageNumber = (String)request.getAttribute("pageNumber");
    String pageCondition = (String)session.getAttribute("pageCondition");
%>
<html>
<head>
    <title>새글 등록</title>
</head>
<script type="text/javascript">
    window.history.forward(1);

    var information = '${information}';

    if (information == 'titleAndContentExcess') {
        alert('제목은 100byte 이하, 내용은 20000byte 이하만 허용됩니다.');
        information = null;
    } else if (information == 'titleExcess') {
        alert('제목은 100byte 이하만 허용됩니다.');
        information = null;
    } else if (information == 'contentExcess') {
        alert('내용은 20000byte 이하만 허용됩니다.');
        information = null;
    } else if (information == 'titleEmpty') {
        alert('제목이 없습니다.');
        information = null;
    }
</script>
<body>
<center>
    <h1>글 등록</h1>
    <br>
    <form action="board" method="post">
        <table border="1" cellpadding="0" cellspacing="0">
            <tr>
                <td bgcolor="burlywood" width="70">제목</td><td align="left">
                <input type="text" name="title"/></td>
            </tr>
            <tr>
                <td bgcolor="burlywood" width="70">내용</td><td align="left">
                <textarea name="content" cols="40" rows="10"></textarea></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="등록 완료"/>
                    <% if (pageState.equals("getBoard")) { %>
                    <button type="submit" form="getBoard">등록 취소</button>
                    <% } %>
                </td>
            </tr>
        </table>
    </form>
    <hr>
    <button type="submit" form="getBoardList">게시물 목록</button>

    <form action="board/<%= session.getAttribute("currentBoardNumber") %>/" method="get" id="getBoard">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>

    <% if ((pageCondition.equals("main")) || (pageCondition.equals("sort-recent")) || (pageCondition.equals("sort-hits")) ||
            (pageCondition.equals("sort-comments")) || (pageCondition.equals("sort-recommendations")) ||
            (pageCondition.equals("sort-decommendations")) || (pageCondition.equals("ownership")) ||
            (pageCondition.equals("board-favorite"))) { %>
    <form action="<%= session.getAttribute("pageCondition") %>" method="get" id="getBoardList">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <% } else { %>
    <form action="main" method="get" id="getBoardList">
        <input type="hidden" name="pageNumber" value="1">
    </form>
    <% } %>
</center>
</body>
</html>
