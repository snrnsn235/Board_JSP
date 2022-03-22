package com.lcomputerstudy.testmvc.controller;

import java.io.IOException;
//import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lcomputerstudy.testmvc.boardservice.Boardservice;
//import com.lcomputerstudy.testmvc.boardvo.Board;
//import com.lcomputerstudy.testmvc.service.UserService;
//import com.lcomputerstudy.testmvc.boardvo.Pagination;
import com.lcomputerstudy.testmvc.vo.*;
//import com.lcomputerstudy.testmvc.vo.User;

@WebServlet("*.do")
public class Controller extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		
		int page = 1;
//		int userCount = 0;
		int boardCount = 0;
		//String pw = null;
		//String idx = null;
		HttpSession session = null;
		command = checkSession(request, response, command);
		Board board = null;
		//User user = null;
		Boardservice boardService = null;
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		switch (command) {
			//user
//			case "/user-list.do":
//				String reqPage = request.getParameter("page");
//				if(reqPage != null && !(reqPage.equals(""))) {
//					page = Integer.parseInt(reqPage);
//				}
//				
//				UserService userService = UserService.getInstance();
//				userCount = userService.getUsersCount();
//				Pagination pagination = new Pagination();
//				pagination.setPage(page);
//				pagination.setCount(userCount);
//				pagination.init();
//				List<User> list = userService.getUsers(pagination);
//				
//				view = "user/list";
//				request.setAttribute("list", list);
//				request.setAttribute("pagination", pagination);
//				break;
//				
//			case "/user-insert.do":
//				view = "user/insert";
//				break;
//			case "/user-insert-process.do":
//				User user = new User();
//				user.setU_id(request.getParameter("id"));
//				user.setU_pw(request.getParameter("pw"));
//				user.setU_name(request.getParameter("name"));
//				user.setU_tel(request.getParameter("tel") + "-" + request.getParameter("tel2")+"-"+request.getParameter("tel3"));
//				user.setU_age(request.getParameter("age"));
//				
//				userService = UserService.getInstance();
//				userService.insertUser(user);
//				
//				view = "user/insert-result";
//				break;		
//			case "/user-login.do":
//				view = "user/login";
//				break;
//			case "/user-login-process.do":
//				idx = request.getParameter("login_id");
//				pw = request.getParameter("login_password");
//				
//				userService = UserService.getInstance();
//				user = userService.loginUser(idx, pw);
//				
//				if(user != null) {
//					session = request.getSession();
//					session.setAttribute("user", user);
//					
//					view = "user/login-result";
//				} else {
//					view = "user/login-fail";
//				}
//				break;
//			case "/logout.do":
//				session = request.getSession();
//				session.invalidate();
//				view = "user/login";
//				break;
//				
//			case "/access-denied.do":
//				view = "user/access-denied";
//				break;
//			
			//Board
			//게시판 리스트
			case "/boardlist.do":
				String reqPage1 = request.getParameter("page");
				if (reqPage1 != null  && !(reqPage1.equals("")) && !(reqPage1.equals("0"))) {
					page = Integer.parseInt(reqPage1);
				}
				boardService = Boardservice.getInstance();
				boardCount = boardService.getBoardsCount();
				Pagination pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(boardCount);
				pagination.init();
				List<Board> Boardlist = Boardservice.getBoards(pagination);
								
				view = "board/boardlist";
				request.setAttribute("boardlist", Boardlist);
				request.setAttribute("pagination", pagination);
				break;
			//상세페이지
			case "/boarddetail.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = Boardservice.getInstance();
				board = boardService.getBoard(board);
				view = "BoardDetail";
				request.setAttribute("board", board);
				break;
			//수정	
			case "/boardedit.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = Boardservice.getInstance();
				board = boardService.getBoard(board);
				view = "BoardEdit";
				request.setAttribute("board", board);
				break;
			
			case "/boardedit-process.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				
				boardService = Boardservice.getInstance();
				boardService.editBoard(board);
				
				view = "BoardEditprocess";
				request.setAttribute("board", board);
				break;
			//새로 만들기	
			case "/boardinsert.do":
				view = "board/boardinsert";
				break;
				
			case "/boardinsert-process.do":
				/*session = request.getSession();
				user = (User)session.getAttribute("user");
				board.setU_idx(user.getU_idx());*/
				
				board.setB_content(request.getParameter("b_content"));
				board.setB_title(request.getParameter("b_title"));
				//board.setB_hits(request.getParameter("hits"));
				//board.setB_date(request.getParameter("date"));
				//board.setB_writer(request.getParameter("writer"));
				
				boardService = Boardservice.getInstance();
				boardService.insertBoard(board);
				
				view = "board/boardinsert-result";
				break;
			
			//답글
			case "/boardreply.do":
				view = "Boardreply";
				break;
			case "/boardreply-process.do":
				board = new Board();
				board.setB_content(request.getParameter("content"));
				board.setB_title(request.getParameter("title"));
				board.setB_group(Integer.parseInt(request.getParameter("b_group")));
				board.setB_order(Integer.parseInt(request.getParameter("b_order")));
				board.setB_depth(Integer.parseInt(request.getParameter("b_depth")));
				boardService = Boardservice.getInstance();
				boardService.replyBoard(board);
				view = "Boardreply-process";
				break;
			
			case "/boarddelete.do":
				view = "BoardDelete";
				break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		rd.forward(request, response);
	}

	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		String[] authList = {
				"/user-list.do"
				,"/user-insert.do"
				,"/user-insert-process.do"
				,"/user-detail.do"
				,"/user-edit.do"
				,"/user-edit-process.do"
				,"/logout.do"
			};
		
		for (String item : authList) {
			if (item.equals(command)) {
				if (session.getAttribute("user") == null) {
					command = "/access-denied.do";
				}
			}
		}
		return command;
	}
}
