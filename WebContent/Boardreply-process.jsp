<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 값 넘겨주기</title>
</head>
<body>
<%@ include file = "db_connection.jsp" %>

<%
	request.setCharacterEncoding("UTF-8");
	String content = request.getParameter("edit_content");
	String title = request.getParameter("edit_title");
	PreparedStatement pstmt = null;
	
	try {
		String sql = "insert into board(b_content, b_title, b_date, b_group, b_order, b_depth) values(?,?, now(),)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,title);
		pstmt.setString(2, content);
		pstmt.executeUpdate();
	} catch (SQLException ex) {
		System.out.println("SQLException : " + ex.getMessage());
	} finally {
		if(pstmt != null) {
			pstmt.close();
		}
		if(conn != null) {
			conn.close();
		}
	}
%>
<h3>작성 완료</h3>
<a href = "boardlist.do">돌아가기</a>
</body>
</html>