package com;

import java.util.HashMap;
import java.util.Map;

public class NewComments {
	Map<HashTag, Long> hashTags = new HashMap<HashTag, Long>();
	int totalNewMessages;
	long timestamp;
	
	public Map<HashTag, Long> getHashTags() {
		return hashTags;
	}
	public void setHashTags(Map<HashTag, Long> hashTags) {
		this.hashTags = hashTags;
	}
	public int getTotalNewMessages() {
		return totalNewMessages;
	}
	public void setTotalNewMessages(int totalNewMessages) {
		this.totalNewMessages = totalNewMessages;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
