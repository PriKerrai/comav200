package com.coma.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.tools.ant.taskdefs.Sleep;

import com.coma.shared.FieldVerifier;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LogIn {

	Button logInButton = new Button("Log In");
	Button signUpButton = new Button("Sign Up");
	TextBox emailTextBox = new TextBox();
	PasswordTextBox passwordTextBox = new PasswordTextBox();
	String password = null;

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
        public FormPanel screen(){
        	final FormPanel form = new FormPanel();
        	form.setEncoding(FormPanel.ENCODING_MULTIPART);
        	form.setMethod(FormPanel.METHOD_POST);
        	form.addStyleName("table-center");
        	form.addStyleName("demo-FormPanel");
        	
        	VerticalPanel holder = new VerticalPanel();
        	
        	holder.add(new Label("Email"));
        	emailTextBox.setName("email");
        	holder.add(emailTextBox);
        	
        	holder.add(new Label("Password"));
        	passwordTextBox.setName("password");
        	holder.add(passwordTextBox);

        	MyHandler handler = new MyHandler();
            logInButton.addClickHandler(handler);
            signUpButton.addClickHandler(handler);
        	
        	holder.add(logInButton);
        	holder.add(signUpButton);

            form.add(holder);    
            return form;
        }
        
    	private String encryptPassword(String password){
    		
    		MessageDigest m = null;
    		try {
    			m = MessageDigest.getInstance("MD5");
    		} catch (NoSuchAlgorithmException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		m.reset();
    		m.update(password.getBytes());
    		byte[] digest = m.digest();
    		BigInteger bigInt = new BigInteger(1,digest);
    		String hashtext = bigInt.toString(16);
    		// Now we need to zero pad it if you actually want the full 32 chars.
    		while(hashtext.length() < 32 ){
    		  hashtext = "0"+hashtext;
    		}
    		return hashtext;
    	}

         	
        private void checkAuthantication(String email, String password){
        	String encryptedPassword = encryptPassword(passwordTextBox.getValue());
        	if(encryptedPassword.equals(password)){        		
        		Comav200.GetInstance().getAndSetUserIDFromDatabase(emailTextBox.getText());
        		
        	}
        	else{
        		//show pop up login fail
        		System.out.println("Log in failed");
        	}
        }
        
    	public void getPasswordFromDatabase(String emailString) {

    		final String email = emailString;
    		databaseConnection.getPasswordForAuthorization(email, new AsyncCallback<String>() {
    			public void onFailure(Throwable caught) {
    			}

    			public void onSuccess(String result) {
    				checkAuthantication(email, result);
    			}
    		});
    	}
        
        class MyHandler implements ClickHandler{

            @Override
            public void onClick(ClickEvent event) {

                    if(event.getSource().equals(logInButton)){                  	
                    	getPasswordFromDatabase(emailTextBox.getText());
                    }
                    if(event.getSource().equals(signUpButton)){
                    	System.out.print(emailTextBox.getText());
                    	Comav200.GetInstance().initializeSignUp();	
                    }
            }
    }
        

}
