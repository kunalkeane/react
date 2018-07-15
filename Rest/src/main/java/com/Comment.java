package com;

public class Comment {
	
	private int id;
	private String text;
	//private List<HashTag> hashTags;
	private String user;
	private String userName;
	private long timestamp;
	
	/*public List<HashTag> getHashTags() {
		return hashTags;
	}
	public void setHashTags(List<HashTag> hashTags) {
		this.hashTags = hashTags;
	}*/
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	/*public List<HashTag> getHashTag() {
		return hashTags;
	}
	public void setHashTag(List<HashTag> hashTags) {
		this.hashTags = hashTags;
	}*/
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	
}
