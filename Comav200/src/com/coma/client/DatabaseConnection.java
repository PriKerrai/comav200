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
	void addCommentToModel(int userID, String comment);
	void addVoteToModel(int userID, int index);
	List<ModelInfo> getAllModelsFromSpecificGroupThatIsProposed(int activeGroup)
			throws IllegalArgumentException;
	ModelInfo loadModel(int modelID);
	
}
