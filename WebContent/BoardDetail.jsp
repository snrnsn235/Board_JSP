<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</style>
<body>
<%@ include file="db_connection.jsp" %>
	<h1>게시판 상세페이지</h1>
	<table>
		<%
			String id = request.getParameter("b_idx");
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String query = "select * from board where b_idx=? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String b_idx = rs.getString("b_idx");
				String b_id = rs.getString("b_id");
				//String b_writer = rs.getString("b_writer");
				String b_title = rs.getString("b_title");
				String b_content = rs.getString("b_content");
			%>
			<tr>
				<td>번호</td>
				<td><%=b_idx %></td>
			</tr>
			<tr>
				<td>회원 아이디</td>
				<td><%=b_id %></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><%=b_title %></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><%=b_content %></td>
			</tr>
			
			<tr style = "height:50px;">
				<td style = "boarder:none;">
					<a href = "BoardEdit.jsp?b_idx=<%=b_idx%>" style="width:70%; font-weight:700; background-color:#818181; color:#fff;">수정</a>
				</td>
				<td style = "boarder:none;">
					<a href = "BoardDelete.jsp?b_idx=<%=b_idx%>" style="width:70%; font-weight:700; background-color:red; color:#fff;">삭제</a>
				</td>
			</tr>
				<td style = "boarder:none;">
					<a href ="boardreply.do" style="width:70%; font-weight:700; background-color:green; color:#fff;">답글달기</a>
				</td>
				<td style = "boarder:none;">
					<a href ="boardlist.do" style="width:70%; font-weight:700; background-color:skyblue; color:#fff;">돌아가기</a>
				</td>
			<%
			}
			%>
	</table>
</body>
</html>