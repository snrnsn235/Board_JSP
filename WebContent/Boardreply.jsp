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
	<input type="hidden" name="b_group" value="${board.b_group }">
	<input type="hidden" name="b_order" value="${board.b_order }">
	<input type="hidden" name="b_depth" value="${board.b_depth }">
	
	<p> 제목 : <input type="text" name="title" value="${board.b_title }"></p>
	<p> 내용 : <input type="text"  name="content" value="${board.b_content }"></p>
	<p> <input type = "submit" value = "작성하기" ></p>
</form>
</body>
</html>