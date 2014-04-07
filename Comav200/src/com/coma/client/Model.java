package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Model implements IsSerializable {

	
	private int modelID;
	private int groupID;
	private int creatorID;
	private String modelName;
	private int modelType;
	private String modelString;
	private int isProposal;
	
	public Model(){}

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getModelType() {
		return modelType;
	}

	public void setModelType(int modelType) {
		this.modelType = modelType;
	}

	public String getModelString() {
		return modelString;
	}

	public void setModelString(String modelString) {
		this.modelString = modelString;
	}

	public int getIsProposal() {
		return isProposal;
	}

	public void setIsProposal(int isProposal) {
		this.isProposal = isProposal;
	}
	

	
	
}
