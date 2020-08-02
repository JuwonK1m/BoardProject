<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020-03-24
  Time: 오후 8:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>
<script type="text/javascript">
    var information = '${information}';

    if (information == 'idOverlap')
        alert('이미 존재하는 계정의 아이디입니다. 다시 입력하십시오.');
    else if (information == 'emailOverlap')
        alert('이미 존재하는 계정의 이메일입니다. 다시 입력하십시오.');
    else if (information == 'idAndEmailOverlap')
        alert('이미 존재하는 계정의 아이디와 이메일입니다. 다시 입력하십시오.');
    else if (information == 'allDataEmpty')
        alert('모두 입력하지 않으셨습니다. 다시 입력하십시오.');
    else if (information == 'idAndPasswordEmpty')
        alert('아이디와 비밀번호를 입력하지 않으셨습니다. 다시 입력하십시오.');
    else if (information == 'idAndEmailEmpty')
        alert('아이디와 이메일을 입력하지 않으셨습니다. 다시 입력하십시오.');
    else if (information == 'passwordAndEmailEmpty')
        alert('비밀번호와 이메일을 입력하지 않으셨습니다. 다시 입력하십시오.');
    else if (information == 'idEmpty')
        alert('아이디를 입력하지 않으셨습니다. 다시 입력하십시오.');
    else if (information == 'passwordEmpty')
        alert('비밀번호를 입력하지 않으셨습니다. 다시 입력하십시오.');
    else if (information == 'emailEmpty')
        alert('이메일을 입력하지 않으셨습니다. 다시 입력하십시오.');
</script>
<body>
<form action="login" method="get">
    <input type="submit" value="로그인화면으로 돌아가기">
</form>
<center>
    <h3>회원가입</h3>
    <form action="join" method="post">
        아이디
        <br>
        <tr>
            <td>
                <input type="text" name="id">
            </td>
        </tr>
        <br><br>
        비밀번호
        <br>
        <tr>
            <td>
                <input type="password" name="password">
            </td>
        </tr>
        <br><br>
        이메일
        <br>
        <tr>
            <td>
                <input type="email" name="email">
            </td>
        </tr>
        <br><br>
        <input type="submit" value="가입하기">
    </form>
</center>
</body>
</html>