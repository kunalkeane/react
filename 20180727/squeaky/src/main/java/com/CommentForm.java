package com;

public class CommentForm {
	
	private String text;
	private String user;
	private long commentId;
	private boolean like;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public boolean isLike() {
		return like;
	}
	public void setLike(boolean isLike) {
		this.like = isLike;
	}
	
}
