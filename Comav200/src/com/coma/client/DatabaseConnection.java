package com.coma.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("database")
public interface DatabaseConnection extends RemoteService {
	void createNewUser(String name, String password) throws IllegalArgumentException;
	int createNewGroup(int userID, String groupName);
	String getPasswordForAuthorization(String email);
	int getUserID(String email);
	void saveModel(int groupID, int userID, String modelName, int modelType,
			String modelString, int isProposal);
	void addVoteToModel(int userID, int modelID, int index);
	List<ModelInfo> getAllModelsFromSpecificGroupThatIsProposed(int activeGroup)
			throws IllegalArgumentException;
	ModelInfo loadModel(int modelID);
	ModelInfo loadGroupModel(int groupID);
	void addCommentToModel(int userID, int modelID, String comment);
	List<String> getCommentsOnModel(int modelID);
	void inviteToGroup(int groupID, int userID);
	WorkGroupInfo getGroupInfo(int activeGroupID);
	List<WorkGroupInfo> getGroupInvites(int userId);
	void setInviteToInactive(int inviteID);
	void addUserToGroup(int groupID, int userId);
	void addUserProfileToUser(int userID, String firstName, String surName, String birthday, String phoneNumber);
	List<String> getUserProfile(int userID) throws IllegalArgumentException;
	List<WorkGroupInfo> getUsersGroups(int userID);
	List<ModelInfo> getAllUserModels(int userID);
	void updateActiveGroupModel(int activeGroupID, int modelID, int version);
	List<String> getAllGroupMembers(int activeGroupID);
	String getModelCreatorName(int modelID);
	List<Integer> getModelIDs(int groupID);
	List<ProposalAvgVote> getVotes(List<Integer> modelIDs);
	String getUserName(String email);
	ModelInfo loadModel(String modelCreatorName, String modelName,
			String modelCreationDate);
	int getLatestGroupModelVersion(int groupID);
	int getLatestJoinedGroup(int userId);
	void setAllProposalsToInactive(int groupID);
}
