package com.lcomputerstudy.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lcomputerstudy.testmvc.database.DBConnection;
import com.lcomputerstudy.testmvc.vo.*;

public class CommentDAO {
	private static CommentDAO dao = null;

	private CommentDAO() {
	}
	
	public static CommentDAO getInstance() {
		if(dao == null) {
			dao = new CommentDAO();
		}
		
		return dao;
	}
	public List<Comment> getComments(Pagination pagination, Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Comment> list = null;
		int pageNum = pagination.getPageNum();
		
		try {
			conn=DBConnection.getConnection();
			String query =new StringBuilder()
					.append("SELECT 		@ROWNUM := @ROWNUM - 1 AS ROWNUM,\n")
					.append("				ta.*\n")
					.append("FROM 			comment ta,\n")
					.append("				(SELECT @rownum := (SELECT	COUNT(*)-?+1 FROM comment ta)) tb\n")
					.append("where   		b_idx = ?\n")
					.append("order by		c_group desc, c_order asc\n")
					.append("LIMIT			?, ? \n")
					.toString();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pageNum);
			pstmt.setInt(2, board.getB_idx());
			pstmt.setInt(3, pageNum);
			pstmt.setInt(4, Pagination.perPage);
			rs = pstmt.executeQuery();
			list = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setRownum(rs.getInt("ROWNUM"));
				comment.setC_idx(rs.getInt("c_idx"));
				comment.setC_content(rs.getString("c_content"));
				comment.setC_date(rs.getString("c_date"));
				list.add(comment);
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
	public void insertComment(Comment comment) {
		Connection conn = null;
		PreparedStatement  pstmt = null;
		
			try {
				conn = DBConnection.getConnection();
				String sql = "insert into comment(c_content,c_date,b_idx,c_group,c_order,c_depth) values(?,now(),?,0,1,0)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, comment.getC_content());
				pstmt.setInt(2, comment.getB_idx());
				pstmt.executeUpdate();
				pstmt.close();
								
				sql = "update comment set c_group=last_insert_id() where c_idx = last_insert_id()";
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
	
	public int getCommentsCount(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "select count(*) as count from comment where b_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_idx());
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
				comment.setC_group(rs.getInt("c_group"));
				comment.setC_order(rs.getInt("c_order"));
				comment.setC_depth(rs.getInt("c_depth"));
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
	
	public void replyComment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into comment(c_content,c_date,c_group,c_order,c_depth) values(?,now(),?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getC_content());
			pstmt.setInt(2, comment.getC_group());
			pstmt.setInt(3, comment.getC_order());
			pstmt.setInt(4, comment.getC_depth());
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "update comment set c_order=c_order+1 where c_group = ? and c_order >= ? and c_idx <> last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getC_group());
			pstmt.setInt(2, comment.getC_order());
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
	
}

