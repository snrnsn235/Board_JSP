<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계시판 만들기 </title>
</head>
<body>
	<h2>게시판 만들기</h2>
	<form action="boardjoin_process.jsp" name="user" method="post">
		<p> 제목: <input type = "text" name="title"></p>	
		<p> 내용 : <input type = "text" maxlength="20" size = "20" name="content"></p>	
		<p> 작성자 : <input type="text" maxlength = "4" size = "4" name="writer"></p>
		<p> 작성일시 : <input type="text" name="date">
		<p> 조회수 : <input type = "text" name="hits"></p>
		<p> <input type="submit" value="저장하기"></p>
	</form>
</body>
</html>