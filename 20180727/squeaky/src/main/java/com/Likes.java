package com;

import java.util.Set;

public class Likes {
	long commentId;
	int count;
	Set<String> userId;
	Set<String> userName;
	
	public Likes(long commentId, int count, Set<String> userId, Set<String> userName) {
		super();
		this.commentId = commentId;
		this.count = count;
		this.userId = userId;
		this.userName = userName;
	}
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Set<String> getUserId() {
		return userId;
	}
	public void setUserId(Set<String> userId) {
		this.userId = userId;
	}
	public Set<String> getUserName() {
		return userName;
	}
	public void setUserName(Set<String> userName) {
		this.userName = userName;
	}

}
