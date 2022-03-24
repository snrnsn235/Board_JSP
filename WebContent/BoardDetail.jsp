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
	
	div {
		border-collapse:collapse;
		margin:40px auto;
		width : 780px;
	}
	div textarea {
		 height: 200px;
		 padding: 100px 100px;
		 box-sizing: border-box;
		 border: 2px solid #ccc;
		 border-radius: 4px;
		 background-color: #f8f8f8;
		 font-size: 16px;
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
					<a href ="boardreply.do?b_group=${board.b_group}&b_order=${board.b_order}&b_depth=${board.b_depth}" style="width:70%; font-weight:700; background-color:green; color:#fff;">답글달기</a>
				</td>
				<td style = "boarder:none;">
					<a href ="boardlist.do" style="width:70%; font-weight:700; background-color:skyblue; color:#fff;">돌아가기</a>
				</td>
			</tr>		
	</table>
	<div>
		<textarea action = "commentinsert.do" name="comment" rows="4" cols="70"></textarea>
			<input type="hidden" name="b_idx" value="${board.b_idx }">
			<input type="hidden" name="c_idx" value="${comment.c_idx }">
			<input type="hidden" name="u_idx" value="${user.u_idx }">
		
		
		<a href ="commentinsert.do? b_idx=${board.b_idx }&c_idx=${comment.c_idx}" style="font-weight:70; background-color:skyblue; color:#fff;">댓글달기</a>
	</div>

</body>
</html>