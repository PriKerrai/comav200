package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WorkGroupInfo implements IsSerializable {

	private int workGroupID;
	private int workGroupFacilitator;
	private String workGroupName;
	
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
	
}
