<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-04-03
  Time: 오후 8:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="dto.TodoDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.CompletedTodoDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<TodoDTO> unexecutedTodoList = (List<TodoDTO>)request.getAttribute("unexecutedTodoList");
    List<TodoDTO> todoList = (List<TodoDTO>)request.getAttribute("todoList");
    List<CompletedTodoDTO> completedTodoList = (List<CompletedTodoDTO>)request.getAttribute("completedTodoList");
    int todoListSize = todoList.size();
    int completedTodoListSize = completedTodoList.size();

    String strPageNumber = (String)request.getAttribute("pageNumber");
    int pageNumber = Integer.parseInt(strPageNumber);
%>
<html>
<head>
    <title>Todo List</title>
</head>
<body>
<form action="main" method="get">
    <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
    <button type="submit">게시판으로 돌아가기</button>
</form>
<table border="1" cellspacing="0" cellspacing="0" width="480" align="left">
    <tr>
        <th bgcolor="orangered" width="480" align="center">${yesterday}</th>
    </tr>
    <tr>
        <th width="480" align="center">어제 못끝낸 일</th>
    </tr>
    <% for (int i = 0; i < unexecutedTodoList.size(); i++) { %>
    <tr>
        <td>
            <form action="todo-complete/<%= unexecutedTodoList.get(i).getNumber() %>" method="post" style="display: inline">
                <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                <button type="submit">완료했음</button>
            </form>
            <%= unexecutedTodoList.get(i).getContent() %>
        </td>
    </tr>
    <% } %>
</table>
<table border="1" cellspacing="0" cellspacing="0" width="960" align="right">
    <tr>
        <th bgcolor="mediumorchid" width="480" align="center">${today}</th>
        <th bgcolor="aqua" width="480" align="center">${today}</th>
    </tr>
    <tr>
        <th width="480" align="center">오늘 할일</th>
        <th width="480" align="center">오늘 완료한일</th>
    </tr>
    <% if (todoListSize >= completedTodoListSize) {
       for (int i = 0; i < todoList.size(); i++) { %>
    <tr>
        <td width="480">
            <form action="todo/view-update/<%= todoList.get(i).getNumber() %>" method="get" style="display: inline">
                <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                <button type="submit">수정</button>
            </form>
            <form action="todo/<%= todoList.get(i).getNumber() %>" method="post" style="display: inline">
                <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                <input type="hidden" name="_method" value="delete">
                <button type="submit">삭제</button>
            </form>
            <form action="todo-complete/<%= todoList.get(i).getNumber() %>" method="post" style="display: inline">
                <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                <button type="submit">완료했음</button>
            </form>
            <%= todoList.get(i).getContent() %>
        </td>
        <% if (i >= completedTodoListSize) { %>
        <td width="400"></td>
        <% } else { %>
        <td width="400"><%= completedTodoList.get(i).getContent() %></td>
        <% } %>
    </tr>
    <% } } else {
    for (int i = 0; i < completedTodoList.size(); i++) { %>
    <tr>
        <% if (i >= todoListSize) { %>
        <td width="400"></td>
        <% } else { %>
        <td width="480">
            <form action="todo/view-update/<%= todoList.get(i).getNumber() %>" method="get" style="display: inline">
                <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                <button type="submit">수정</button>
            </form>
            <form action="todo/<%= todoList.get(i).getNumber() %>" method="post" style="display: inline">
                <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                <input type="hidden" name="_method" value="delete">
                <button type="submit">삭제</button>
            </form>
            <form action="todo-complete/<%= todoList.get(i).getNumber() %>" method="post" style="display: inline">
                <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
                <button type="submit">완료했음</button>
            </form>
            <%= todoList.get(i).getContent() %>
        </td>
        <% } %>
        <td width="400">
            <%= completedTodoList.get(i).getContent() %>
        </td>
    </tr>
    <% } } %>
    <tr>
        <td>
            <textarea name="content" rows="1" cols="57" form="insertTodo"></textarea>
            <button type="submit" form="insertTodo">추가</button>
        </td>
    </tr>
</table>

<form action="todo" method="post" id="insertTodo">
    <input type="hidden" name="pageNumber" value="<%= pageNumber %>">
</form>
</body>
</html>
