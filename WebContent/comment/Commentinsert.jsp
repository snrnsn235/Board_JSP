<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="commentinsert.do" method="post">
			<input type="hidden" name="b_idx" value="${board.b_idx }">
			<input type="hidden" name="c_idx" value="${comment.c_idx }">
			<textarea id="cmtCnt" name="c_content" placeholder = "여러분의 댓글을 입력해주세요." rows="4" cols="70"></textarea>
			<button type="button" 
					class = "Insert"
					c_idx="${comment.c_idx }"
					b_idx="${board.b_idx }"
					>댓글달기
			</button>
</form> 