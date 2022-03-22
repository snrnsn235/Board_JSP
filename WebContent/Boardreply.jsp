<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 답글달기</title>
</head>
<body>
<h2>답글 달기</h2>
<form action="boardreply-process.do" name="board" method="post">
	<input type="hidden" name="b_group" value="">
	<p> 제목 : <input type = "text" name = "title"></p>
	<p> 내용 : <input type="text"  name="content"></p>
	<p> <input type = "submit" value = "작성하기"></p>
</form>
</body>
</html>