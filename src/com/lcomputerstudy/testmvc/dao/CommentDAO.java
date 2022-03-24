package com.lcomputerstudy.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lcomputerstudy.testmvc.database.DBConnection;
import com.lcomputerstudy.testmvc.vo.*;

public class CommentDAO {
	private static CommentDAO dao = null;
	private int executeUpdate;
	
	private CommentDAO() {
		
		
	}
	
	public static CommentDAO getInstance() {
		if(dao == null) {
			dao = new CommentDAO();
		}
		
		return dao;
	}
	
	public void insertComment(Comment comment) {
		Connection conn = null;
		PreparedStatement  pstmt = null;
		
			try {
				conn = DBConnection.getConnection();
				String sql = "insert into comment(c_content,c_date) values(?,now())";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, comment.getC_content());
				pstmt.executeUpdate();
				pstmt.close();
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
	
	public Comment getComment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from comment where c_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, comment.getC_idx());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				comment.setC_content(rs.getString("c_content"));
			} 
		}catch(Exception e) {
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
		return comment;
	}
}

