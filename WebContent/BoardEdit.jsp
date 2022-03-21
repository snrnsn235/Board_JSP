<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 수정</title>
</head>
<body>
<%@ include file = "db_connection.jsp" %>
	<h1>게시판 수정</h1>
	<%
		String id = request.getParameter("b_idx");
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "select*from board where b_idx=?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, id);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			String b_idx = rs.getString("b_idx");
			String b_id = rs.getString("b_id");
			//String b_writer = rs.getString("b_writer");
			String b_content = rs.getString("b_content");
	%>
	
		<form action="BoardEditprocess.jsp" name="board" method="post">
			<input type="hidden" name="b_idx" value="<%=b_idx %>">
			<p> 회원 아이디 : <input type = "text" name="edit_id" value=<%=b_id %>>
			<p> 내용 : <input type = "text" name="edit_content" value=<%=b_content %>>
			<p> <input type="submit" value="수정완료"></p>
		</form>
		
	<%
		}
	%>
</body>
</html>