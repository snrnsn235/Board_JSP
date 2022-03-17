package com.lcomputerstudy.testmvc.boardservice;

import java.util.ArrayList;

import com.lcomputerstudy.testmvc.boarddao.boardDAO;
import com.lcomputerstudy.testmvc.boardvo.board;

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
	
	public static ArrayList<board> getBoards(int page) {
		return dao.getBoards(page);
	}
	
	public void insertBoard(board board) {
		dao.insertBoard(board);
	}
	
	public int getBoardsCount() {
		return dao.getBoardsCount();
	}
}
