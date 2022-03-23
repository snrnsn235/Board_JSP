package com.lcomputerstudy.testmvc.boardservice;

import java.util.List;

import com.lcomputerstudy.testmvc.boarddao.*;
//import com.lcomputerstudy.testmvc.boardvo.Board;
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
	
	public static List<Board> getBoards(Pagination pagination) {
		return dao.getBoards(pagination);
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
	public int getBoardsCount() {
		return dao.getBoardsCount();
	}
	
	public void replyBoard(Board board) {
		dao.replyBoard(board);
	}

	public Board getBoard(Board board) {
		return dao.getBoard(board);
	}
	
//	public board loginBoard(String idx, String pw) {
//		return dao.loginBoard(idx, pw);
//	}
}
