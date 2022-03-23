<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 수정</title>
</head>

<body>
<%@ include file = "db_connection.jsp" %>
	<h1>게시판 수정</h1>

		<form action="boardedit-process.do" name="board" method="post">
			<input type="hidden" name="b_idx" value="${board.b_idx}">
			<p> 제목 : <input type = "text" name="title" value="${board.b_title}">
			<p> 내용 : <input type = "text" name="content" value="${board.b_content }">
			<p> <input type="submit" value="수정완료"></p>
		</form>
		

</body>
</html>