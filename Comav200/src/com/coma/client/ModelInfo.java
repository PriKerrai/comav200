package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ModelInfo implements IsSerializable {

	private String modelID;
	private String modelGroupID;
	private String modelCreator;
	private String modelType;
	private String modelString;
	private String modelName;	
	private int IsProposal;
	private String modelCreationDate;
	
	public ModelInfo() {}

    public ModelInfo(String modelID, String modelGroupID, String modelCreator, 
    		String modelType, String modelString, String modelName, 
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

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public String getModelGroupID() {
		return modelGroupID;
	}

	public void setModelGroupID(String modelGroupID) {
		this.modelGroupID = modelGroupID;
	}

	public String getModelCreator() {
		return modelCreator;
	}

	public void setModelCreator(String modelCreator) {
		this.modelCreator = modelCreator;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
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
	
