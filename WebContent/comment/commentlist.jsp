<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<form action="commentinsert.do" method="post">
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
								<a href="#"
									class="List"
									b_idx="${board.b_idx }"
									page="${pagination.prevPage}">
								◀</a>
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
									<a href="#"
										class="List"
										b_idx="${board.b_idx }"
										page="${i}">
										${i}</a>
								</li>
							</c:when>
						</c:choose>
					</c:forEach>
				<c:choose>
						<c:when test = "${pagination.nextPage-1 != pagination.lastPage }">
							<li style="">
								<a href="#"
									class="List"
									b_idx="${board.b_idx }"
									page="${pagination.nextPage}">
									▶</a>
							</li>
						</c:when>
					</c:choose>	
			</ul>
		</div>