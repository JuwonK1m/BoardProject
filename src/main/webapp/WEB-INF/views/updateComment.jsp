<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-04-02
  Time: 오후 4:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.BoardDTO" %>
<%@ page import="dto.UserDTO" %>
<%@ page import="dto.CommentDTO" %>
<%@ page import="java.util.List" %>
<%
    BoardDTO board = (BoardDTO)request.getAttribute("board");
    UserDTO user = (UserDTO)session.getAttribute("user");
    String userId = user.getId();
    List<CommentDTO> commentList = (List<CommentDTO>)request.getAttribute("commentList");
    Boolean objisFavoriteBoard = (Boolean)request.getAttribute("isFavoriteBoard");
    boolean isFavoriteBoard = objisFavoriteBoard;
    String commendation = (String)request.getAttribute("commendation");
    String strPageNumber = request.getParameter("pageNumber");
    int pageNumber = Integer.parseInt(strPageNumber);
    String pageCondition = (String)session.getAttribute("pageCondition");

    session.setAttribute("currentBoardNumber", board.getNumber());
    session.setAttribute("currentBoardTitle", board.getTitle());
    session.setAttribute("currentBoardWriter", board.getWriter());
    session.setAttribute("currentBoardContent", board.getContent());
    session.setAttribute("currentBoardDate", board.getDate());
    session.setAttribute("currentBoardHit", board.getHit());

    session.setAttribute("pageState", "getBoard");

    Integer objUpdateCommentNumber = (Integer)request.getAttribute("updateCommentNumber");
    int updateCommentNumber = objUpdateCommentNumber;
%>
<html>
<head>
    <title>게시물</title>
