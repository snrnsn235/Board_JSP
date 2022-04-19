<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 추가</title>
</head>
<body>
<h2>게시판 추가하기</h2>
<form action="boardinsert-process.do" name="board" method="post">
	<p> 제목: <input type = "text" name="title" value = "${board.b_title }"></p>
	<p> 내용 : <input type = "text" maxlength="20" size = "20" name="content" value = "${board.b_content }"></p>	
	<p> 파일 업로드 : <input type = "file" name = "filename">
	<p> <input type="submit" value="추가하기"></p>
</form>
</body>
</html>