package com.lcomputerstudy.testmvc.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.lcomputerstudy.testmvc.service.*;
import com.lcomputerstudy.testmvc.vo.*;

@WebServlet("*.do")
public class Controller extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private static final String CHARSET = "utf-8";
    private static final String ATTACHES_DIR = "C:\\attaches";
    private static final int LIMIT_SIZE_BYTES = 1024 * 1024;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
		    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>Multipart Test</TITLE></HEAD><BODY>");
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		
		//page
		int page = 1;
		
		//user, board, comment 총 개수
		int userCount = 0;
		int boardCount = 0;
		int commentCount = 0;
		
		//로그인
		String pw = null;
		String idx = null;
		
		//검색
		Search search = null;
		
		//login과 연계
		HttpSession session = null;
		command = checkSession(request, response, command);
		
		//User, Board, Comment, Pagination 클래스에 변수를 각각 선언해준것
		User user = null;
		Board board = null;
		Comment comment = null;
		Pagination pagination = null;
		BoardFile boardfile = null;
		//Boardservice, Commentservice 클래스에 변수 각각 선언해준것
		Boardservice boardService = null;
		Commentservice commentservice = null;
		
		boolean isRedirected = false;
        
		switch (command) {
			//user
		//회원리스트
			case "/userlist.do":
				String reqPage = request.getParameter("page");
				if(reqPage != null && !(reqPage.equals(""))) {
					page = Integer.parseInt(reqPage);
				}
				
				UserService userService = UserService.getInstance();
				userCount = userService.getUsersCount();
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(userCount);
				pagination.init();
				List<User> list = userService.getUsers(pagination);
				
				view = "user/list";
				request.setAttribute("list", list);
				request.setAttribute("pagination", pagination);
				break;
				//회원추가
			case "/insert.do":
				view = "user/insert";
				break;
			case "/insert-process.do":
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2")+"-"+request.getParameter("tel3"));
				user.setU_age(request.getParameter("age"));
				user.setU_level(request.getParameter("level"));
				
				userService = UserService.getInstance();
				userService.insertUser(user);
				
				view = "user/insert-result";
				break;	
				//고객 정보
			case "/userdetail.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				user = userService.getUsers(user);
				view = "userDetail";
				request.setAttribute("user", user);
				break;
				
			case "/userEdit.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				user = userService.getUsers(user);				
				view = "userEdit";
				request.setAttribute("user", user);
				break;
				
			case "/userEdit-process.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				user.setU_id(request.getParameter("u_id"));
				user.setU_pw(request.getParameter("u_pw"));
				user.setU_name(request.getParameter("u_name"));
				user.setU_tel(request.getParameter("u_tel1") + "-" + request.getParameter("u_tel2")+"-"+request.getParameter("u_tel3"));
				user.setU_age(request.getParameter("u_age"));
				
				userService = UserService.getInstance();
				userService.editUser(user);
				
				view = "editProcess";
				request.setAttribute("user", user);
				break;
				
				//고객정보삭제
			case "/userDelete.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
								
				userService = UserService.getInstance();
				userService.deleteUser(user);
				
				view = "BoardDelete";
				break;
				//회원 로그인
			case "/userlogin.do":
				view = "user/login";
				break;
			case "/login-process.do":
				idx = request.getParameter("login_id");
				pw = request.getParameter("login_password");
				
				userService = UserService.getInstance();
				user = userService.loginUser(idx, pw);
				
				if(user != null) {
					session = request.getSession();
					session.setAttribute("user", user);
					
					view = "user/login-result";
				} else {
					view = "user/login-fail";
				}
				break;
				//로그아웃
			case "/logout.do":
				session = request.getSession();
				session.invalidate();
				view = "user/login";
				break;
				
			case "/access-denied.do":
				view = "user/access-denied";
				break;
			
			//Board
			//게시판 리스트
			case "/boardlist.do":
				//검색창
				search = new Search();
				search.setField(request.getParameter("field"));//select의 option value에 들어감
				search.setValue(request.getParameter("value"));//검색버튼을 누르면 field, query에 값을 넣어줌
				//파일 업로드
				
				//페이지, 리스트
				String reqPage1 = request.getParameter("page");
				if (reqPage1 != null  && !(reqPage1.equals("")) && !(reqPage1.equals("0"))) {
					page = Integer.parseInt(reqPage1);
				}
				//전체 게시판 개수 
				boardService = Boardservice.getInstance();
				boardCount = boardService.getBoardsCount(search); 
				//pagination
				pagination = new Pagination(); 
				pagination.setPage(page);
				pagination.setCount(boardCount);
				pagination.setSearch(search);
				pagination.init();
				List<Board> Boardlist = Boardservice.getBoardlist(pagination, search);
								
				view = "board/boardlist";
				request.setAttribute("board", board);
				request.setAttribute("user", user);
				request.setAttribute("boardlist", Boardlist);
				request.setAttribute("pagination", pagination);
				request.setAttribute("search", search);
				break;
				
			//상세페이지 칸
			case "/boarddetail.do":
				session = request.getSession();
				//user = (User)session.getAttribute("user");
				board = new Board();
				
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = Boardservice.getInstance();
				board = boardService.getBoard(board);
						
			//	상세페이지 - 댓글 리스트
				String reqPage2 = request.getParameter("page");
				if (reqPage2 != null  && !(reqPage2.equals("")) && !(reqPage2.equals("0"))) {
					page = Integer.parseInt(reqPage2);
				}
				commentservice = Commentservice.getInstance();
				commentCount = commentservice.getCommentsCount(board);
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(commentCount);
				pagination.init();
				List<Comment> commentlist = Commentservice.getComments(pagination, board);
				board.setCommentList(commentlist);
						
				view = "BoardDetail";
				request.setAttribute("user", user);
				request.setAttribute("board", board);
				request.setAttribute("commentList", commentlist);
				request.setAttribute("pagination", pagination);
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
			//삭제하기
			case "/boarddelete.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
								
				boardService = Boardservice.getInstance();
				boardService.deleteBoard(board);
				
				view = "BoardDelete";
				break;
			//새로 생성하기
			case "/boardinsert.do":
				view = "board/boardinsert";
				break;
			
			//새로 생성하기 위한 과정들
			case "/boardinsert-process.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board = new Board();
				board.setU_idx(user.getU_idx());
				File attachesDir = new File(ATTACHES_DIR);
				 
		        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		        fileItemFactory.setRepository(attachesDir);
		        fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);
		        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
				
		        try {
		        	List<FileItem> items = upload.parseRequest(request);
		        	//다중 업로드를 위한 List
		        	List<BoardFile> bfList = new ArrayList<BoardFile>();
			        for (FileItem item : items) {
			            if (item.isFormField()) {
			            	switch (item.getFieldName()) {
			            	case "title":
			            		board.setB_title(item.getString(CHARSET));
			            		System.out.printf("파라미터 명 : %s, 파라미터 값 : %s \n", item.getFieldName(), item.getString(CHARSET));
			            		break;
			            	case "content":
			            		board.setB_content(item.getString(CHARSET));
			            		System.out.printf("파라미터 명 : %s, 파라미터 값 : %s \n", item.getFieldName(), item.getString(CHARSET));
			            		break;
			            	}
			             } else if (item.getSize() > 0){		 
			            	  boardfile = new BoardFile();
			            	 
		                      String separator = File.separator;
		                      int index = item.getName().lastIndexOf(separator);
		                      String fileName = item.getName().substring(index  + 1);
		                      System.out.printf("파라미터 명 : %s, 파일 명 : %s, 파일 크기 : %s bytes \n", item.getFieldName(), item.getName(), item.getSize());
		                      board.setFilename(item.getName());
		                      File uploadFile = new File(ATTACHES_DIR +  separator + fileName);
		                      item.write(uploadFile);
		                      
		                      boardfile.setFileName(item.getName());
//			                  boardfile.setOrgFileName(item.getName());
		                      bfList.add(boardfile);
		                }
			            board.setFileList(bfList);
			            System.out.println(bfList);
			            System.out.println(board);
			        }
		        } catch (Exception e) {
			          // 파일 업로드 처리 중 오류가 발생하는 경우
			          e.printStackTrace();
			          out.println("<h1>파일 업로드 중 오류가  발생하였습니다.</h1>");
			    }
		       
				boardService = Boardservice.getInstance() ;
				boardService.insertBoard(board);
			    
				view = "board/boardinsert-result";
			    break;
	    
			  //MultipartRequest 구문
