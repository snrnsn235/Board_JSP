package com.lcomputerstudy.testmvc.Boardservice;

import java.util.ArrayList;
import com.lcomputerstudy.testmvc.Boarddao.BoardDAO;
import com.lcomputerstudy.testmvc.Boardvo.Board;

public class BoardService {
	private static BoardService service = null;
	private static BoardDAO dao = null;
	
	private BoardService() {
		
	}
	
	public static BoardService getInstance() {
		if(service == null) {
			service = new BoardService();
			dao = BoardDAO.getInstance();
		}
		return service;
	}
	
	public static ArrayList<Board> getBoards() {
		return dao.getBoards();
	}
	
	public void insertBoard(Board board) {
		BoardDAO.insertBoard(board);
	}
}
