<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 삭제</title>
</head>
<body>
<%@ include file="db_connection.jsp" %>

<h2>삭제 완료</h2>
<script>
	setTimeout(function () {
		window.location.href = "userlist.jsp";}
		</script>
</body>
</html>