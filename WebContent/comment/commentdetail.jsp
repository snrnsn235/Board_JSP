<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 상세정보</title>
<style>
	h1 {
	text-align: center;
	}
	table {
		border-collapse:collapse;
		margin:40px auto;
	}
	table tr th {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:400px;
		height:3px;
		text-align:center;
	}
</style>
</head>
<body>
	<h1>댓글 상세 페이지</h1>
		<table>
			<tr>
				<td>번호</td>
				<td>${comment.c_idx }</td>
			</tr>
			<tr>
				<td>댓글내용</td>
				<td>${comment.c.content }</td>
			</tr>
			
		</table>
</body>
</html>