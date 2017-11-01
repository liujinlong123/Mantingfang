package com.android.mantingfang.topic;

public class TopicGameFContent {

	private String userId;
	private String userName;
	private String headPath;
	/**
	 * 段位
	 */
	private String victoryRate;		//段位
	private String collect;
	private String gameNumber;
	private String singleRank;
	
	public TopicGameFContent() {}
	
	/**
	 * 1--收藏  0--没收藏
	 * @param userId
	 * @param userName
	 * @param headPath
	 * @param victoryRate
	 * @param collect
	 */
	public TopicGameFContent(String userId, String userName, String headPath, String victoryRate, String gameNumber, String collect) {
		this.userId = userId;
		this.userName = userName;
		this.headPath = headPath;
		this.victoryRate = victoryRate;
		this.gameNumber = gameNumber;
		this.collect = collect;
	}
	
	

	public String getSingleRank() {
		return singleRank;
	}

	public void setSingleRank(String singleRank) {
		this.singleRank = singleRank;
	}

	public String getGameNumber() {
		return gameNumber;
	}

	public void setGameNumber(String gameNumber) {
		this.gameNumber = gameNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getVictoryRate() {
		return victoryRate;
	}

	public void setVictoryRate(String victoryRate) {
		this.victoryRate = victoryRate;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}
	
}
