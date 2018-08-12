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
public class CommentDAO {
	
	@Autowired
	private UserDataAccess ldapDataAccessObject;
	
	@Autowired
	DAOUtil daoUtil;
	
	private final static String ALL  = "ALL";
	
		public  Comments getAllComments() {
			try {
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from comment where replyto is null order by displayid desc limit 20");
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				Comments comments = getAllComments(commentsList);
				comments.setSelectedTag(ALL);
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getAllComments(List<Comment> commentsList) {
			try {
				setLikesForComments(commentsList);
				return new Comments(commentsList, new Date().getTime(), getAllHashTags(), getTopTrends());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getLastComments(String tag) {
			try {
				Connection con = DBConnection.getConnection();
				String sql;
				if(!ALL.equals(tag)){
					sql = "select * from comment a, hashtag b where a.replyto is null and (a.id = b.comment_id) and b.hashtag = ? order by a.displayid limit 20";
				} else {
					sql = "select * from comment order by displayid limit 20";
				}
				
				PreparedStatement ps = con.prepareStatement(sql);
				if(!ALL.equals(tag)){
					ps.setString(1, tag);
				}
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				Collections.reverse(commentsList);
				Comments comments = getAllComments(commentsList);
				comments.setSelectedTag(tag);
				return comments;
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getLastCommentsByUser(String userId) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from comment where user=? OR id in (select comment_id from react.likes where user=?)"
						+ "order by displayid limit 20";
				
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, userId);
				
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				
				Collections.reverse(commentsList);
				
				commentsList = getDisplayableComments(commentsList);
				Comments comments = getAllComments(commentsList);
				comments.setTimelineUser(userId);
				return comments;
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getNextComments(long commentId, String tag) {
			try {
				Connection con = DBConnection.getConnection();
				String sql;
				if(!ALL.equals(tag)){
					sql = "select * from comment a, hashtag b where  a.displayid > ? and a.replyto is null and (a.id = b.comment_id) and b.hashtag = ? order by a.displayid limit 20";
				} else {
					sql = "select * from comment where displayid > ? and replyto is null order by displayid limit 20";
				}
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				if(!ALL.equals(tag)){
					ps.setString(2, tag);
				}
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				Collections.reverse(commentsList);
				Comments comments = getAllComments(commentsList);
				comments.setSelectedTag(tag);
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getNextCommentsByUser(long commentId, String userId) {
			try {
				Connection con = DBConnection.getConnection();
				String sql ="select * from comment where displayid > ? and (user=? OR id in (select comment_id from react.likes where user=?))"
						+ " order by displayid limit 20";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ps.setString(2, userId);
				ps.setString(3, userId);
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				Collections.reverse(commentsList);
				commentsList = getDisplayableComments(commentsList);
				Comments comments = getAllComments(commentsList);
				comments.setTimelineUser(userId);
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		
		private List<Comment> getDisplayableComments(List<Comment> commentsList){
			List<Comment> displayableComments = new ArrayList<>();
			for(Comment comment : commentsList){
				if(comment.getReplyTo() > 0){
					comment = getComment(comment.getReplyTo());
				}
				if(!displayableComments.contains(comment)){
					displayableComments.add(comment);
				}
			}
			return displayableComments;
		}
		
		
		public  Comments getPrevComments(long commentId, String tag) {
			try {
				Connection con = DBConnection.getConnection();
				String sql;
				if(!ALL.equals(tag)){
					sql = "select * from comment a, hashtag b where  a.displayid < ? and a.replyto is null and (a.id = b.comment_id) and b.hashtag = ? order by a.displayid desc, a.updatetime desc limit 20";
				} else {
					sql = "select * from comment where displayid< ? and replyto is null order by displayid desc limit 20";
				}
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				if(!ALL.equals(tag)){
					ps.setString(2, tag);
				}
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();Comments comments = getAllComments(commentsList);
				comments.setSelectedTag(tag);
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getPrevCommentsByUser(long commentId, String userId) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from comment where  displayid < ? and (user=? OR id in (select comment_id from react.likes where user=?))"
						+ " order by displayid desc limit 20";
				
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ps.setString(2, userId);
				ps.setString(3, userId);
				
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				
				commentsList  = getDisplayableComments(commentsList);
				Comments comments = getAllComments(commentsList);
				comments.setTimelineUser(userId);
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getCommentsByTag(String tag) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from comment a, hashtag b where (a.id = b.comment_id) and a.replyto is null and b.hashtag = ? "
						+ "order by a.displayid desc limit 20";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, tag);
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				Comments comments = getAllComments(commentsList);
				comments.setSelectedTag(tag);
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comments getCommentsByUser(String user) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select * from comment where (user=? OR id in (select comment_id from react.likes where user=?)) "
						+ " order by displayid desc limit 20";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, user);
				ps.setString(2, user);
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				commentsList  = getDisplayableComments(commentsList); 
				setLikesForComments(commentsList);
				con.close();
				Comments comments = getAllComments(commentsList);
				comments.setTimelineUser(user);
				return comments;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
				
		
		public  Comment postComment(CommentForm commentForm) {
			String comment = commentForm.getText();
			String user = commentForm.getUser();
			List<String> tags = CommentUtil.getHashTag(commentForm.getText());
			try {
				Connection con = DBConnection.getConnection();
				Long commentId = postComment(con, comment, user);
				if(tags.size() > 0){
					postHashTags(con, commentId.longValue(), tags);
				}
				con.close();
				return getComment(commentId);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Comment editComment(CommentForm commentForm) {
			try {
				Connection con = DBConnection.getConnection();
				deleteHashTagsForComment(con, commentForm.getCommentId(), CommentUtil.getHashTag(commentForm.getText()));
				List<String> tags = CommentUtil.getHashTag(commentForm.getText());
				if(tags.size() > 0){
					postHashTags(con, commentForm.getCommentId(), tags);
				}
				editComment(con, commentForm);
				con.close();
				return getComment(commentForm.getCommentId());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  void likeComment(long commentId, boolean isLike, String user) {
			if(isLike){
				likeComment(commentId, user);
			} else{
				unlikeComment(commentId, user);
			}
		}
		
		private  void likeComment(long commentId, String user) {
			try {
				String sql = "insert into likes (comment_id, user) values (?,?)";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ps.setString(2, user);
				ps.executeUpdate();
				
				Comment comment = getComment(commentId);
				
				if(comment.getReplyTo() > 0){
					updateDisplayIdForComment(con, comment.getReplyTo());
				}else{
					updateDisplayIdForComment(con, commentId);	
				}
				
				con.close();
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void unlikeComment(long commentId, String user) {
			try {
				String sql = "delete from likes where comment_id=? and user=?";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ps.setString(2, user);
				ps.executeUpdate();
				con.close();
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Likes getLikesByCommentId(long commentId) {
			try (Connection con = DBConnection.getConnection()) {
				return getLikesByCommentId(con, commentId);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public  void setLikesForComments(List<Comment> comments) {
			for(Comment comment : comments){
				comment.setLikes(getLikesByCommentId(comment.getId()));
			}
		}
		
		private Likes getLikesByCommentId(Connection con, long commentId) {
			try {
				String sql = "select * from likes where comment_id = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, commentId);
				ResultSet rs = ps.executeQuery();
				Set<String> userIds = new HashSet<String>();
				Set<String> userNames = new HashSet<String>();
				int count = 0;
				while (rs.next()){
					count++;
					String userId = rs.getString("user"); 
					userIds.add(userId);
					userNames.add(ldapDataAccessObject.getUserNameByUserId(userId));
				}
				Likes likeComment = new Likes(commentId, count, userIds, userNames);
				return likeComment;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private synchronized void editComment(Connection con, CommentForm commentForm) throws SQLException {
			try {
				long maxDisplayId = getMaxDisplayId(con);
				
				String sql = "update comment set text=?, displayid=?, updatetime=now(), edited='Y' where id=? and user=?";
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, commentForm.getText());
				ps.setLong(2, maxDisplayId);
				ps.setLong(3, commentForm.getCommentId());
				ps.setString(4, commentForm.getUser());
				ps.execute();
			}catch(SQLException e) {
				throw e;
			}
		}
		
		public synchronized long getMaxDisplayId(Connection con) throws SQLException {
			try {
				long maxDisplayId = 1;
				String selectSql = "select max(displayid) from comment";
				PreparedStatement selectPS = con.prepareStatement(selectSql);
				ResultSet rs = selectPS.executeQuery();
				if(rs.next()){
					maxDisplayId = rs.getLong(1);
				}
				return maxDisplayId + 1;
			}catch(SQLException e) {
				throw e;
			}
		}
		
		private synchronized Long postComment(Connection con, String comment, String user) throws SQLException {
			Long commentId = null;
			try {
				long maxDisplayId = getMaxDisplayId(con);
				
				String sql = "insert into comment (text, user, displayid) values (?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, comment);
				ps.setString(2, user);
				ps.setLong(3, maxDisplayId);
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
		
		public synchronized void updateDisplayIdForComment(Connection con, long commentId) throws SQLException {
			try {
				long maxDisplayId = getMaxDisplayId(con);
				String sql = "update comment set displayid = ? where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, maxDisplayId);
				ps.setLong(2, commentId);
				ps.executeUpdate();
			}catch(SQLException e) {
				throw e;
			}
		}
		
		
		/*public  void editLikeComment(long oldCommentId, long edittedCommentId) {
			try {
				String sql = "update likes set comment_id = ? where comment_id=?";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setLong(1, edittedCommentId);
				ps.setLong(2, oldCommentId);
				ps.executeUpdate();
				con.close();
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}*/
		
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
					comment.setDisplayId(rs.getInt("displayid"));
					comment.setText(rs.getString("text"));
					comment.setUser(rs.getString("user"));
					comment.setEdited(rs.getString("edited"));
					Timestamp timestamp = rs.getTimestamp("updatetime");
					comment.setTimestamp(timestamp.getTime());
					comment.setUserName(ldapDataAccessObject.getUserNameByUserId(comment.getUser()));
					comment.setReplyTo(rs.getInt("replyTo"));
				}
				
				//comment.setHashTag(getHashTags(commentId));
				con.close();
				return comment;
			} catch (Exception e) {
				new RuntimeException(e);
			}
			return null;
	    }
		
		public  NewComments getNewComments(long timestamp, String user) {
			NewComments newComments = new NewComments(); 
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select count(*) from comment where (updatetime > ? and user <> ? ) OR "
						+ "  id in (select comment_id from react.likes where updatetime > ? and user <> ?)";
				
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(timestamp));
				ps.setString(2, user);
				ps.setTimestamp(3, new Timestamp(timestamp));
				ps.setString(4, user);
				
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
		
		public  Set<String> getHashTags(String tag) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "select distinct hashtag from hashtag where hashtag like ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, tag);
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
				String sql = "SELECT distinct hashtag FROM react.hashtag h order by h.id desc limit 15;";
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
		
		public  List<User> getUsers() {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "SELECT * FROM react.users";
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				
				List<User> users = new ArrayList<User>();
				while (rs.next()){
					User user = new User();
					user.setUserId(rs.getString("userid"));
					user.setEmailAddress(rs.getString("email"));
					user.setFirstName(rs.getString("fname"));
					user.setLastName(rs.getString("lname"));
					user.setUserName(ldapDataAccessObject.getUserNameByUserId(user.getUserId()));
					users.add(user);
				}
				con.close();
				return users;
			} catch (Exception e) {
				new RuntimeException(e);
			}
			return null;
		}
		
		public  User getUser(String userId) {
			try {
				Connection con = DBConnection.getConnection();
				String sql = "SELECT * FROM react.users where userid=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ResultSet rs = ps.executeQuery();
				User user = null;
				if(rs.next()){
					user = new User();
					user.setUserName(rs.getString("userid"));
					user.setEmailAddress(rs.getString("email"));
					user.setFirstName(rs.getString("fname"));
					user.setLastName(rs.getString("lname"));
				}
				con.close();
				return user;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}
		
		public User addUserToDb(String userId, String fname, String lname, String email){
			try {
				Connection con = DBConnection.getConnection();
				String sql = "insert into users values (?,?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, email);
				ps.setString(3, fname);
				ps.setString(4, lname);
				ps.executeUpdate();
				con.close();
				return getUser(userId);
			}catch(SQLException e) {
				try{
				Connection con = DBConnection.getConnection();
				String sql = "update users set fname=?, lname=?, email=? where userid=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, fname);
				ps.setString(2, lname);
				ps.setString(3, email);
				ps.setString(4, userId);
				ps.executeUpdate();
				con.close();
				return getUser(userId);
				}catch(SQLException ex){
					throw new RuntimeException(ex);	
				}
			}
		
		}
		
	}	

