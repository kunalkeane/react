package com;

public class HashTag {
	
	private int id;
	private String hashTag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHashTag() {
		return hashTag;
	}
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	
	@Override
	public int hashCode() {
		return hashTag.hashCode();
	}
	
	@Override
	public boolean equals(Object that) {
		return this.hashTag.equalsIgnoreCase(((HashTag)that).hashTag);
	}

}
