<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인중입니다.</title>
</head>
<style>
	body {
	margin : 0;
	padding : 0;
	}
	div:nth-child(1) {
		background-color:rgba(75,189,217, 0.1);
		padding:10px 30px;
		font-size:1.2rem;
		font-weight:700;
	}
	div:nth-child(2) {
		width:200px;
	}
	div ul {
		width:100%;
		text-align:center;
		list-style:none;
		padding:0;
	}
	
	div ul li {
		padding:10px;
		font-size:1rem;
		background-color:rgba(75,189,217,0.1);
		border-radius:10px;
		margin:10px;
		font-weight:700;
		box-shadow:2px 3px 3px #bbbbbb;
	}
	
	div ul li a {
		text-decoration:none;
		color:#333333;
	}	
</style>
<body>
<div style = "text-align:center; ">
${sessionScope.user.u_name} 님 환영합니다. 
</div>
<div style = "border-collapse:collapse; margin:40px auto;">
	<ul>
		<li><a href="userlist.do">회원 목록</a></li>
		<li><a href="logout.do">로그아웃</a></li>
		<li><a href="boardlist.do">게시판 목록</a>
	</ul>
</div>
</body>
</html>