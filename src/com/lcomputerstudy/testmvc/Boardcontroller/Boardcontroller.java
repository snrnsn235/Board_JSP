package com.lcomputerstudy.testmvc.Boardcontroller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lcomputerstudy.testmvc.Boardservice.BoardService;
import com.lcomputerstudy.testmvc.Boardvo.Board;

@WebServlet("*.do")
public class Boardcontroller extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		switch (command) {
		case "/Board-Boardlist.do":
			BoardService boardService = BoardService.getInstance();
			ArrayList<Board> Boardlist = BoardService.getBoards();
			
			view = "Board/Boardlist";
			request.setAttribute("Boardlist", Boardlist);
			break;
		case "Board-Boardinsert.do":
			view = "Board/Boardinsert";
			break;
			
		case "Board-Boardinsert-result.do":
			Board board = new Board();
			board.setB_content(request.getParameter("content"));
			board.setB_id(request.getParameter("id"));
			board.setB_title(request.getParameter("title"));
			board.setB_hits(request.getParameter("hits"));
			board.setB_date(request.getParameter("date"));
			board.setB_writer(request.getParameter("writer"));
			
			boardService = BoardService.getInstance();
			boardService.insertBoard(board);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		rd.forward(request, response);
	}

	
}
