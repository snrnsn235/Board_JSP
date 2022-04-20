<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%
	request.setCharacterEncoding("euc-kr");
	String name = request.getParameter("name");
	String subject = request.getParameter("subject");
	String filename1 = request.getParameter("filename1");
	String filename2 = request.getParameter("filename2");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>파일 업로드 확인 및 다운로드</title>
</head>
<body>
올린사람 : <%=name %><br>
제목 : <%= subject %><br>
파일명1 : <a href="upload/<%= filename1 %>"><%= filename1 %></a><br>
파일명2 : <a href="upload/<%= filename2 %>"><%= filename2 %></a>
</body>
</html>