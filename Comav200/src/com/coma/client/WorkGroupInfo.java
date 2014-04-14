package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WorkGroupInfo implements IsSerializable {

	//This field is only used for invites. 
		//to create a new class for invites would make a lot of redundant code
	private int workGroupInviteID; 
	
	private int workGroupID;
	private String workGroupName;
	private int workGroupFacilitator;
	private String facilitatorName;
	
	
	public String getFacilitatorName() {
		return facilitatorName;
	}
	public void setFacilitatorName(String facilitatorName) {
		this.facilitatorName = facilitatorName;
	}
	public int getWorkGroupID() {
		return workGroupID;
	}
	public void setWorkGroupID(int workGroupID) {
		this.workGroupID = workGroupID;
	}
	public int getWorkGroupFacilitator() {
		return workGroupFacilitator;
	}
	public void setWorkGroupFacilitator(int workGroupFacilitator) {
		this.workGroupFacilitator = workGroupFacilitator;
	}
	public String getWorkGroupName() {
		return workGroupName;
	}
	public void setWorkGroupName(String workGroupName) {
		this.workGroupName = workGroupName;
	}
	public int getWorkGroupInviteID() {
		return workGroupInviteID;
	}
	public void setWorkGroupInviteID(int workGroupInviteID) {
		this.workGroupInviteID = workGroupInviteID;
	}
	
}
