package com.coma.client;

public class ActiveInvite {

	private static ActiveInvite instance = null;
	
	public static ActiveInvite getInstance(){
		if(instance == null){
			instance = new ActiveInvite();
			return instance;
		}else{
			return instance;
		}
		
	}
	
	private int inviteID;
	private int groupID;
	
	public int getInviteID() {
		return inviteID;
	}
	public void setInviteID(int inviteID) {
		this.inviteID = inviteID;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
}