//				MultipartRequest multipartRequest = new MultipartRequest(request, ATTACHES_DIR, LIMIT_SIZE_BYTES, "UTF-8", new DefaultFileRenamePolicy() );
//				board.setB_content(multipartRequest.getParameter("content"));
//				board.setB_title(multipartRequest.getParameter("title"));
//				board.setFilename(multipartRequest.getFilesystemName("filename"));
//				parameter가 전달된 녀석의 문자를 얻어오는 것이라면
//				part는 전달한 name값을 가지고 특정 파트를 읽는 것이다.
//				저장경로는 상대경로를 쓸 수 없다 절대경로여야한다
//				request.getServletContext() 상대경로 넘겨주면 실제 물리경로를 얻어주는 녀석
//				request.getParts()메서드를 통해 여러개의 Part를 Collection에 담아 리턴합니다.
//				String 클래스는 불변이지만 StringBuilder는 가변
				
			//답글달기
			case "/boardreply.do":
				board = new Board();
				board.setB_group(Integer.parseInt(request.getParameter("b_group")));
				board.setB_order(Integer.parseInt(request.getParameter("b_order")));
				board.setB_depth(Integer.parseInt(request.getParameter("b_depth")));
				view = "Boardreply";
				request.setAttribute("board", board);
				break;
				
			case "/boardreply-process.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board = new Board();
				user = new User();
				board.setB_content(request.getParameter("content"));
				board.setB_title(request.getParameter("title"));
				board.setB_date(request.getParameter("date"));
				board.setB_group(Integer.parseInt(request.getParameter("b_group")));
				board.setB_order(Integer.parseInt(request.getParameter("b_order"))+1);
				board.setB_depth(Integer.parseInt(request.getParameter("b_depth"))+1);
				board.setU_idx(user.getU_idx());
				boardService = Boardservice.getInstance();
				boardService.replyBoard(board);
				view = "Boardreply-process";
				break;
				
				//댓글달기					
			case "/commentinsert.do":
				comment = new Comment();
				comment.setC_content(request.getParameter("c_content"));
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				
				commentservice = Commentservice.getInstance();
				commentservice.insertComment(comment);
				isRedirected = true;
				view = "boarddetail.do?b_idx="+comment.getB_idx();
				break;
				
				//대댓글등록
			case "/commentReply.do":
				//session은 로그인 할때 사용하는 것이므로 필요없음
