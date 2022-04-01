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
	public void insertComment(Comment comment) {
		dao.insertComment(comment);
	}
	
	public Comment getComment(Comment comment) {
		return dao.getComment(comment);
	}
	
	public int getCommentsCount(Board board) {
		return dao.getCommentsCount(board);
	}
	public void replyComment(Comment comment) {
		dao.replyComment(comment);
	}

}
