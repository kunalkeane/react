package com.util;

import org.springframework.stereotype.Component;

@Component
public class StatsCache {
	
	private long lastUpdateTime;

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	

}
