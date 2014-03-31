package com.coma.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseConnectionAsync {
	void getPasswordForAuthorization(String email, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void createNewGroup(int userID, String groupName, String date,
			AsyncCallback<Void> callback);
	void createNewUser(String name, String password,
			AsyncCallback<Void> callback);

}
