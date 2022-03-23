package com.lcomputerstudy.testmvc.boarddao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.lcomputerstudy.testmvc.boardvo.Board;
import com.lcomputerstudy.testmvc.vo.*;
import com.lcomputerstudy.testmvc.Boarddatabase.DBConnection;

public class BoardDAO {
	private static BoardDAO dao = null;
	private int executeUpdate;
	
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if(dao == null) {
			dao = new BoardDAO();
		}
		
		return dao;
	}
	
	public List<Board> getBoards(Pagination pagination) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = null;
		int pageNum = pagination.getPageNum();
		
		try {
			conn = DBConnection.getConnection();
			String query =new StringBuilder()
					.append("SELECT 		@ROWNUM := @ROWNUM - 1 AS ROWNUM,\n")
					.append("				ta.*\n")
					.append("FROM 			board ta,\n")
					.append("				(SELECT @rownum := (SELECT	COUNT(*)-?+1 FROM board ta)) tb\n")
					.append("order by		b_group desc, b_order asc\n")
					.append("LIMIT			?, ?\n")
					.toString();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pageNum);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, Pagination.perPage);
			rs = pstmt.executeQuery();
			list = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setRownum(rs.getInt("ROWNUM"));
				board.setB_idx(rs.getInt("b_idx"));
				//board.setB_num(rs.getString("b_num"));
				//board.setB_writer(rs.getString("b_writer"));
				board.setB_hit(rs.getInt("b_hit"));
				board.setB_content(rs.getString("b_content"));
				board.setB_date(rs.getString("b_date"));
				//board.setB_id(rs.getString("b_id"));
				board.setB_title(rs.getString("b_title"));
				
				list.add(board);
				}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public void editBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnection();
			String sql = "UPDATE board SET b_title=?, b_content=? WHERE b_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getB_idx());
			pstmt.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(pstmt != null ) pstmt.close();
				if(conn != null ) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnection();
			String sql = "delete from board where b_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_idx());
			pstmt.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(pstmt != null ) pstmt.close();
				if(conn != null ) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into board(b_title,b_content,b_date,b_hit,b_group,b_order,b_depth) values(?,?,now(),0,0,1,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			//pstmt.setString(4, board.getB_writer());
			//pstmt.setString(4, board.getB_date());
			//pstmt.setString(5, board.getB_hits());
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "update board set b_group=last_insert_id() where b_idx = last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(pstmt != null ) pstmt.close();
				if(conn != null ) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}

	public void replyBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into board(b_title,b_content,b_date,b_group,b_order,b_depth) values(?,?,now(),?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getB_group());
			pstmt.setInt(4, board.getB_order());
			pstmt.setInt(5, board.getB_depth());
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "update board set b_order=b_order+1 where b_group = ? and b_order >= ? and b_idx <> last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_group());
			pstmt.setInt(2, board.getB_order());
			pstmt.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getBoardsCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0 ;
		
		try {
			conn = DBConnection.getConnection();
			String query = "SELECT COUNT(*) count FROM board";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public Board getBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM board where b_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board.setB_content(rs.getString("b_content"));
				board.setB_hit(rs.getInt("b_hit"));
				board.setB_title(rs.getString("b_title"));
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(rs.getInt("b_order"));
				board.setB_depth(rs.getInt("b_depth"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return board;
	}

	
	
//	public board loginBoard(String idx, String pw) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		board board = null;
//		try {
//			conn = DBConnection.getConnection();
//			String sql = "SELECT * FROM board WHERE b_id = ? AND b_pw=?";
//		}
//	}
}


