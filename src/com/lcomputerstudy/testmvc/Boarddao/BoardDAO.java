package com.lcomputerstudy.testmvc.boarddao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lcomputerstudy.testmvc.Boarddatabase.DBConnection;
import com.lcomputerstudy.testmvc.boardvo.board;

public class boardDAO {
	private static boardDAO dao = null;
	
	private boardDAO() {
		
	}
	
	public static boardDAO getInstance() {
		if(dao == null) {
			dao = new boardDAO();
		}
		
		return dao;
	}
	
	public ArrayList<board> getBoards(int page) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<board> list = null;
		int pageNum = ()
		try {
			conn = DBConnection.getConnection();
			String query = "select * from board limit ?, 3";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			list = new ArrayList<board>();
			
			while(rs.next()) {
				board board = new board();
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_num(rs.getString("b_num"));
				board.setB_writer(rs.getString("b_writer"));
				board.setB_hits(rs.getString("b_hits"));
				board.setB_content(rs.getString("b_content"));
				board.setB_date(rs.getString("b_date"));
				board.setB_id(rs.getString("b_id"));
				board.setB_title(rs.getString("b_title"));
				list.add(board);
			}
		} catch(Exception e) {
			
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public void insertBoard(board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into board(b_title,b_id,b_content,b_writer,b_date,b_hits) values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_id());
			pstmt.setString(3, board.getB_content());
			pstmt.setString(4, board.getB_writer());
			pstmt.setString(5, board.getB_date());
			pstmt.setString(6, board.getB_hits());
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

	public int getBoardsCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0 ;
		
		try {
			conn = DBConnection.getConnection();
			String query = "SELECT COUNT(*) count FROM user";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch(Exception e) {
			
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
}