//				session = request.getSession();
//				board = (Board)session.getAttribute("board");
				//board = new Board();
				comment = new Comment();
				
				comment.setC_content(request.getParameter("c_content"));
				comment.setC_group(Integer.parseInt(request.getParameter("c_group")));
				comment.setC_order(Integer.parseInt(request.getParameter("c_order"))+1);
				comment.setC_depth(Integer.parseInt(request.getParameter("c_depth"))+1);
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				
				commentservice = Commentservice.getInstance();
				commentservice.replyComment(comment);
				isRedirected = true;
				view = "boarddetail.do?b_idx="+comment.getB_idx();
				request.setAttribute("comment", comment);
				break;
				//댓글수정
			case "/commentEdit.do":
				comment = new Comment();
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				commentservice = Commentservice.getInstance();
				comment = commentservice.getComment(comment);
				
				//commentEdit-process.do
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				comment.setC_content(request.getParameter("c_content"));
				commentservice = Commentservice.getInstance();
				commentservice.editComment(comment);
				isRedirected = true;
				view = "boarddetail.do?b_idx="+comment.getB_idx();
				request.setAttribute("comment", comment);
				break;
				
				//댓글삭제
			case "/commentDelete.do":
				comment = new Comment();
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				
				commentservice = Commentservice.getInstance();
				commentservice.deleteComment(comment);
				isRedirected = true;
				view = "boarddetail.do?b_idx="+comment.getB_idx();
				break;
			
				//ajax
			case "/aj-commentReply.do":
				comment = new Comment();
				
				comment.setC_content(request.getParameter("c_content"));
				comment.setC_group(Integer.parseInt(request.getParameter("c_group")));
				comment.setC_order(Integer.parseInt(request.getParameter("c_order"))+1);
				comment.setC_depth(Integer.parseInt(request.getParameter("c_depth"))+1);
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				
				commentservice = Commentservice.getInstance();
				commentservice.replyComment(comment);
				commentCount = commentservice.getCommentsCount(comment);
				
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(commentCount);
				pagination.init();
				List<Comment> commentList = Commentservice.getCommentss(pagination, comment);
				view = "comment/commentlist";
				request.setAttribute("comment", comment);
				request.setAttribute("commentList", commentList);
				request.setAttribute("pagination", pagination);
				break;
				
			case "/aj-commentEdit.do":
				comment = new Comment();
				
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				commentservice = Commentservice.getInstance();
				comment = commentservice.getComment(comment);
				//대댓글 갯수
				commentCount = commentservice.getCommentsCount(comment);
				//commentEdit-process.do
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				comment.setC_content(request.getParameter("c_content"));
				commentservice = Commentservice.getInstance();
				commentservice.editComment(comment);
				
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(commentCount);
				pagination.init();
				List<Comment> commentList1 = Commentservice.getCommentss(pagination, comment);
				view = "comment/commentlist";
				request.setAttribute("comment", comment);
				request.setAttribute("commentList", commentList1);
				request.setAttribute("pagination", pagination);
				break; 
		
			case "/aj-commentDelete.do":
				comment = new Comment();
				
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				commentservice = Commentservice.getInstance();
				commentservice.deleteComment(comment);
				//댓글 개수
				commentCount = commentservice.getCommentsCount(comment);
				//Pagination, list
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(commentCount);
				pagination.init();
				List<Comment> commentList2 = Commentservice.getCommentss(pagination, comment);
				view = "comment/commentlist";
				request.setAttribute("comment", comment);
				request.setAttribute("commentList", commentList2);
				request.setAttribute("pagination", pagination);
				break;
				
			case "/aj-commentInsert.do":
				comment = new Comment();
				
				comment.setC_content(request.getParameter("c_content"));
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				commentservice = Commentservice.getInstance();
				commentservice.insertComment(comment);
				
				//댓글 개수
				commentCount = commentservice.getCommentsCount(comment);
				
				//Pagination, list, view
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(commentCount);
				pagination.init();
				List<Comment> commentList3 = Commentservice.getCommentss(pagination, comment);
				view="comment/commentlist";
				request.setAttribute("comment", comment);
				request.setAttribute("commentList", commentList3);
				request.setAttribute("pagination", pagination);
				break;
				
			//관리자 권한 추가, 제거하기
			case "/aj-levelUpdate.do":
				user = new User();
				
				user.setU_level(request.getParameter("u_level"));
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				userService.levelUpdate(user);
				userCount = userService.getUsersCount();
				
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(userCount);
				pagination.init();
				
				List<User> list1 = userService.getUsers(pagination);
				view="user/list";
				request.setAttribute("user", user);
				request.setAttribute("list", list1);
				request.setAttribute("pagination", pagination);
				break;
				
			case "/aj-commentList.do":
				comment = new Comment();
				board = new Board();
				
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				String reqPage3 = request.getParameter("page");
				if (reqPage3 != null  && !(reqPage3.equals("")) && !(reqPage3.equals("0"))) {
					page = Integer.parseInt(reqPage3);
				}
				//댓글 개수
				commentservice = Commentservice.getInstance();
				commentCount = commentservice.getCommentsCount(comment);
				
				//Pagination, list, view
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(commentCount);
				pagination.init();
				List<Comment> commentList4 = Commentservice.getCommentss(pagination, comment);
				view="comment/commentlist";
				request.setAttribute("board", board);
				request.setAttribute("comment", comment);
				request.setAttribute("commentList", commentList4);
				request.setAttribute("pagination", pagination);
				break;
				
		}
		
		if (isRedirected) {
			response.sendRedirect(view);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
			rd.forward(request, response);
		}
		
	}


	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		String[] authList = {
				"/logininsert.do"
				,"/insert-process.do"
				,"/userdetail.do"
				,"/userEdit.do"
				,"/userEdit-process.do"
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