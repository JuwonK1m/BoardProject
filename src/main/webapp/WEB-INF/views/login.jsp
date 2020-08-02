<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-03-24
  Time: 오후 8:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>로그인</title>
  </head>
  <script type="text/javascript">
    window.history.forward(1);

    var information = '${information}';

    if (information == 'joinSuccess')
      alert('회원가입이 완료되었습니다.');
    else if (information == 'userNotExist')
      alert('유저 정보가 없습니다. 회원가입 하십시오.');
    else if (information == 'idEmpty')
      alert('아이디를 입력하지 않으셨습니다. 다시 입력하십시오.');
    else if (information == 'passwordEmpty')
      alert('비밀번호를 입력하지 않으셨습니다. 다시 입력하십시오.');
    else if (information == 'idAndPasswordEmpty')
      alert('아이디와 비밀번호를 입력하지 않으셨습니다. 다시 입력하십시오.');
  </script>
  <body>
  <center>
  <h3>로그인</h3>
  <form action="user" method="get">
    <table border="1" cellpadding="0" cellspacing="0">
      <tr>
        <td bgcolor="aqua">아이디</td>
        <td>
          <input type="text" name="id"/>
        </td>
        <td rowspan="2">
          <input type="submit" value="로그인"/>
        </td>
      </tr>
      <tr>
        <td bgcolor="aqua">비밀번호</td>
        <td><input type="password" name="password"/></td>
      </tr>
    </table>
  </form>
  <h4>계정이 없으신가요?</h4>
  <form action="view-join" method="get">
    <input type="submit" value="회원가입">
  </form>
  </center>
  </body>
</html>
