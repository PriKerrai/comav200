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
	void getUserID(String email, AsyncCallback<Integer> callback);
	void saveModel(int userID, String type, String model,
			AsyncCallback<Void> asyncCallback);
	void getModel(int modelID, AsyncCallback<Model> asyncCallback);
	void getVoteList(AsyncCallback<List<DiagramInfo>> callback);

}
