package com.coma.client;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.widgets.MessageFrame;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HandleGroups {

	public HandleGroups(){}
	
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public void sendGroupInvite(String email){
		getGroupFacilitator(email);
	}
	public void getGroupInvites(){
		
		//TODO: not yet implemented
		
	}
	
	public void acceptGroupInvite(){
		
	}
	
	public void declineGroupInvite(){
		
	}
	
	private void getGroupFacilitator(String email){
		
		final String userEmail = email;
		databaseConnection.getGroupInfo(User.getInstance().getActiveGroupID(), new AsyncCallback<WorkGroupInfo>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(WorkGroupInfo result) {
						if(result.getWorkGroupFacilitator() == User.getInstance().getUserId()){
							getUserID(userEmail);
						}
					}
				});
		}
	private void getUserID(String email){
		final String userEmail = email;
		databaseConnection.getUserID(userEmail, new AsyncCallback<Integer>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(Integer result) {
						sendInvite(result);
					}
				});
		}
	private void sendInvite(int userID){
		databaseConnection.inviteToGroup(User.getInstance().getActiveGroupID(), userID, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(Void result) {
					}
				});
		}
	
	private void getGroupInvitesFromDB(){
		databaseConnection.getGroupInvites(User.getInstance().getUserId(), new AsyncCallback<List<WorkGroupInvite>>() {
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<WorkGroupInvite> result) {
						// TODO Auto-generated method stub
						
					}
				});
		}
	
	private void getGroupInfo(){
		databaseConnection.getGroupInfo(User.getInstance().getActiveGroupID(), new AsyncCallback<WorkGroupInfo>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(WorkGroupInfo result) {
					}
				});
		}
	
	private void setInviteToInactive(int inviteID){
		databaseConnection.setInviteToInactive(inviteID, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(Void result) {
					}
				});
		}
	private void addUserToGroup(int groupID, int invID){
		final int inviteID = invID;
		databaseConnection.addUserToGroup(groupID, User.getInstance().getUserId(), new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(Void result) {
					}
				});
		}
	
	
}
