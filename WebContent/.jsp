<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 추가</title>
</head>
<body>
<div style = "text-align:center; border-collapse:collapse;	width:500px; margin:40px auto;">
	<h2>이미지 추가하기</h2>
		<form action="imageInsert-process.do" name="board" method="post" enctype="multipart/form-data">
			<p> 파일 업로드 : <input type = "file" name="filename" value="${board.filename }">
			<p> <input type="submit" value="추가하기"></p>
		</form>
</div>
</body>
</html>