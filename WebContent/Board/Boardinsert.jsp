<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 추가</title>
</head>
<body>
<div style = "text-align:center; border-collapse:collapse;	width:500px; margin:40px auto;">
<h2>게시판 추가하기</h2>
<form action="boardinsert-process.do" method="post" enctype="multipart/form-data">
	<p> 제목: <input type = "text" name="title" ></p>
		내용 : <textarea name="content" placeholder = "내용을 입력해주세요." rows="4" cols="70" ></textarea>
	<p> 파일 업로드 : <input type = "file" name="filename">
	<p> 파일 업로드 : <input type = "file" name="filename">
	<p> <input type="submit" value="추가하기"></p>
</form>
</div>
</body>
</html>
<!-- enctype속성은 <form>태그의 데이터들을 전송할때 데이터들을 어떤 형식으로 변환할것인지에 대한 값을 지정하는 속성 -->

	
	
	
	
	
	
	
	
	
	
	
	
	
	<!-- multipart/form-data를 사용하면  request.getParameter()로 데이터를 불러올 수 없게 된다.-->
	<!-- multipart/form-data를 사용하면 post방식에서만 사용가능하다 -->
	
	<!-- name=""을 전송할 때  식별자로 사용하고 있는 filename을 식별하기 위해서 1, 2를 붙여서 쓸것이냐 -->
	<!-- 아니면 똑같은 이름으로 해서 배열로 보낼 것인가를 생각해볼 수 있는데 -->
	<!-- 1, 2를 붙이는 것보다 배열로 받을 수 있도록 하는 것이 좋다-->