<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 수정</title>
</head>
<body>
<%@ include file="db_connection.jsp" %>
	<h1>회원 정보 수정</h1>
	<form action="userEdit-process.do" name="user" method="post">
		<input type = "hidden" name="u_idx" value="${user.u_idx }">
		<p> 아이디 : <input type = "text" name="u_id" value="${user.u_id }">
		<p> 비밀번호 : <input type = "password" name="u_pw" value="${user.u_pw}">
		<p> 이름 : <input type = "text" name="u_name" value="${user.u_name }">
		<p> 연락처 : <input type = "text" maxlength = "4" size="4" name="u_tel1" value="${user.arr_tel[0] }">
				 <input type = "text" maxlength = "4" size="4" name="u_tel2" value="${user.arr_tel[1] } ">
				 <input type = "text" maxlength = "4" size="4" name="u_tel3" value="${user.arr_tel[2] }">
		</p>
		<p> 나이 : <input type = "text" name="u_age" value="${user.u_age }">
		<p> <input type = "submit" value="수정완료"></p>
	</form>
</body>
</html>
