<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 추가</title>
</head>
<body>
<div style = "text-align:center; border-collapse:collapse;	width:500px; margin:40px auto;">
	<h2>게시판 추가하기</h2>
		<form action="boardinsert-process.do" name="board" method="post" enctype="multipart/form-data">
			<p> 제목: <input type = "text" name="title" value = "${board.b_title }"></p>
				내용 : <textarea name="content" placeholder = "내용을 입력해주세요." rows="4" cols="70" value = "${board.b_content }"></textarea>
			<p> 파일 업로드 : <input type = "file" name="filename1" value="${board.filename1 }">
			<p> 파일 업로드 : <input type = "file" name="filename2" value="${board.filename2 }">
			<p> <input type="submit" value="추가하기"></p>
		</form>
</div>
</body>
</html>