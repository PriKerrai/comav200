package com.coma.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.coma.client.DatabaseConnection;
import com.coma.client.Model;
import com.coma.client.ModelInfo;
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
			preparedStatement.setInt(1, 1);
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
	public void createNewGroup(int userID, String groupName) {
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
	public void addCommentToModel(int userID, String comment) {


	}

	@Override
	public void addVoteToModel(int userID, int index) {
		// TODO Auto-generated method stub

	}
}


