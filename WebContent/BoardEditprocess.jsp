<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정 값 넘겨듣기</title>
</head>
<body>
<%@ include file = "db_connection.jsp" %>
<%
	request.setCharacterEncoding("UTF-8");

	String idx = request.getParameter("b_idx");
	String id = request.getParameter("edit_id");
	//String writer = request.getParameter("edit_writer");
	String content = request.getParameter("edit_content");
	
	PreparedStatement pstmt = null;
	
	try {
		String sql = "UPDATE board SET b_id=?, b_content=? where b_idx=?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, id);
		//pstmt.setString(2, writer);
		pstmt.setString(2, content);
		pstmt.setString(3, idx);
		pstmt.executeUpdate();
	%>
	
	<h3>수정 완료</h3>
	
	<%
		}  catch(SQLException ex) {
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
<a href ="boardlist.do">돌아가기</a>
</body>
</html>