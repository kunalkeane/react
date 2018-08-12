package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Comment;
import com.CommentForm;
import com.Comments;
import com.DBConnection;
import com.HashTag;
import com.Likes;
import com.NewComments;
import com.User;
import com.service.UserDataAccess;
import com.util.CommentUtil;


@Component
public class ReplyDAO {
	
	@Autowired
	private UserDataAccess ldapDataAccessObject;
	
	@Autowired
	DAOUtil daoUtil;
	@Autowired
	CommentDAO commentDAO;
	
		public  Comments getRepliesForComments(long commentId) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from comment where replyTo=? order by displayId limit 20";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				Comments comments = commentDAO.getAllComments(commentsList);
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  void deleteReply(long commentId) {
			try {
				Connection con = DBConnection.getConnection();
				deleteReply(con, commentId);
				deleteComment(con, commentId);
				con.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private void deleteReply(Connection con, long commentId) {
			try {
				String sql = "delete from likes where comment_id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ps.execute();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private  void deleteComment(Connection con, long commentId) {
			try {
				String sql = "delete from comment where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ps.execute();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comment replyComment(CommentForm commentForm) {
			String comment = commentForm.getText();
			String user = commentForm.getUser();
			
			try {
				Connection con = DBConnection.getConnection();
				Long commentId = postComment(con, comment, user, commentForm.getReplyTo());
				commentDAO.updateDisplayIdForComment(con, commentForm.getReplyTo());
				con.close();
				return commentDAO.getComment(commentId);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private synchronized Long postComment(Connection con, String comment, String user, long replyTo) throws SQLException {
			Long commentId = null;
			try {
				long maxDisplayId = commentDAO.getMaxDisplayId(con);
				
				String sql = "insert into comment (text, user, displayid, replyto) values (?,?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, comment);
				ps.setString(2, user);
				ps.setLong(3, maxDisplayId);
				ps.setLong(4, replyTo);
				ps.executeUpdate();
				
				
				ResultSet generatedKeys = ps.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                commentId = generatedKeys.getLong(1);
	            }
	            else {
	                throw new SQLException("Posting comment failed, no ID obtained.");
	            }
			}catch(SQLException e) {
				throw e;
			}
			return commentId;
		}
		
}	



