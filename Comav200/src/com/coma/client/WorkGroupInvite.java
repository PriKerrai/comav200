package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WorkGroupInvite implements IsSerializable {

	private int workGroupInviteID;
	private int workGroupID;
	private String workGroupName;
	
	public int getWorkGroupInviteID() {
		return workGroupInviteID;
	}
	public void setWorkGroupInviteID(int workGroupInviteID) {
		this.workGroupInviteID = workGroupInviteID;
	}
	public int getWorkGroupID() {
		return workGroupID;
	}
	public void setWorkGroupID(int workGroupID) {
		this.workGroupID = workGroupID;
	}
	public String getWorkGroupName() {
		return workGroupName;
	}
	public void setWorkGroupName(String workGroupName) {
		this.workGroupName = workGroupName;
	}
	
}
