package com.coma.client;

import java.sql.SQLException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("database")
public interface DatabaseConnection extends RemoteService {
	void createNewUser(String name, String password) throws IllegalArgumentException;
	void createNewGroup(int userID, String groupName, String date);
	String getPasswordForAuthorization(String email);
}
