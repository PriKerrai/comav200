package com.coma.client;

import java.util.List;

import com.coma.client.widgets.SwitchGroupCellGrid;
import com.coma.client.widgets.SwitchGroupCellGridForDialog;
import com.coma.client.widgets.SwitchGroupDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Dialog;

public class HandleGroups {

	public HandleGroups(){}

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public void sendGroupInvite(String email){
		getGroupFacilitator(email);
	}
	public void getGroupInvites(){

		getGroupInvitesFromDB();

	}

	public void acceptGroupInvite(int groupID, int inviteID){
		addUserToGroupFromInvite(groupID, inviteID);
	}

	public void declineGroupInvite(int inviteID){
		setInviteToInactive(inviteID);
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
		databaseConnection.getGroupInvites(User.getInstance().getUserId(), new AsyncCallback<List<WorkGroupInfo>>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<WorkGroupInfo> result) {

				System.out.println(result.size());
				Comav200.GetInstance().getHandleGroupInvitesPanel().setInviteList(result);
				System.out.println("efter set list");
				//						HandleGroupInvitesDialogBox hgidb = new HandleGroupInvitesDialogBox();
				//						Dialog dialog = hgidb.createDialogBox(invitesList.loadModelPanel());
				//						dialog.center();
				//						dialog.show();

			}
		});
	}


	private void addUserToGroupFromInvite(int groupID, int invID){
		final int inviteID = invID;
		databaseConnection.addUserToGroup(groupID, User.getInstance().getUserId(), new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(Void result) {
				setInviteToInactive(inviteID);
			}
		});
	}

	public void addUserToGroup(int groupID){
		databaseConnection.addUserToGroup(groupID, User.getInstance().getUserId(), new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(Void result) {

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

	public void getUsersGroups(){
		databaseConnection.getUsersGroups(User.getInstance().getUserId(), new AsyncCallback<List<WorkGroupInfo>>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<WorkGroupInfo> result) {
				Comav200.GetInstance().getSwitchGroupCellPanel().setworkGroupInfoList(result);

			}
		});
	}
	
	public void getUsersGroupsForDialog(){
		databaseConnection.getUsersGroups(User.getInstance().getUserId(), new AsyncCallback<List<WorkGroupInfo>>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<WorkGroupInfo> result) {
				SwitchGroupDialog sgd = new SwitchGroupDialog();
				SwitchGroupCellGridForDialog.setworkGroupInfoList(result);
				Dialog dialog = sgd.createDialogBox(SwitchGroupCellGridForDialog.createSwitchGroupCellGrid());

				dialog.center();
				dialog.show();

			}
		});
	}

	public void getAllGroupMemebers(){
		databaseConnection.getAllGroupMembers(User.getInstance().getActiveGroupID(), new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<String> result) {


			}
		});
	}

}
