<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-03-26
  Time: 오후 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.UserDTO" %>
<%@ page import="dto.BoardDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="other.Pagination" %>
<%
    UserDTO userDTO = (UserDTO)session.getAttribute("user");
    String userId = userDTO.getId();
    List<BoardDTO> boardList = (List<BoardDTO>)request.getAttribute("boardList");

    String pageCondition = (String)session.getAttribute("pageCondition");

    Pagination pagination = (Pagination)request.getAttribute("pagination");
    int pageNumber = pagination.getPageNumber();
    int pageCount = pagination.getPageCount();

    session.setAttribute("pageState", "getBoardList");
%>
<html>
<head>
    <title>게시판</title>
</head>
<body>
<center>
    <h1><a href="main?pageNumber=1" methods="get">게시판</a></h1>
    <h3><%= userId %> 님 환영합니다!
        <button type="submit" form="view-logout">로그아웃</button>
        <button type="submit" form="view-delete">회원탈퇴</button>
    </h3>

    <form action="condition" method="get">
        <input type="hidden" name="pageNumber" value="1">
        <table border="1" cellspacing="0" cellspacing="0" width="840">
            <tr>
                <td>
                    <button type="submit" form="getBoardList-sort-recent">최신순</button>
                    <button type="submit" form="getBoardList-sort-hits">조회순</button>
                    <button type="submit" form="getBoardList-sort-comments">댓글순</button>
                    <button type="submit" form="getBoardList-sort-recommendations">추천순</button>
                    <button type="submit" form="getBoardList-sort-decommendations">비추천순</button>
                    <button type="submit" form="getUserBoardList">내 게시물</button>
                    <button type="submit" form="getFavoriteBoardList">즐겨찾기</button>
                    <button type="submit" form="getTodoList">Todo</button>
                </td>
                <td align="right">
                    <select name="searchCondition">
                        <option value="title">제목
                        <option value="content">내용
                        <option value="writer">글작성자
                        <option value="comment">댓글
                        <option value="commentWriter">댓글작성자
                        <option value="integration">통합
                    </select>
                    <input name="text" type="text"/>
                    <input type="submit" value="검색"/>
                </td>
            </tr>
        </table>
    </form>

    <table border="1" cellpadding="0" cellspacing="0" width="840">
        <tr>
            <th bgcolor="gold" width="100">게시물 번호</th>
            <th bgcolor="gold" width="330">제목</th>
            <th bgcolor="gold" width="150">글쓴이</th>
            <th bgcolor="gold" width="160">최종 수정</th>
            <th bgcolor="gold" width="100">조회수</th>
        </tr>
        <% if ((pageCondition.equals("main")) || (pageCondition.equals("sort-recent")) || (pageCondition.equals("sort-hits")) ||
                (pageCondition.equals("sort-comments")) || (pageCondition.equals("sort-recommendations")) ||
                (pageCondition.equals("sort-decommendations")) || (pageCondition.equals("ownership")) ||
                (pageCondition.equals("board-favorite"))) { %>
        <% for (int i = 0; i < boardList.size(); i++) { %>
        <tr>
            <td align="center"><%= boardList.get(i).getNumber() %></td>
            <td align="left"><a href="board/<%= boardList.get(i).getNumber()%>/?pageNumber=<%= pageNumber %>" methods="get"><%= boardList.get(i).getTitle() %></a></td>
            <td align="center"><%= boardList.get(i).getWriter() %></td>
            <td align="center"><%= boardList.get(i).getDate() %></td>
            <td align="center"><%= boardList.get(i).getHit() %></td>
        </tr>
        <% } } else { %>
        <% for (int i = boardList.size() - 1; i >= 0; i--) { %>
        <tr>
            <td align="center"><%= boardList.get(i).getNumber() %></td>
            <td align="left"><a href="board/<%= boardList.get(i).getNumber()%>/?pageNumber=<%= pageNumber %>" methods="get"><%= boardList.get(i).getTitle() %></a></td>
            <td align="center"><%= boardList.get(i).getWriter() %></td>
            <td align="center"><%= boardList.get(i).getDate() %></td>
            <td align="center"><%= boardList.get(i).getHit() %></td>
        </tr>
        <% } } %>
    </table>
    <br>
    <form action="view-insert" method="get">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <button type="submit">새글 등록</button>
    </form>

    <% if ((pageCondition.equals("main")) || (pageCondition.equals("sort-recent")) || (pageCondition.equals("sort-hits")) ||
            (pageCondition.equals("sort-comments")) || (pageCondition.equals("sort-recommendations")) ||
            (pageCondition.equals("sort-decommendations")) || (pageCondition.equals("ownership")) || (pageCondition.equals("board-favorite"))) { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="1">
        <button type="submit">처음</button>
    </form>

    <% if (pageNumber != 1) { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= pageNumber - 1 %>">
        <button type="submit">이전</button>
    </form>
    <% } else { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <button type="submit">이전</button>
    </form>
    <% } %>

    <% if (pageNumber % 5 == 0) { %>
    <% for (int i = pageNumber - 4; ((i <= pageCount) && (i <= pageNumber)); i++) { %>
    <% if (i == pageNumber) { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= i %>">
        <button type="submit" style="background-color: aqua"><%= i %></button>
    </form>
    <% } else { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= i %>">
        <button type="submit"><%= i %></button>
    </form>
    <% } } } else { %>
    <% for (int i = 5 * (pageNumber / 5) + 1; ((i <= pageCount) && (i <= 5 * (pageNumber / 5) + 5)); i++) { %>
    <% if (i == pageNumber) { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= i %>">
        <button type="submit" style="background-color: aqua"><%= i %></button>
    </form>
    <% } else { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= i %>">
        <button type="submit"><%= i %></button>
    </form>
    <% } } }%>

    <% if (pageNumber + 1 <= pageCount) { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= pageNumber + 1 %>">
        <button type="submit">다음</button>
    </form>
    <% } else { %>
    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
        <button type="submit">다음</button>
    </form>
    <% } %>

    <form action="<%= pageCondition %>" method="get" style="display: inline">
        <input type="hidden" name="pageNumber" value="<%= pageCount %>">
        <button type="submit">끝</button>
    </form>
    <% } %>

    <form action="sort-recent" method="get" id="getBoardList-sort-recent">
        <input type="hidden" name="pageNumber" value="1">
    </form>
    <form action="sort-hits" method="get" id="getBoardList-sort-hits">
        <input type="hidden" name="pageNumber" value="1">
    </form>
    <form action="sort-comments" method="get" id="getBoardList-sort-comments">
        <input type="hidden" name="pageNumber" value="1">
    </form>
        <form action="ownership" method="get" id="getUserBoardList">
            <input type="hidden" name="pageNumber" value="1">
        </form>
    <form action="view-logout" method="get" id="view-logout">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <form action="../user/view-delete" method="get" id="view-delete">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <form action="todolist" method="get" id="getTodoList">
        <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    </form>
    <form action="board-favorite" method="get" id="getFavoriteBoardList">
        <input type="hidden" name="pageNumber" value="1">
    </form>
    <form action="sort-recommendations" method="get" id="getBoardList-sort-recommendations">
        <input type="hidden" name="pageNumber" value="1">
    </form>
    <form action="sort-decommendations" method="get" id="getBoardList-sort-decommendations">
        <input type="hidden" name="pageNumber" value="1">
    </form>
</center>
</body>
</html>
