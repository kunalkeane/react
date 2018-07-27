package com;

import java.util.List;
import java.util.Set;

public class Comments {
	List<Comment> comments;
	long accessTime;
	Set<String> hashTags;
	List<String> topTrends;
	String selectedTag;
/*	long startCommentId;
	long endCommentId;
	boolean isTop;
	boolean isBottom;*/
	
	public Comments(List<Comment> comments, long accessTime, Set<String> hashTags, List<String> topTrends,
			String selectedTag) {
		super();
		this.comments = comments;
		this.accessTime = accessTime;
		this.hashTags = hashTags;
		this.topTrends = topTrends;
		this.selectedTag = selectedTag;
	}

	public List<Comment> getComments() {
		return comments;
	}

	/*public void setComments(List<Comment> comments) {
		this.comments = comments;
	}*/

	public long getAccessTime() {
		return accessTime;
	}

	public Set<String> getHashTags() {
		return hashTags;
	}

	public List<String> getTopTrends() {
		return topTrends;
	}

	public long getStartCommentId() {
		if(this.getComments() == null || this.getComments().isEmpty()){
			return -1;
		}
		return this.getComments().get(0).getId();
	}

	public long getEndCommentId() {
		if(this.getComments() == null || this.getComments().isEmpty()){
			return -1;
		}
		return this.getComments().get(this.getComments().size() - 1).getId();
	}

	public String getSelectedTag() {
		return selectedTag;
	}

	public void setSelectedTag(String selectedTag) {
		this.selectedTag = selectedTag;
	}

	
}
