<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
</head>
<style>
	table {
		border-cooapse:collapse;
		}
	table tr th {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width : 200px;
		text-align:center;
	}
</style>
<body>
<%@ include file = "db_connection.jsp" %>
	<h1>게시판 목록</h1>
	<table>
		<tr>
			<th>ID</th>
			<th>이름</th>
			<th>내용</th>
		</tr>
		<%
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String query = "select * from board";
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String b_id = rs.getString("b_id");
				String b_writer = rs.getString("b_writer");
				String b_content = rs.getString("b_content");
				
		%>
		<tr>
			<td><a href = "BoardDetail.jsp?b_id=<%=b_id%>"><%=b_id %></a></td>
			<td><%=b_writer %></td>
			<td><%=b_content %></td>
		</tr>
		
		<%
			}
			rs.close();
			pstmt.close();
			conn.close();
		%>
	</table>
</body>
</html>