</head>
<body>
<center>
    <h1>게시물</h1>
    <% if (!commendation.equals("nothing")) {
        if (commendation.equals("recommendation")) { %>
    <%= user.getId() %>님이 추천한 게시물
    <% } else { %>
    <%= user.getId() %>님이 비추천한 게시물
    <% } } %>
    <form action="../../../../view-update" method="get">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <table border="1" cellpadding="0" cellspacing="0" width="600" height="360">
            <tr>
                <td bgcolor="burlywood" width="87" >제목</td>
                <td align="left"><%= board.getTitle() %></td>
            </tr>
            <tr>
                <td bgcolor="burlywood">작성자</td>
                <td align="left"><%= board.getWriter() %></td>
            </tr>
            <tr>
                <td bgcolor="burlywood" height="250">내용</td>
                <td align="left"><%= board.getContent() %></td>
            </tr>
            <tr>
                <td bgcolor="burlywood">최종 수정</td>
                <td align="left"><%= board.getDate() %></td>
            </tr>
            <tr>
                <td bgcolor="burlywood">조회수</td>
                <td align="left"><%= board.getHit() %></td>
            </tr>
            <tr>
                <td bgcolor="burlywood">즐겨찾기</td>
                <% if (isFavoriteBoard == true) { %>
                <td align="left">O</td>
                <% } else { %>
                <td align="left">X</td>
                <% } %>
            </tr>
            <tr>
                <td bgcolor="burlywood">추천/비추천</td>
                <td>
                    <%= board.getRecommendationCount()%> / <%= board.getDecommendationCount() %>
                </td>
            </tr>
            <% if (userId.equals(board.getWriter())) { %>
            <tr>
                <td colspan="2" align="center">
                    <% if (commendation.equals("recommendation")) { %>
                    <button type="submit" form="deleteRecommendation">추천 취소</button>
                    <button type="submit" form="updateDecommendation">비추천</button>
                    <% } else if (commendation.equals("decommendation")) { %>
                    <button type="submit" form="updateRecommendation">추천</button>
                    <button type="submit" form="deleteDecommendation">비추천 취소</button>
                    <% } else { %>
                    <button type="submit" form="updateRecommendation">추천</button>
                    <button type="submit" form="updateDecommendation">비추천</button>
                    <% } %>
                    <br>
                    <input type="submit" value="수정"/>
                    <button type="submit" form="view-delete">삭제</button>
                    <% if (isFavoriteBoard == true) { %>
                    <button type="submit" form="deleteFavoriteBoard">즐겨찾기에서 삭제</button>
                    <% } else { %>
                    <button type="submit" form="insertFavoriteBoard">즐겨찾기에 추가</button>
                    <% } %>
                </td>
            </tr>
            <% } else { %>
            <tr>
                <td colspan="2" align="center">
                    <% if (commendation.equals("recommendation")) { %>
                    <button type="submit" form="deleteRecommendation">추천 취소</button>
                    <button type="submit" form="updateDecommendation">비추천</button>
                    <% } else if (commendation.equals("decommendation")) { %>
                    <button type="submit" form="updateRecommendation">추천</button>
                    <button type="submit" form="deleteDecommendation">비추천 취소</button>
                    <% } else { %>
                    <button type="submit" form="updateRecommendation">추천</button>
                    <button type="submit" form="updateDecommendation">비추천</button>
                    <% } %>
                    <br>
                    <% if (isFavoriteBoard == true) { %>
                    <button type="submit" form="deleteFavoriteBoard">즐겨찾기에서 삭제</button>
                    <% } else { %>
                    <button type="submit" form="insertFavoriteBoard">즐겨찾기에 추가</button>
                    <% } %>
                </td>
            </tr>
            <% } %>
        </table>
    </form>
    <button type="submit" form="view-insert">새글 등록</button>
    <button type="submit" form="getBoardList">게시물 목록</button>
    <br><br>

    댓글 <%= commentList.size() %>개
    <table border="1" cellpadding="0" cellspacing="0" width="870">
        <tr>
            <th bgcolor="lightskyblue" width="120">작성자</th>
            <th bgcolor="lightskyblue" width="152">최근 등록</th>
            <th bgcolor="lightskyblue" width="505">내용</th>
            <th bgcolor="lightskyblue" width="92">옵션</th>
        </tr>
        <% for (int i = 0; i < commentList.size(); i++) { %>
        <tr>
            <td width="120"><%= commentList.get(i).getWriter() %></td>
            <td width="152"><%= commentList.get(i).getDate() %></td>
            <% if (commentList.get(i).getNumber() != updateCommentNumber) { %>
            <td width="505"><%= commentList.get(i).getContent() %></td>
            <% } else { %>
            <td><textarea name="revisedContent" style="width:100%" form="updateComment"><%= commentList.get(i).getContent() %></textarea></td>
            <% } %>
            <td width="93">
                <% if (commentList.get(i).getWriter().equals(userId)) {
                    if (commentList.get(i).getNumber() != updateCommentNumber) { %>
                <form action="../../../comment/view-update/<%= commentList.get(i).getNumber() %>/" method="get" style="display: inline">
                    <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                    <input type="submit" value="수정">
                </form>
                <form action="../../../comment/<%= commentList.get(i).getNumber() %>/" method="post" style="display: inline">
                    <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                    <input type="hidden" name="_method" value="delete">
                    <input type="submit" value="삭제">
                </form>
                <% } else { %>
                <form action="../<%= updateCommentNumber %>" method="post" id="updateComment" style="display: inline">
                    <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                    <input type="hidden" name="_method" value="put">
                    <button type="submit">완료</button>
                </form>
                <form action="../../../" method="get" style="display: inline">
                    <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                    <button type="submit">취소</button>
                </form>
                <% }} %>
            </td>
        </tr>
        <% } %>
    </table>
    <br>
    <form action="../../../comment" method="post">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <table border="1" cellspacing="0" cellspacing="0" width="870">
            <tr>
                <td>
                    <textarea name="content" rows="5" cols="111"></textarea>
                </td>
                <td>
                    <button type="submit">등록</button>
                </td>
            </tr>
        </table>
    </form>

    <form action="../../../../../view-insert" method="get" id="view-insert">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <form action="../../../../view-delete" method="get" id="view-delete">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <% if ((pageCondition.equals("main")) || (pageCondition.equals("sort-recent")) || (pageCondition.equals("sort-hits")) ||
            (pageCondition.equals("sort-comments")) || (pageCondition.equals("sort-recommendations")) ||
            (pageCondition.equals("sort-decommendations")) || (pageCondition.equals("ownership")) ||
            (pageCondition.equals("board-favorite"))) { %>
    <form action="../../../../../<%= session.getAttribute("pageCondition") %>" method="get" id="getBoardList">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <% } else { %>
    <form action="../../../../../main" method="get" id="getBoardList">
        <input type="hidden" name="pageNumber" value="1">
    </form>
    <% } %>
    <form action="../../../recommendation" method="post" id="updateRecommendation">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <input type="hidden" name="_method" value="put">
    </form>
    <form action="../../../decommendation" method="post" id="updateDecommendation">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <input type="hidden" name="_method" value="put">
    </form>
    <form action="recommendation" method="post" id="deleteRecommendation">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <input type="hidden" name="_method" value="delete">
    </form>
    <form action="decommendation" method="post" id="deleteDecommendation">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <input type="hidden" name="_method" value="delete">
    </form>
    <form action="../../../favorite" method="post" id="insertFavoriteBoard">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <form action="../../../favorite" method="post" id="deleteFavoriteBoard">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <input type="hidden" name="_method" value="delete">
    </form>
    <br>
</center>
</body>
</html>