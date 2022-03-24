package com.lcomputerstudy.testmvc.service;

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
	
	public void insertComment(Comment comment) {
		dao.insertComment(comment);
	}
	
	public Comment getComment(Comment comment) {
		return dao.getComment(comment);
	}
}
