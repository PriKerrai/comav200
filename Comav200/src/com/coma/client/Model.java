package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Model implements IsSerializable {

	private int id;
	private int creatorID;
	private String type;
	private String message;
	
	public Model(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
