<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
	
	div {
		border-collapse:collapse;
		margin:40px auto;
		width : 780px;
	}
	div textarea {
		 height: 200px;
		 padding: 100px 100px;
		 box-sizing: border-box;
		 border: 2px solid #ccc;
		 border-radius: 4px;
		 background-color: #f8f8f8;
		 font-size: 16px;
		 overflow:auto;
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
</head>
<body>
	<h1>게시판 상세페이지</h1>
	<table>
			<tr>
				<td>번호</td>
				<td>${board.b_idx }</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>${board.b_title }</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>${board.b_content }</td>
			</tr>
			
			<tr style = "height:50px;">
				<td style = "boarder:none;">
					<a href = "boardedit.do?b_idx=${board.b_idx}" style="width:70%; font-weight:700; background-color:#818181; color:#fff;">수정</a>
				</td>
				<td style = "boarder:none;">
					<a href = "boarddelete.do?b_idx=${board.b_idx}" style="width:70%; font-weight:700; background-color:red; color:#fff;">삭제</a>
				</td>
			</tr>
			<tr>
				<td style = "boarder:none;">
					<a href ="boardreply.do?b_group=${board.b_group}&b_order=${board.b_order}&b_depth=${board.b_depth}" style="width:70%; font-weight:700; background-color:green; color:#fff;">답글달기</a>
				</td>
				<td style = "boarder:none;">
					<a href ="boardlist.do" style="width:70%; font-weight:700; background-color:skyblue; color:#fff;">돌아가기</a>
				</td>
			</tr>		
	</table>
	<!-- 댓글 작성하기 -->

	<!-- 댓글리스트   -->
	<div id="commentList">
		<form action="commentinsert.do" method="post">
			<input type="hidden" name="b_idx" value="${board.b_idx }">
			<input type="hidden" name="c_idx" value="${comment.c_idx }">
			<textarea name="c_content" placeholder = "여러분의 댓글을 입력해주세요." rows="4" cols="70"></textarea>
			<button type="button" 
					class = "Insert"
					c_idx="${comment.c_idx }"
					b_idx="${board.b_idx }"
					>댓글달기
			</button>
		</form>
		<h3 style = "text-align:center;">댓글목록</h3>
		<table>
			<tr>
				<td colspan="3">댓글 개수 : ${pagination.count }</td>
			</tr>
			<tr>
				<th>번호</th>
				<th>내용</th>
				<th>대댓글, 수정, 삭제</th>
			</tr>
			<c:forEach items="${commentList}" var="comment" varStatus = "status">
				<tr>
					<td>${comment.rownum}</td>
					<td style="text-align: left;">
						<c:forEach var="i" begin="1" end="${comment.c_depth }">
						->
						</c:forEach>
						${comment.c_content }
					</td>
					<td>
						<button type="button" class="commentComment">대댓글</button>
						<button type="button" class="commentEdit">수정</button>
						<button type="button" class="commentDelete">삭제</button>
					</td>
				</tr>
				<!-- 대댓글등록하기 -->
				<tr style="display: none;">
					<td colspan="3">
						<form action="commentReply.do" name = "comment" method="post">
							<textarea name="c_content" rows="2" cols="70"></textarea>
							<button type="button" 
									class="Reply" 
									c_group="${comment.c_group}" 
									c_order="${comment.c_order}" 
									c_depth="${comment.c_depth}"
									b_idx="${board.b_idx }">등록</button>
							<button type="button" class="commentCancel">취소</button>
						</form>
					</td>
				</tr>
				<!-- 수정하기 -->
				<tr style="display: none;">
					<td colspan="3">
						<form action="commentEdit.do" name = "edit" method="post">
								<input type="hidden" name="c_idx" value="${comment.c_idx }">
								<input type="hidden" name="b_idx" value="${board.b_idx }">
								<textarea name="c_content" rows="2" cols="70">${comment.c_content }</textarea>
								<button type="button"
										class = "Edit"
										c_idx="${comment.c_idx }"
										b_idx="${board.b_idx }"
										>수정완료</button>
								<button type="button" class="commentCancel">취소</button>
						</form>
					</td>
				</tr>
				<!-- 삭제 -->
				<tr style="display: none;">
					<td colspan="3">
						<form action="commentDelete.do" name = "delete" method="post">
								<input type="hidden" name="c_idx" value="${comment.c_idx }">
								<input type="hidden" name="b_idx" value="${board.b_idx }">
								<button type="button"
										class = "Delete"
										c_group="${comment.c_group}" 
										c_order="${comment.c_order}" 
										c_depth="${comment.c_depth}"
										c_idx="${comment.c_idx }"
										b_idx="${board.b_idx }">
										삭제</button>
								
								<button type="button" class="commentCancel">취소</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<!-- Pagination -->
		<div style="border-collapse:collapse; margin:40px auto; width : 780px;">
			<ul>
				<c:choose>
						<c:when test="${pagination.startPage-1 != 0}">
							<li style="">
								<a href="boarddetail.do?b_idx=${board.b_idx }&page=${pagination.prevPage}">◀</a>
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
									<a href="boarddetail.do?b_idx=${board.b_idx }&page=${i}">${i}</a>
								</li>
							</c:when>
						</c:choose>
					</c:forEach>
				<c:choose>
						<c:when test = "${pagination.nextPage-1 != pagination.lastPage }">
							<li style="">
								<a href="boarddetail.do?b_idx=${board.b_idx }&page=${pagination.nextPage}">▶</a>
							</li>
						</c:when>
					</c:choose>	
			</ul>
		</div>
	</div>
	<script>
		//1. 댓글달기
		//$(document).on('click', '.btnCommentReg', function () {
			//$('#frmComment').submit();
		//}); //했음
		
		//2. 대댓글창 띄우기
		$(document).on('click', '.commentComment', function () {
			$(this).parent().parent().next().css('display', '');
		}); //했음

		//3. 대댓글 수정하기 창 띄우기
		$(document).on('click','.commentEdit', function() {
			console.log('edit');
			$(this).parent().parent().next().next().css('display', '');
		}); //했음

		//4. 대댓글 지우기 창 띄우기
		$(document).on('click', '.commentDelete', function() {
			$(this).parent().parent().next().next().next().css('display', '');
		});
		
		//5. 댓글달기 취소
		$(document).on('click', '.commentCancel', function () {
			$(this).parent().parent().parent().css('display', 'none');
		}); //했음

		//6. 대댓글등록하기, 했음
		$(document).on('click', '.Reply', function () {
			//$(this).submit();
			
			//let, var은 변수 선언 방식으로 let이 최신 방법, var은 예전에 많이 쓰던 방법
			let cGroup = $(this).attr('c_group');
			let cOrder = $(this).attr('c_order');
			let cDepth = $(this).attr('c_depth');
			let bIdx = $(this).attr('b_idx');
			let cContent = $(this).prev().val();

			$.ajax({
				method: "POST",
				url: "aj-commentReply.do",
				data: { c_group: cGroup, c_order: cOrder, c_depth:cDepth, b_idx:bIdx, c_content:cContent }
			})
			.done(function( msg ) {
			    //alert( "Data Saved: " + msg );
				$('#commentList').html(msg);
			});
			
		}); 
		
		//7. 대댓글 수정하기, 했음
		$(document).on('click', '.Edit', function () {
			//$(this).submit();
			//let, var은 변수 선언 방식으로 let이 최신 방법, var은 예전에 많이 쓰던 방법
			let cIdx = $(this).attr('c_idx');
			let bIdx = $(this).attr('b_idx');
			let cContent = $(this).prev().val();

			$.ajax({
				method: "POST",
				url: "aj-commentEdit.do",
				data: { c_idx:cIdx, b_idx:bIdx, c_content:cContent }
			})
			.done(function( msg ) {
			    //alert( "Data Saved: " + msg );
				$('#commentList').html(msg);
			});
		});
		//8. 대댓글 삭제하기
		$(document).on('click', '.Delete', function() {
			let cIdx = $(this).attr('c_idx');
			let bIdx = $(this).attr('b_idx');

			$.ajax({
				method: "POST",
				url: "aj-commentDelete.do",
				data: {c_idx:cIdx, b_idx:bIdx }
			})
			.done(function( msg ) {
			    //alert( "Data Saved: " + msg );
				$('#commentList').html(msg);
			});
		});
		//9. 댓글달기
		$(document).on('click', '.Insert', function() {
			let bIdx = $(this).attr('b_idx');
			let cIdx = $(this).attr('c_idx');
			let cContent = $(this).prev().val();
			
			$.ajax({
				method: "POST",
				url: "aj-commentInsert.do",
				data: {c_idx:cIdx, b_idx:bIdx, c_content:cContent }
			})
			.done(function(msg) {
				$('#comment').html(msg);
			});
		});
	</script>
</body>
</html>