<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록2</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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
	div {
		border-collapse:collapse;
		width:470px;
		margin:40px auto;
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
<body id = "LList">
	<h1>회원 목록</h1>
		<c:choose>
		<c:when test="${sessionScope.user.u_level eq 'yes' }">
			<div style = "text-align:center; border-collapse:collapse;	width:500px; margin:40px auto;">
				<a href="insert.do" style="text-align: center; width:70%; font-weight:700; background-color:yellowgreen; color:#fff;">회원추가하기</a>
			</div>
				<table>
					<tr>
						<td colspan="4">전체 회원 수 : ${pagination.count}</td>
					<tr>
						<th>No</th>
						<th>ID</th>
						<th>이름</th>
						<th>관리자 권한 추가/제거</th>
					</tr>
					<c:forEach items="${list}" var="user" varStatus = "status">
						<tr>
							<td><a href="userdetail.do?u_idx=${user.u_idx}">${user.rownum}</a></td>
							<td>${user.u_id}</td>
							<td>${user.u_name}</td>
							<td>
								<button type="button" class="levelInsert">권한추가</button>
								<button type="button" class="levelRemove">권한제거하기</button>
							</td>
						</tr>
						<tr style="display: none;">
							<td colspan="4">
								<h4>관리자 권한을 주시겠습니까?</h4>
								<button type="button" 
										class="YesInsert" 
										u_level="yes"
										u_idx="${user.u_idx }">예</button>
								<button type="button" class="Cancel">아니요</button>
							</td>
						</tr>
						<tr style="display: none;">
							<td colspan="4">
								<h4>관리자 권한을 제거하시겠습니까?</h4>
								<button type="button" 
										class="Yesremove"
										u_level="no"
										u_idx="${user.u_idx }">예</button>
								<button type="button" class="Cancel">아니요</button>
							</td>
						</tr>
					</c:forEach>
				</table>
		</c:when>
		<c:when test="${sessionScope.user.u_level eq 'no' }">
		<table>
			<tr>
				<td colspan="4">전체 회원 수 : ${pagination.count}</td>
			<tr>
				<th>No</th>
				<th>ID</th>
				<th>이름</th>
			</tr>
			<c:forEach items="${list}" var="user" varStatus = "status">
				<tr>
					<td><a href="userdetail.do?u_idx=${user.u_idx}">${user.rownum}</a></td>
					<td>${user.u_id}</td>
					<td>${user.u_name}</td>
			</c:forEach>
		</table>
		</c:when>
		</c:choose>
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
		<script>
		//권한추가 창 띄우기
			$(document).on('click', '.levelInsert', function () {
				$(this).parent().parent().next().css('display', '');
				});
		//취소하기
			$(document).on('click', '.Cancel', function() {
				$(this).parent().parent().css('display', 'none');
				});
		//권한제거하기 창 띄우기
			$(document).on('click', '.levelRemove', function() {
				$(this).parent().parent().next().next().css('display', '');
				});
			
		//권한주기
			$(document).on('click', '.YesInsert', function() {
				let ulevel = $(this).attr('u_level');
				let uidx = $(this).attr('u_idx');

				$.ajax({
					method: "POST",
					url: "aj-levelUpdate.do",
					data: { u_level:ulevel, u_idx:uidx }
				})
				.done(function( msg ) {
					$('#LList').html(msg);
				});
			}); 
			
		
		//권한제거하기
			$(document).on('click', '.Yesremove', function() {
				let ulevel = $(this).attr('u_level');
				let uidx = $(this).attr('u_idx');

				$.ajax({
					method: "POST",
					url: "aj-levelUpdate.do",
					data: { u_level:ulevel, u_idx:uidx }
				})
				.done(function( msg ) {
					$('#LList').html(msg);
				});
			});
		</script>
</body>
</html>