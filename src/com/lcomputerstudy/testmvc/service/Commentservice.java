package com.lcomputerstudy.testmvc.service;

import java.util.List;

import com.lcomputerstudy.testmvc.dao.CommentDAO;
import com.lcomputerstudy.testmvc.vo.*;

public class Commentservice {
	private static Commentservice service = null;
	private static CommentDAO dao = null;
	
	private Commentservice() {
		
	}
	
	public static Commentservice getInstance() {
		if(service==null) {
			service = new Commentservice();
			dao = CommentDAO.getInstance();
		}
		return service;
	}
	
	public static List<Comment> getComments(Pagination pagination, Board board) {
		return dao.getComments(pagination, board);
	}
	//대댓글 추가
	public void insertComment(Comment comment) {
		dao.insertComment(comment);
	}
	
	public Comment getComment(Comment comment) {
		return dao.getComment(comment);
	}
	
	public int getCommentsCount(Board board) {
		return dao.getCommentsCount(board);
	}
	//대댓글에 답글달기
	public void replyComment(Comment comment) {
		dao.replyComment(comment);
	}
	//대댓글 삭제하기
	public void deleteComment(Comment comment) {
		dao.deleteComment(comment);
	}
	
	public void editComment(Comment comment) {
		dao.editComment(comment);
	}

}
