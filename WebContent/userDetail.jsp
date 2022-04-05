<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 상세</title>
</head>
<style>
	table {
		border-collapese : collapse;
	}
	table tr th {
		font-weight:700;
		}
	table tr td, table tr th {
		border:1px solid #818181;
		width : 200px;
		text-align:center;
	}
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
		border : none;
		cursor : pointer;
		padding:10px;
		display:inline-block;
	}
</style>

<body>
<%@ include file="db_connection.jsp" %>
	<h1>회원 상세 페이지</h1>
	<table>
		<tr>
			<td>회원번호
			<td>${user.u_idx }</td>
		</tr>
		<tr>
			<td>회원 ID
			<td>${user.u_id }
		</tr>
		<tr>
			<td>회원이름
			<td>${user.u_name }
		</tr>
		<tr>
			<td>회원 전화번호
			<td>${user.u_tel }
		</tr>
		<tr>
			<td>회원 나이
			<td>${user.u_age }
		</tr>
		
		<tr style="height:50px;">
			<td style="border:none;">
				<a href="userEdit.do?u_idx=${user.u_idx }" style="width:70%;font-weight:700;background-color:#818181;color:#fff;" >수정</a>
			</td>
			<td style="border:none;">
				<a href="userDelete.do?u_idx=${user.u_idx }" style="width:70%;font-weight:700;background-color:red;color:#fff;">삭제</a>
			</td>
		</tr>
</table>
</body>
</html>