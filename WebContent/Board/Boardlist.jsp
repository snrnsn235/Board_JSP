<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록2</title>
</head>
<style>
	table {
		border-collapse:collapse;
	}
	table tr th {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:200px;
		text-align:center;
	}
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
	}
</style>
<body>
	<h1>회원 목록</h1>
	<table>
		<tr>
			<th>ID</th>
			<th>이름</th>
			<th>내용</th>
		</tr>
		<c:forEach items="${Boardlist}" var="item">
			<tr>
				<td>${item.b_id }</td>
				<td>${item.b_writer }</td>
				<td>${item.b_content }</td>
			</tr>
		</c:forEach>	
</table>
</body>
</html>