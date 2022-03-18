package com.lcomputerstudy.testmvc.boardservice;

import java.util.List;

import com.lcomputerstudy.testmvc.boarddao.*;
import com.lcomputerstudy.testmvc.boardvo.board;
import com.lcomputerstudy.testmvc.boardvo.Pagination;

public class boardservice {
	private static boardservice service = null;
	private static boardDAO dao = null;
	
	private boardservice() {
		
	}
	
	public static boardservice getInstance() {
		if(service == null) {
			service = new boardservice();
			dao = boardDAO.getInstance();
		}
		return service;
	}
	
	public static List<board> getBoards(Pagination pagination) {
		return dao.getBoards(pagination);
	}
	
	public void insertBoard(board board) {
		dao.insertBoard(board);
	}
	
	public int getBoardsCount() {
		return dao.getBoardsCount();
	}
	
//	public board loginBoard(String idx, String pw) {
//		return dao.loginBoard(idx, pw);
//	}
}
