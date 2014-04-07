package com.coma.client;

import java.sql.SQLException;
import java.util.Date;
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
	List<DiagramInfo> getVoteList();
	Model loadModel(int modelID);
	
}
