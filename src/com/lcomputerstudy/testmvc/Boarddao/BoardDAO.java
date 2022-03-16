package com.lcomputerstudy.testmvc.Boarddao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lcomputerstudy.testmvc.database.DBConnection;
import com.lcomputerstudy.testmvc.Boardvo.Board;

public class BoardDAO {
	private static BoardDAO dao = null;
	
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if(dao == null) {
			dao = new BoardDAO();
		}
		
		return dao;
	}
	
	public ArrayList<Board> getBoards() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from board";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			list = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
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
	
	public void insertBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into board(b_title,b_id,b_content,b_writer,b_date,b_hits) values(?,?,?,?,?,?)";
			pstmt.setString(1, board.getB_title());
			pstmt.setString(1, board.getB_id());
			pstmt.setString(parameterIndex, x);
			pstmt.setString(parameterIndex, x);
			pstmt.setString(parameterIndex, x);
			pstmt.setString(parameterIndex, x);
		}
	}
}
