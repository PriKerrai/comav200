package com.coma.client;

public class User {

	private static User instance = null;
	private int userId;
	private int activeGroupID;

	public int getActiveGroupID() {
		return activeGroupID;
	}

	public void setActiveGroupID(int activeGroupID) {
		this.activeGroupID = activeGroupID;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public static User getInstance(){
		
		if(instance == null){
			instance = new User();
			return instance;
		}else{
			return instance;
		}
		
	}
	
}
