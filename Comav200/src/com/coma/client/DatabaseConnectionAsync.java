package com.coma.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseConnectionAsync {
	void getPasswordForAuthorization(String email, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void createNewGroup(int userID, String groupName, AsyncCallback<Integer> callback);
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
	void getAllUserModels(int userID, AsyncCallback<List<ModelInfo>> callback);
	void updateActiveGroupModel(int activeGroupID, int modelID, int version,
			AsyncCallback<Void> callback);
	void getAllGroupMembers(int activeGroupID,
			AsyncCallback<List<String>> asyncCallback);
	void getModelCreatorName(int modelID, AsyncCallback<String> callback);
	void getModelIDs(int groupID, AsyncCallback<List<Integer>> asyncCallback);
	void getVotes(List<Integer> modelIDs,
			AsyncCallback<List<ProposalAvgVote>> callback);
	void getUserName(String email, AsyncCallback<String> asyncCallback);
	void loadModel(String modelCreatorName, String modelName,
			String modelCreationDate, AsyncCallback<ModelInfo> asyncCallback);
	void getLatestGroupModelVersion(int groupID, AsyncCallback<Integer> callback);
	void getLatestJoinedGroup(int userId, AsyncCallback<Integer> asyncCallback);
	void setAllProposalsToInactive(int groupID, AsyncCallback<Void> callback);
	
}
