<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세</title>
</head>
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
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
		border:none;
		cursor:pointer;
		padding:10px;
		display:inline-block;
	}
</style>
<body>
	<h1>게시판 상세페이지</h1>
	<table>
			<tr>
				<td>번호</td>
				<td>${board.b_idx }</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>${board.b_title }</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>${board.b_content }</td>
			</tr>
			
			<tr style = "height:50px;">
				<td style = "boarder:none;">
					<a href = "boardedit.do?b_idx=${board.b_idx}" style="width:70%; font-weight:700; background-color:#818181; color:#fff;">수정</a>
				</td>
				<td style = "boarder:none;">
					<a href = "boarddelete.do?b_idx=${board.b_idx}" style="width:70%; font-weight:700; background-color:red; color:#fff;">삭제</a>
				</td>
			</tr>
			<tr>
				<td style = "boarder:none;">
					<a href ="boardreply.do?b_group=${board.b_group}&b_order=${board.b_order}" style="width:70%; font-weight:700; background-color:green; color:#fff;">답글달기</a>
				</td>
				<td style = "boarder:none;">
					<a href ="boardlist.do" style="width:70%; font-weight:700; background-color:skyblue; color:#fff;">돌아가기</a>
				</td>
			</tr>		
	</table>
</body>
</html>