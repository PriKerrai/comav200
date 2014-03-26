package com.coma.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.coma.client.DatabaseConnection;
import com.coma.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseConnectionImpl extends RemoteServiceServlet implements
		DatabaseConnection {

	public String databaseServer(String input) throws IllegalArgumentException {
		
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

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
	public String databaseServer(String email, String password)
			throws IllegalArgumentException {
		Connection dbCon = null;
		Statement stmt = null;

        String insertUserQuery = "INSERT INTO user (email, password) VALUES (?,?)";
        
           try{
        	      dbCon = initializeDBConnection(); 
        	      PreparedStatement preparedStmt = dbCon.prepareStatement(insertUserQuery);
        	   	  preparedStmt.setString(1, email);
        	      preparedStmt.setString(2, password);
        	      
        	      
        	      preparedStmt.executeUpdate();
           
           } catch (SQLException ex) {
               Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
           } 
              //close connection ,stmt and resultset here
        	   
           
		return null;
	}
}
