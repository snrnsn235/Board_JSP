<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body id = "LList">
	<h1>회원 목록</h1>
		<table>
			<tr>
				<td colspan="4">전체 회원 수 : ${pagination.count}</td>
			<tr>
				<th>No</th>
				<th>ID</th>
				<th>이름</th>
				<th>관리자 권한 추가/제거</th>
			</tr>
			<c:forEach items="${list}" var="item" varStatus = "status">
				<tr>
					<td><a href="userdetail.do?u_idx=${item.u_idx}">${item.rownum}</a></td>
					<td>${item.u_id}</td>
					<td>${item.u_name}</td>
					<td>
						<button type="button" class="levelInsert">권한추가</button>
						<button type="button" class="levelRemove">권한제거하기</button>
					</td>
				</tr>
				<tr style="display: none;">
					<td colspan="4">
						<form name="level" method="post">
						<h4>관리자 권한을 주시겠습니까?</h4>
						<button type="button" class="YesInsert">예</button>
						<button type="button" class="Cancel">아니요</button>
						</form>
					</td>
				</tr>
				<tr style="display: none;">
					<td colspan="4">
						<form name="level" method="post">
						<h4>관리자 권한을 제거하시겠습니까?</h4>
						<button type="button" class="Yes">예</button>
						<button type="button" class="Cancel">아니요</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
		<!-- 아래부터 pagination -->
		<div>
			<ul>
				<c:choose>
					<c:when test="${pagination.startPage-1 != 0}">
						<li style="">
							<a href="userlist.do?page=${pagination.prevPage}">◀</a>
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
								<a href="userlist.do?page=${i}">${i}</a>
							</li>
						</c:when>
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when test = "${pagination.nextPage lt pagination.lastPage }">
						<li style="">
							<a href="userlist.do?page=${pagination.nextPage}">▶</a>
						</li>
					</c:when>
				</c:choose>
			</ul>
		</div>
</body>