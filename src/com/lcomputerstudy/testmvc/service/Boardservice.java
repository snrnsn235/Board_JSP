package com.lcomputerstudy.testmvc.service;

import java.util.List;

import com.lcomputerstudy.testmvc.dao.*;
import com.lcomputerstudy.testmvc.vo.*;

public class Boardservice {
	private static Boardservice service = null;
	private static BoardDAO dao = null;
	
	private Boardservice() {
		
	}
	
	public static Boardservice getInstance() {
		if(service == null) {
			service = new Boardservice();
			dao = BoardDAO.getInstance();
		}
		return service;
	}
	
	public static List<Board> getBoardlist(Pagination pagination, Search search) {
		return dao.getBoardlist(pagination, search);
	}
	public void insertBoard(Board board) {
		dao.insertBoard(board);
	}
	
	public void editBoard(Board board) {
		dao.editBoard(board);
	}
	
	public void deleteBoard(Board board) {
		dao.deleteBoard(board);
	}
	public int getBoardsCount(Search search) {
		return dao.getBoardsCount(search);
	}
	
	public void replyBoard(Board board) {
		dao.replyBoard(board);
	}

	public Board getBoard(Board board) {
		return dao.getBoard(board);
	}
}
