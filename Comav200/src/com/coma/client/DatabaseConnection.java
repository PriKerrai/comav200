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
	void createNewGroup(int userID, String groupName);
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
	void addUserProfileToUser(int userID, String firstName, String surName, String birthday, String phoneNumber);
	List<String> getUserProfile(int userID) throws IllegalArgumentException;
}
