package com.coma.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.coma.client.DatabaseConnection;
import com.coma.client.ModelInfo;
import com.coma.client.ProposalAvgVote;
import com.coma.client.WorkGroupInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseConnectionImpl extends RemoteServiceServlet implements
DatabaseConnection {
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	private Connection initializeDBConnection() throws SQLException {
		String dbURL = "jdbc:mysql://localhost:3306/comadatabase";
		String username ="root";
		String password = "";

		Connection dbCon = null;

		return dbCon = DriverManager.getConnection(dbURL, username, password);
	}

	@Override
	public void createNewUser(String email, String password)
			throws IllegalArgumentException {
		Connection dbCon = null;

		String insertUserQuery = "INSERT INTO user (userEmail, userPassword) VALUES (?,?)";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStmt = dbCon.prepareStatement(insertUserQuery);
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, password);

			preparedStmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
	}

	@Override
	public String getPasswordForAuthorization(String email)throws IllegalArgumentException {
		Connection dbCon = null;
		String password = null;

		String query = "SELECT userPassword FROM user WHERE userEmail = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				password = rs.getString("userPassword");
			}
			return password;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public int getUserID(String email) throws IllegalArgumentException {
		Connection dbCon = null;
		int userID = 0;

		String query = "SELECT userID FROM user WHERE userEmail = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				userID = rs.getInt("userID");
			}
			return userID;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return 0;
	}

	@Override
	public List<ModelInfo> getAllModelsFromSpecificGroupThatIsProposed(int activeGroup) throws IllegalArgumentException {
		Connection dbCon = null;

		List<ModelInfo> modelInfoList = new ArrayList<ModelInfo>();

		int modelID;
		int modelGroupID;
		int modelCreator;
		int modelType;
		String modelString;
		String modelName;	
		int IsProposal;
		String modelCreationDate;

		String query = "SELECT * FROM model WHERE groupID = ? AND isProposal = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, activeGroup);
			preparedStatement.setInt(2, 1);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelID = rs.getInt("modelID");
				modelGroupID = rs.getInt("groupID");
				modelCreator = rs.getInt("modelCreator");
				modelType = rs.getInt("modelType");
				modelString = rs.getString("modelString");
				modelName	 = rs.getString("modelName");
				IsProposal = rs.getInt("isProposal");
				modelCreationDate = rs.getString("creationDate");

				ModelInfo mI = new ModelInfo(modelID, modelGroupID, modelCreator,modelType,modelString,modelName,IsProposal,modelCreationDate);
				modelInfoList.add(mI);
			}
			return  modelInfoList;
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return  null;

	}

	@Override
	public int createNewGroup(int userID, String groupName) {
		Connection dbCon = null;

		String query = "INSERT INTO workGroup (groupFacilitator, groupname) VALUES (?,?)";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, groupName);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

		query = "SELECT * FROM workGroup WHERE groupFacilitator = ? ORDER BY groupID DESC LIMIT 1";
		int groupID = 0;
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				groupID = rs.getInt("groupID");
			} 
		}
		catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  
		return groupID;

	}

	public String getDate() {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(calendar.getTime());
	}

	@Override
	public void saveModel(int groupID, int userID, String modelName,
			int modelType, String modelString, int isProposal) {

		String creationDate = getDate();

		Connection dbCon = null;

		String query = "INSERT INTO model (groupID, modelCreator, modelName, modelType, modelString, creationDate, isProposal) VALUES (?,?,?,?,?,?,?)";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setString(3, modelName);
			preparedStatement.setInt(4, modelType);
			preparedStatement.setString(5, modelString);
			preparedStatement.setString(6, creationDate);
			preparedStatement.setInt(7, isProposal);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public ModelInfo loadModel(int modelID) {
		Connection dbCon = null;
		ModelInfo modelInfo = new ModelInfo();

		String query = "SELECT * FROM model WHERE modelID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelInfo.setModelID(rs.getInt("modelID"));
				modelInfo.setModelGroupID(rs.getInt("groupID"));
				modelInfo.setModelCreator(rs.getInt("modelCreator"));
				modelInfo.setModelType(rs.getInt("modelType"));
				modelInfo.setModelString(rs.getString("modelString"));
				modelInfo.setModelName(rs.getString("modelName"));
				modelInfo.setIsProposal(rs.getInt("isProposal"));
				modelInfo.setModelCreationDate(rs.getString("creationDate"));
			}
			return modelInfo;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public void addCommentToModel(int userID, int modelID, String comment) {

		Connection dbCon = null;

		String query = "insert into modelcomment (modelID, comment) VALUES (?,?)";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);   
			preparedStatement.setInt(1, modelID);
			preparedStatement.setString(2, comment);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 

	}

	@Override
	public void addVoteToModel(int userID, int modelID, int grade) {

		Connection dbCon = null;

		String query = "INSERT INTO voteonmodel (modelID, userID, grade) VALUES (?,?,?)";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, grade);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public List<String> getCommentsOnModel(int modelID) {
		Connection dbCon = null;

		List<String> commentList = new ArrayList<String>();

		String comment;

		String query = "SELECT comment FROM modelcomment WHERE modelID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				comment = rs.getString("comment");
				commentList.add(comment);
			}
			return  commentList;
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return  null;
	}

	public void inviteToGroup(int groupID, int userID) {

		Connection dbCon = null;

		String query = "INSERT INTO workgroupinvites (groupID, userID) VALUES (?,?)";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, userID);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  
	}

	@Override
	public WorkGroupInfo getGroupInfo(int activeGroupID) {
		Connection dbCon = null;
		WorkGroupInfo workGroupInfo = new WorkGroupInfo();

		String query = "SELECT * FROM workgroup WHERE groupID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, activeGroupID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				workGroupInfo.setWorkGroupID(rs.getInt("groupID"));
				workGroupInfo.setWorkGroupFacilitator(rs.getInt("groupFacilitator"));
				workGroupInfo.setWorkGroupName(rs.getString("groupName"));
			}
			return workGroupInfo;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public ModelInfo loadGroupModel(int groupID) {
		Connection dbCon = null;
		ModelInfo modelInfo = new ModelInfo();
		String query = "SELECT * FROM activegroupmodel as a LEFT JOIN model as m ON a.modelID = m.modelID WHERE a.groupID = ? ORDER BY a.groupModelID DESC LIMIT 1 ";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelInfo.setModelID(rs.getInt("modelID"));
				modelInfo.setModelGroupID(rs.getInt("groupID"));
				modelInfo.setModelCreator(rs.getInt("modelCreator"));
				modelInfo.setModelType(rs.getInt("modelType"));
				modelInfo.setModelString(rs.getString("modelString"));
				modelInfo.setModelName(rs.getString("modelName"));
				modelInfo.setIsProposal(rs.getInt("isProposal"));
				modelInfo.setModelCreationDate(rs.getString("creationDate"));

			}			
			return modelInfo;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public List<WorkGroupInfo> getGroupInvites(int userID) {
		Connection dbCon = null;
		List<WorkGroupInfo> invitesList = new ArrayList<WorkGroupInfo>();

		String query = "SELECT * FROM workgroupinvites WHERE userID = ? AND isActive = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, 1);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				WorkGroupInfo workGroupInfo = new WorkGroupInfo();
				workGroupInfo.setWorkGroupInviteID(rs.getInt("inviteID"));
				workGroupInfo.setWorkGroupID(rs.getInt("groupID"));
				invitesList.add(workGroupInfo);
			}
			return getGroupInvitesInfo(invitesList);

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}


	private List<WorkGroupInfo> getGroupInvitesInfo(List<WorkGroupInfo> invitesList) {
		Connection dbCon = null;

		String query = "SELECT * FROM workgroup WHERE groupID = ?";
		for(WorkGroupInfo groupInfo : invitesList){
			try{
				dbCon = initializeDBConnection(); 
				PreparedStatement preparedStatement = dbCon.prepareStatement(query);
				preparedStatement.setInt(1, groupInfo.getWorkGroupID());
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					groupInfo.setWorkGroupName(rs.getString("groupName"));
					groupInfo.setWorkGroupFacilitator(rs.getInt("groupFacilitator"));
				}

			} catch (SQLException ex) {
				Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
			}      
		}
		return invitesList;
	}


	@Override
	public void addUserProfileToUser(int userID, String firstName,
			String surName, String birthDay, String phoneNumber) {
		Connection dbCon = null;

		String query = "INSERT INTO userprofile (userID, firstName, surName, birthDate, phoneNumber) VALUES (?,?,?,?,?)" +
				"ON DUPLICATE KEY update firstName = ?,surName = ?, birthDate = ?, phoneNumber = ?";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, surName);
			preparedStatement.setString(4, birthDay);
			preparedStatement.setString(5, phoneNumber);

			preparedStatement.setString(6, firstName);
			preparedStatement.setString(7, surName);
			preparedStatement.setString(8, birthDay);
			preparedStatement.setString(9, phoneNumber);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  
	}

	@Override
	public List<String> getUserProfile(int userID) throws IllegalArgumentException {
		Connection dbCon = null;

		List<String> userProfile = new ArrayList<String>();

		String query = "SELECT * FROM userprofile WHERE userID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String fName = rs.getString("firstName");
				String sName = rs.getString("surName");
				String bDay = rs.getString("birthDate");
				String phoneNr = rs.getString("phoneNumber");
				userProfile.add(fName);
				userProfile.add(sName);
				userProfile.add(bDay);
				userProfile.add(phoneNr);
			}

			return userProfile;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return userProfile;
	}

	@Override
	public void updateActiveGroupModel(int activeGroupID, int modelID, String version) {
		Connection dbCon = null;

		String modelString = "";
		String query = "SELECT modelString FROM model WHERE modelID = ?";
		try{
			dbCon = initializeDBConnection(); 

			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelString = rs.getString("modelString");
			}

			String queryTwo = "INSERT INTO activegroupmodel(groupID, modelID, modelString, version) VALUES (?,?,?,?)";

			PreparedStatement preparedStatementTwo = dbCon.prepareStatement(queryTwo);
			preparedStatementTwo.setInt(1, activeGroupID);
			preparedStatementTwo.setInt(2, modelID);
			preparedStatementTwo.setString(3, modelString);
			preparedStatementTwo.setString(4, version);

			preparedStatementTwo.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 

	}

	@Override
	public List<WorkGroupInfo> getUsersGroups(int userID) {
		Connection dbCon = null;
		List<WorkGroupInfo> invitesList = new ArrayList<WorkGroupInfo>();

		String query = "SELECT * FROM workgroupmember WHERE userID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				WorkGroupInfo workGroupInfo = new WorkGroupInfo();
				workGroupInfo.setWorkGroupID(rs.getInt("groupID"));
				//workGroupInfo.setWorkGroupName(rs.getString("groupName"));
				//workGroupInfo.setWorkGroupFacilitator(rs.getInt("groupFacilitator"));
				invitesList.add(workGroupInfo);
			}
			return getUsersGroupsInfo(invitesList);

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	public List<WorkGroupInfo> getUsersGroupsInfo(List<WorkGroupInfo> invitesList) {
		Connection dbCon = null;

		String query = "SELECT * FROM workgroup WHERE groupID = ?";

		for(WorkGroupInfo groupInfo: invitesList){
			try{
				dbCon = initializeDBConnection(); 
				PreparedStatement preparedStatement = dbCon.prepareStatement(query);
				preparedStatement.setInt(1, groupInfo.getWorkGroupID());
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					groupInfo.setWorkGroupName(rs.getString("groupName"));
					groupInfo.setWorkGroupFacilitator(rs.getInt("groupFacilitator"));
				}
			} catch (SQLException ex) {
				Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return invitesList;
	}

	@Override
	public List<ModelInfo> getAllUsersModels(int userID) {
		Connection dbCon = null;
		List<ModelInfo> modelList = new ArrayList<ModelInfo>();

		String query = "SELECT * FROM model WHERE modelCreator = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ModelInfo modelInfo = new ModelInfo();
				modelInfo.setModelID(rs.getInt("modelID"));
				modelInfo.setModelGroupID(rs.getInt("groupID"));
				modelInfo.setModelCreator(rs.getInt("modelCreator"));
				modelInfo.setModelType(rs.getInt("modelType"));
				modelInfo.setModelString(rs.getString("modelString"));
				modelInfo.setModelName(rs.getString("modelName"));
				modelInfo.setIsProposal(rs.getInt("isProposal"));
				modelInfo.setModelCreationDate(rs.getString("creationDate"));
				modelList.add(modelInfo);
			}
			return modelList;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public void setInviteToInactive(int inviteID) {
		Connection dbCon = null;



		String query = "UPDATE workgroupinvites SET isActive = ? WHERE inviteID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, inviteID);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public void addUserToGroup(int groupID, int userID) {
		Connection dbCon = null;

		System.out.println("GROUP : " + groupID + "USERID : " + userID);
		String query = "INSERT INTO workgroupmember (groupID, userID) VALUES (?,?)";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, userID);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public List<String> getAllGroupMembers(int groupID) {
		Connection dbCon = null;
		List<String> members = new ArrayList<String>();

		String query = "SELECT * FROM workgroupmember as w LEFT JOIN user as u ON w.userID = u.userID WHERE w.groupID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				members.add(rs.getString("userEmail"));
			}
			return members;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}


	@Override
	public List<ProposalAvgVote> getVotes(List<Integer> modelIDs) {
		Connection dbCon = null;
		ProposalAvgVote proposalAvgVote;
		List<ProposalAvgVote> votes = new ArrayList<ProposalAvgVote>();

		String query = "SELECT AVG(v.grade) as average, u.firstName FROM model as m Left JOIN userprofile as u ON m.modelCreator = u.userID LEFT JOIN voteonmodel as v ON v.modelID = m.modelID WHERE m.modelID = ?";

		for (Iterator<Integer> i = modelIDs.iterator(); i.hasNext();) {

			if(i.hasNext()){

				proposalAvgVote = new ProposalAvgVote();

				try{		
					int modelID = i.next();
					dbCon = initializeDBConnection(); 
					PreparedStatement preparedStatement = dbCon.prepareStatement(query);
					preparedStatement.setInt(1, modelID);					
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						proposalAvgVote.setName(rs.getString("firstName"));
						proposalAvgVote.setAvgVote(rs.getFloat("average"));
					}
					votes.add(proposalAvgVote);

				} catch (SQLException ex) {
					Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
				}      
			}
		}
		return votes;
	}


	@Override
	public String getModelCreatorName(int modelID) {
		Connection dbCon = null;
		String modelCreator = "";

		String query = "SELECT * FROM userprofile as u LEFT JOIN model as m ON u.userID =  m.modelCreator WHERE m.modelID = ? ";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelCreator = rs.getString("firstName");
			}
			return modelCreator;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public List<Integer> getModelIDs(int groupID) {
		Connection dbCon = null;
		List<Integer> modelIDs = new ArrayList<Integer>();

		String query = "SELECT * FROM model  WHERE groupID = ? AND isProposal = ? ";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, 1);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelIDs.add(rs.getInt("modelID"));
			}
			return modelIDs;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}
	
	
	/*
	@Override
	public String getUserName(String email) {
		Connection dbCon = null;
		String name = "";
		String query = "SELECT * FROM user as u LEFT JOIN userprofile as p ON u.userID = p.userID WHERE userEmail = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				name = rs.getString("firstName");
			}
			return name;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}
	*/
	

}


