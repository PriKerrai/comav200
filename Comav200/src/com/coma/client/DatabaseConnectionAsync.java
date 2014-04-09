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

}
