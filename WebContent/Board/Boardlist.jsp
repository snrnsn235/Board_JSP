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
	h1 {
		text-align:center;
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
		width:200px;
		text-align:center;
	}
	
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
	}
		
	ul {
		width:600px;
		height:50px;
		margin:10px auto;
	}
	li {
		list-style:none;
		width:50px;
		line-height:50px;
		border:1px solid #ededed;
		float:left;
		text-align:center;
		margin:0 5px;
		border-radius:5px;
	}
</style>
<body>
	<h1>게시판 목록</h1>
	<div style = "border-collapse:collapse;	width:500px; margin:40px auto;">
		<h3 style = "text-align:center;"> 게시글 검색하기 </h3>
			<form>
				<fieldset>
					<legend>게시글 검색 필드</legend>
					<label>검색분류</label>
						<select name="field">
							<option value="b_idx">번호</option>
							<option value="b_title">제목</option>
							<option value="b_content">내용</option>
						</select>
					<label class="hidden">검색어</label>
							<input type="text" name="value" value="${param.q}"/>
							<input type="submit" value="검색">	
					</fieldset>
				</form>
	</div>
	<hr>
	<br>
	<div style = "text-align:center; border-collapse:collapse;	width:500px; margin:40px auto;">		
		<a href="boardinsert.do" style="text-align: center; width:70%; font-weight:700; background-color:yellowgreen; color:#fff;">게시글 추가하기</a>
		<a href="login-process.do?u_idx${user.u_idx }" style="text-align: center; width:70%; font-weight:700; background-color:yellowgreen; color:#fff;">돌아가기</a>
	</div>
			<table>
				<tr>
					<td colspan="4">전체 게시판 개수 : ${pagination.count }</td>
				</tr>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>내용</th>
					<th>아이디</th>
				</tr>
				<c:forEach items="${boardlist}" var="board" varStatus = "status">
					<tr>
						<td><a href="boarddetail.do?b_idx=${board.b_idx}">${board.rownum}</a></td>
						<td>${board.b_title }</td>
						<td style="text-align: center;">
							<c:forEach var="i" begin="1" end="${board.b_depth}">
							->
							</c:forEach>
							${board.b_content }
						</td>
						<td>${user.u_id }</td>
					</tr>
				</c:forEach>
			</table>
	<!-- pagination -->
	<div style = "border-collapse:collapse;	width:360px; margin:40px auto;">
		<ul>
			<c:choose>
					<c:when test="${pagination.startPage-1 != 0}">
						<li style="">
							<a href="boardlist.do?page=${pagination.prevPage}&field=${search.field}&value=${search.value}">◀</a>
						</li>
					</c:when>
			</c:choose>
			<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}" step="1">
					<c:choose>
						<c:when test="${pagination.page eq i }">
							<li style="background-color:#ededed;">
								<span>${i}</span>
							</li>
						</c:when>
						<c:when test="${ pagination.page ne i }">
							<li>
								<a href="boardlist.do?page=${i}&field=${search.field}&value=${search.value}">${i}</a>
							</li>
						</c:when>
					</c:choose>
				</c:forEach>
			<c:choose>
					<c:when test = "${pagination.nextPage-1 != pagination.lastPage }">
						<li style="">
							<a href="boardlist.do?page=${pagination.nextPage}&field=${search.field}&value=${search.value}">▶</a>
						</li>
					</c:when>
				</c:choose>	
		</ul>
	</div>
	<script></script>
</body>
</html>