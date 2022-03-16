<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 삭제</title>
</head>
<body>
<%@ include file="db_connection.jsp" %>

<%
	String idx = request.getParameter("b_idx");
	System.out.println(idx);
	
	PreparedStatement pstmt = null;
	
	String query = "delete from board where b_idx=?";
	pstmt = conn.prepareStatement(query);
	pstmt.setString(1, idx);
	pstmt.executeUpdate();
%>

	<h2>삭제 완료</h2>

<script>
setTimeout(function () {
	window.location.href = "Boardlist.jsp";
})
</script>
</body>
</html>