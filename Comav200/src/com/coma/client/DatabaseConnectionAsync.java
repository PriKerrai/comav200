package com.coma.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseConnectionAsync {
	void databaseServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
