package com.coma.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseConnectionAsync {
	void databaseServer(String email, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void databaseServer(String name, String password, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
