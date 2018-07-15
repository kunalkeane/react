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
import com.NewComments;
import com.UserInformation;
import com.service.UserDataAccess;
import com.util.CommentUtil;
import com.util.StatsCache;


@Component
public class CommentDAO {
	
	@Autowired
	private UserDataAccess ldapDataAccessObject;
	@Autowired
	private StatsCache statsCache;
	
	
		public  Comments getAllComments() {
			try {
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from comment order by id desc, updatetime desc limit 20");
				List<Comment> commentsList = getComments(rs);
				con.close();
				return new Comments(commentsList, new Date().getTime(), getAllHashTags(), getTopTrends());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getLastComments() {
			try {
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from comment order by id, updatetime limit 20");
				List<Comment> commentsList = getComments(rs);
				con.close();
				Collections.reverse(commentsList);
				return new Comments(commentsList, new Date().getTime(), getAllHashTags(), getTopTrends());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getNextComments(long commentId) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from comment where id > ? order by id, updatetime limit 20";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = getComments(rs);
				con.close();
				Collections.reverse(commentsList);
				return new Comments(commentsList, new Date().getTime(), getAllHashTags(), getTopTrends());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getPrevComments(long commentId) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from comment where id < ? order by id desc, updatetime desc limit 20";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = getComments(rs);
				con.close();
				return new Comments(commentsList, new Date().getTime(), getAllHashTags(), getTopTrends());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getCommentsByTag(String tag) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from comment a, hashtag b where (a.id = b.comment_id) and b.hashtag = ? order by a.updatetime desc";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, tag);
				ResultSet rs = ps.executeQuery();
				Comments comments = new Comments(getComments(rs), new Date().getTime(), getAllHashTags(), getTopTrends());
				con.close();
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private List<Comment> getComments(ResultSet rs) throws SQLException{
			List<Comment> comments = new ArrayList<Comment>();
			
			while (rs.next()){
				Comment comment = new Comment();
				int commentId = rs.getInt("id");
				comment.setId(commentId);
				//comment.setHashTag(getHashTags(commentId));
				comment.setUser(rs.getString("user"));
				comment.setText(rs.getString("text"));
				Timestamp timestamp = rs.getTimestamp("updatetime");
				comment.setTimestamp(timestamp.getTime());
				comment.setUserName(ldapDataAccessObject.getUserNameByUserId(comment.getUser()));
				comments.add(comment);
			}
			//return new Comments(comments, new Date().getTime(), getAllHashTags(), getTopTrends(), firstCommentId, lastCommentId);
			return comments;
		}
		
		public  Comment postComment(CommentForm commentForm) {
			long updateTime = new Date().getTime();
			String comment = commentForm.getText();
			String user = commentForm.getUser();
			List<String> tags = CommentUtil.getHashTag(commentForm.getText());
			try {
				Connection con = DBConnection.getConnection();
				Long commentId = postComment(con, comment, user);
				postHashTags(con, commentId.longValue(), tags);
				con.close();
				statsCache.setLastUpdateTime(updateTime);
				return getComment(commentId);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comment editComment(CommentForm commentForm) {
			try {
				Connection con = DBConnection.getConnection();
				deleteHashTagsForComment(con, commentForm.getCommentId(), CommentUtil.getHashTag(commentForm.getText()));
				deleteComment(con, commentForm);
				postComment(commentForm);
				con.close();
				return getComment(commentForm.getCommentId());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private  void deleteComment(Connection con, CommentForm commentForm) throws SQLException {
			try {
				String sql = "delete from comment where id=?";
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, commentForm.getCommentId());
				ps.execute();
			}catch(SQLException e) {
				throw e;
			}
		}
		
		private  Long postComment(Connection con, String comment, String user) throws SQLException {
			Long commentId = null;
			try {
				String sql = "insert into comment (text, user) values (?,?) ";
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, comment);
				ps.setString(2, user);
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
		
		private  void deleteHashTagsForComment(Connection con, long commentId, List<String> hashTags) throws SQLException {
			try {
				String sql = "delete from hashtag where comment_id=? ";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ps.executeUpdate();
			}catch(SQLException e) {
				throw e;
			}
		}
		
		private  void postHashTags(Connection con, long commentId, List<String> hashTags) throws SQLException {
			try {
				String sql = "insert into hashtag (comment_id, hashtag) values (?,?) ";
				PreparedStatement ps = con.prepareStatement(sql);
				for(String hashtag : hashTags){
					ps.setLong(1, commentId);
					ps.setString(2, hashtag);
					ps.addBatch();
				}
				ps.executeBatch();
			}catch(SQLException e) {
				throw e;
			}
		}
		
		public  Comment getComment(long commentId) {
			try {
				Connection con = DBConnection.getConnection();
				
				String sql = "select * from comment where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ResultSet rs = ps.executeQuery();
				Comment comment = new Comment();
				if (rs.next()){
					comment.setId(rs.getInt("id"));
					comment.setText(rs.getString("text"));
					comment.setUser(rs.getString("user"));
					comment.setUserName(ldapDataAccessObject.getUserNameByUserId(comment.getUser()));	
				}
				
				//comment.setHashTag(getHashTags(commentId));
				con.close();
				return comment;
			} catch (Exception e) {
				new RuntimeException(e);
			}
			return null;
	    }
		
		public  NewComments getNewComments(long timestamp) {
			NewComments newComments = new NewComments(); 
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select count(*) from comment where updatetime > ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(timestamp));
				
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()){
					newComments.setTotalNewMessages(rs.getInt(1));
					newComments.setTimestamp(new Date().getTime());
				}
				con.close();
			} catch (Exception e) {
				new RuntimeException(e);
			}
		return newComments;
	            
		}

		
		public  Set<String> getAllHashTags() {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select distinct hashtag from hashtag";
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				
				Set<String> hashTags = new TreeSet<String>();
				while (rs.next()){
					hashTags.add(rs.getString("hashtag"));
				}
				con.close();
				return hashTags;
			} catch (Exception e) {
				new RuntimeException(e);
			}
			return null;
	    }
		
		public  List<HashTag> getHashTags(long commentId) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from hashtag where comment_id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ResultSet rs = ps.executeQuery();
				
				List<HashTag> hashTags = new ArrayList<HashTag>();
				while (rs.next()){
					HashTag hashTag = new HashTag();
					hashTag.setId(rs.getInt("id"));
					hashTag.setHashTag(rs.getString("hashtag"));
					hashTags.add(hashTag);
				}
				con.close();
				return hashTags;
			} catch (Exception e) {
				new RuntimeException(e);
			}
			return null;
	    }

		public  List<String> getTopTrends() {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "SELECT distinct hashtag FROM react.hashtag h order by h.id desc limit 10;";
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				
				List<String> hashTags = new ArrayList<String>();
				while (rs.next()){
					hashTags.add(rs.getString("hashtag"));
				}
				con.close();
				return hashTags;
			} catch (Exception e) {
				new RuntimeException(e);
			}
			return null;
	    }
		
		
		public  Comment getComment() {
			
			HashTag hashTag = new HashTag();
			hashTag.setHashTag("#infoquest");
			HashTag[] hashTags = new HashTag[]{hashTag};
			List<HashTag> hashTagsList = Arrays.asList(hashTags);
			return CommentUtil.getComment(1, hashTagsList, "#infoquest is great program");
		}
		
		
	}	

