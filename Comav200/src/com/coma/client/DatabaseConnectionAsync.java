package com.coma.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseConnectionAsync {
	void getPasswordForAuthorization(String email, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void createNewGroup(int userID, String groupName, AsyncCallback<Void> callback);
	void createNewUser(String name, String password,
			AsyncCallback<Void> callback);
	void getUserID(String email, AsyncCallback<Integer> asyncCallback);	void saveModel(int groupID, int userID, String modelName, int modelType, String modelString, int isProposal,
			AsyncCallback<Void> asyncCallback);
	void loadGroupModel(int groupID, AsyncCallback<ModelInfo> asyncCallback);
	void loadModel(int modelID, AsyncCallback<ModelInfo> asyncCallback);
	void addVoteToModel(int userID, int modelID, int index, AsyncCallback<Void> asyncCallback);
	void getAllModelsFromSpecificGroupThatIsProposed(int activeGroup, AsyncCallback<List<ModelInfo>> callback);
	void addCommentToModel(int userID, int modelID, String comment,
			AsyncCallback<Void> callback);
	void getCommentsOnModel(int modelID,
			AsyncCallback<List<String>> asyncCallback);
	
	void inviteToGroup(int groupID, int userID, AsyncCallback<Void> callback);
	void getGroupInfo(int activeGroupID,
			AsyncCallback<WorkGroupInfo> asyncCallback);
	void getGroupInvites(int userId,
			AsyncCallback<List<WorkGroupInfo>> asyncCallback);
	void setInviteToInactive(int inviteID, AsyncCallback<Void> asyncCallback);
	void addUserToGroup(int groupID, int userId,
			AsyncCallback<Void> asyncCallback);
	void addUserProfileToUser(int userID, String firstName, String surName,
			String birthday, String phoneNumber, AsyncCallback<Void> callback);
	void getUserProfile(int userID, AsyncCallback<List<String>> callback);
	void getUsersGroups(int userID, AsyncCallback<List<WorkGroupInfo>> callback);
	void getAllUsersModels(int userID, AsyncCallback<List<ModelInfo>> callback);
	void updateActiveGroupModel(int activeGroupID, int modelID, String version,
			AsyncCallback<Void> callback);
	
}
