package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ModelInfo implements IsSerializable {

	private int modelID;
	private int modelGroupID;
	private int modelCreator;
	private String modelCreatorName;
	public String getModelCreatorName() {
		return modelCreatorName;
	}

	public void setModelCreatorName(String modelCreatorName) {
		this.modelCreatorName = modelCreatorName;
	}

	private int modelType;
	private String modelString;
	private String modelName;	
	private int IsProposal;
	private String modelCreationDate;
	private int groupModelVersion;
	
	public int getGroupModelVersion() {
		return groupModelVersion;
	}

	public void setGroupModelVersion(int groupModelVersion) {
		this.groupModelVersion = groupModelVersion;
	}

	public ModelInfo() {}

    public ModelInfo(int modelID, int modelGroupID, int modelCreator, 
    		int modelType, String modelString, String modelName, 
    		int IsProposal, String modelCreationDate) {
    	this.modelID = modelID;
    	this.modelGroupID = modelGroupID;
    	this.modelCreator = modelCreator;
    	this.modelType = modelType;
    	this.modelString = modelString;
    	this.modelName = modelName;
    	this.IsProposal = IsProposal;
    	this.modelCreationDate = modelCreationDate;	
    }

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
	}

	public int getModelGroupID() {
		return modelGroupID;
	}

	public void setModelGroupID(int modelGroupID) {
		this.modelGroupID = modelGroupID;
	}

	public int getModelCreator() {
		return modelCreator;
	}

	public void setModelCreator(int modelCreator) {
		this.modelCreator = modelCreator;
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

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int isIsProposal() {
		return IsProposal;
	}

	public void setIsProposal(int isProposal) {
		IsProposal = isProposal;
	}

	public String getModelCreationDate() {
		return modelCreationDate;
	}

	public void setModelCreationDate(String modelCreationDate) {
		this.modelCreationDate = modelCreationDate;
	}
}
	
