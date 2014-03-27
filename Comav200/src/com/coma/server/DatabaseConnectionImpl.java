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
	
	@Override
	public String databaseServer(String email)throws IllegalArgumentException {
		Connection dbCon = null;
		String password = "hej";
	
        String query = "SELECT password FROM user WHERE email = ?";
        System.out.println("databaseServer");
           try{
        	   System.out.println("before connection");
        	      dbCon = initializeDBConnection(); 
        	      System.out.println("after connection");
        	      PreparedStatement preparedStatement = dbCon.prepareStatement(query);
        	      preparedStatement.setString(1, email);
        	      ResultSet rs = preparedStatement.executeQuery();
        	      while (rs.next()) {
        	    	  System.out.println("while");
        	      	password = rs.getString("password");
        	      }
        	      System.out.println("password = " + password);
        	      return password;
           
           } catch (SQLException ex) {
        	   System.out.println("CATCH");
               Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
           }      
		return null;
	}
}
