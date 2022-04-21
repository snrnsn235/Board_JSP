package com.lcomputerstudy.testmvc.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lcomputerstudy.testmvc.service.*;
import com.lcomputerstudy.testmvc.vo.*;

////파일업로드
//@MultipartConfig(
////location = "/tmp", 절대경로는 서비스를 실행하는 리눅스와 윈도우에 차이있으므로 
//		//설정하지 않고 자바가 지정된 임시 디렉토리를 사용하도록 하자
//fileSizeThreshold=1024*1024,
//maxFileSize=1024*1024*50, //하나의 파일size 5MB가 최대로 설정됨
//maxRequestSize=1024*1024*50*5 //전체요청으로 50MB를 5개까지. 초과는 불가능하다
//)

@WebServlet("*.do")
public class Controller extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
		    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

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
		
		//Boardservice, Commentservice 클래스에 변수 각각 선언해준것
		Boardservice boardService = null;
		Commentservice commentservice = null;
		
		boolean isRedirected = false;
		//파일 업로드
		String path = "c:\\attaches";
		
		DiskFileUpload upload = new DiskFileUpload();
		upload.setSizeMax(1000000);;
		upload.setSizeThreshold(4096);
		upload.setRepositoryPath(path);
		
		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator params = items.iterator();
				
//	    File attaches = new File(ATTACHES_DIR);
//	    
//        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();//DiskFileItemFactory 업로드된 파일을 저장할 저장소와 관련된 클래스
//        fileItemFactory.setRepository(attaches);//setRepository()는 업로드된 파일을 저장할 위치를 file객체로 지정
//        fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);//setSizeThreshold() 저장소에 임시파일을 생성할 한계 크기를 byte단위로 정한다. 현재 1024 byte로 지정되었음
//        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
//        //ServletFileUpload클래스는 HTTP 요청에 대한 HttpServletRequest 객체로부터 multipart/form-data형식으로 넘어온 HTTP Body 부분을 다루기 쉽게 변환(parse)해주는 역할을 수행합니다.
        
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
			case "/logininsert.do":
				view = "user/insert";
				break;
			case "/insert-process.do":
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("pw"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel") + "-" + request.getParameter("tel2")+"-"+request.getParameter("tel3"));
				user.setU_age(request.getParameter("age"));
				
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
				
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				board.setU_idx(user.getU_idx());
				
				while (params.hasNext()) {
					FileItem item = (FileItem) params.next();
					if(item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString("utf-8");
						out.println(name + "=" + value + "<br>");
					} else {
						String fileFieldName = item.getFieldName();
						String fileName = item.getName();
						String contentType = item.getContentType();
						
						fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
						long fileSize = item.getSize();
						
						File file = new File(path+"/"+fileName);
						try {
							item.write(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						out.println("--------------------------<br>");
						out.println("요청 파라미터 이름 : " + fileFieldName + "<br>");
						out.println("저장 파일 이름 : " + fileName + "<br>");
						out.println("파일 콘텐츠 유형 : " + contentType + "<br>");
						out.println("파일 크기 : " + fileSize);
					}
				}
//				try {
//		            List<FileItem> items = fileUpload.parseRequest(request);
//		            for (FileItem item : items) {
//		                if (item.isFormField()) {
//		                    System.out.printf("파라미터 명 : %s, 파라미터 값 :  %s \n", item.getFieldName(), item.getString(CHARSET));
//		                } else {
//		                    System.out.printf("파라미터 명 : %s, 파일 명 : %s,  파일 크기 : %s bytes \n", item.getFieldName(),
//		                            item.getName(), item.getSize());
//		                    if (item.getSize() > 0) {
//		                        String separator = File.separator;
//		                        int index =  item.getName().lastIndexOf(separator);
//		                        String fileName = item.getName().substring(index  + 1);
//		                        File uploadFile = new File(ATTACHES_DIR +  separator + fileName);
//		                        item.write(uploadFile);
//		                    }
//		                }
//		            }
//		            out.println("<h1>파일 업로드 완료</h1>");
//		        } catch (Exception e) {
//		            // 파일 업로드 처리 중 오류가 발생하는 경우
//		            e.printStackTrace();
//		            out.println("<h1>파일 업로드 중 오류가  발생하였습니다.</h1>");
//		        }
				//parameter가 전달된 녀석의 문자를 얻어오는 것이라면
				//part는 전달한 name값을 가지고 특정 파트를 읽는 것이다.
				//저장경로는 상대경로를 쓸 수 없다 절대경로여야한다
				//request.getServletContext() 상대경로 넘겨주면 실제 물리경로를 얻어주는 녀석
				//request.getParts()메서드를 통해 여러개의 Part를 Collection에 담아 리턴합니다.
				//String 클래스는 불변이지만 StringBuilder는 가변
//				Collection<Part> parts = request.getParts();
//				StringBuilder builder = new StringBuilder();
//				for(Part p : parts) {
//					if(!p.getName().equals("filename")) continue;//여기서 getName()은 밑에 줄에 getPart("filename")을 뜻한다.
//					if(p.getSize() == 0) continue; //비어있는 데이터 그럴 경우 continue를 통해서 건너띄게 만들었음
//					
//					javax.servlet.http.Part filePart = request.getPart("filename");
//					Part filePart = p;
//					String fileName = filePart.getSubmittedFileName();
//					builder.append(fileName);
//					builder.append(",");//첫번째 파일명 들어가고 그다음에 구분자 들어가고
//					
//					InputStream fis = filePart.getInputStream();
//					String realPath = request.getServletContext().getRealPath("/member/upload");
//					//getServletContext() : 웹 어플리케이션이 설치되어 있는 경로를 리턴해줌
//					//getRealPath("/member/upload"); : ServletContext()의 getRealPath는 웹어플리케이션이 실행된 곳, 즉 설치된 곳의 경로를 찾음
//					System.out.println(realPath);
//					
//					File path = new File(realPath);
//					if(!path.exists())
//						path.mkdirs();
//					
//					String filePath = realPath + File.separator + fileName;
//					FileOutputStream fos = new FileOutputStream(filePath);
//					
//					byte[] buf = new byte[1024];
//					int size=0;
//					while((size=fis.read(buf)) != -1)
//					//데이트를 byte단위로 읽어주며, 스트림 끝에 도달한 경우 -1라는 정수형을 반환한다.
//						fos.write(buf, 0, size); 
//					fos.close();
//					fis.close();
//				}
//				
//				builder.delete(builder.length()-1, builder.length());
//				board.setFilename(builder.toString());
				boardService = Boardservice.getInstance() ;
				boardService.insertBoard(board);
				
				view = "board/boardinsert-result";
				break;
				
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