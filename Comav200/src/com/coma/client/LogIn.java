package com.coma.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class LogIn {

	TextButton logInButton = new TextButton("Log In");
	TextButton signUpButton = new TextButton("Sign Up");
	TextField emailTextField = new TextField();
	PasswordField passwordField = new PasswordField();
	String password = null;

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public FormPanel screen() {
		final FormPanel form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.addStyleName("table-center");
		form.addStyleName("demo-FormPanel");

		VerticalPanel holder = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		holder.add(new Label("Email"));
		emailTextField.setName("email");
		holder.add(emailTextField);

		holder.add(new Label("Password"));
		passwordField.setName("password");
		holder.add(passwordField);

		signUpButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				Comav200.GetInstance().initializeSignUp();
			}
		});

		logInButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				getPasswordFromDatabase(emailTextField.getText());
			}
		});

		hPanel.add(logInButton);
		hPanel.add(signUpButton);
		holder.add(hPanel);

		form.add(holder);
		return form;
	}

	private String encryptPassword(String password) {

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
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	private void checkAuthantication(String email, String password) {
		String encryptedPassword = encryptPassword(passwordField.getValue());
		String emailText = emailTextField.getText();
		if (encryptedPassword.equals(password)) {
			getAndSetUserName(emailText);
			getAndSetUserID(emailText);	
			User.getInstance().setUserEmail(emailTextField.getText());
		} else {
			AlertMessageBox alert = new AlertMessageBox("Login failed",
					"Please check your credentials and try again.");
			alert.show();
		}
	}

	/**
	*Gets active users ID from database and sets the ID in the User class
	*/
	public void getAndSetUserID(String email) {
		databaseConnection.getUserID(email, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Integer result) {
				// TODO Auto-generated method stub
				User.getInstance().setUserId(result);
				Comav200.GetInstance().initMainProgram();		
			}
		});
	}
	
	public void getAndSetUserName(String email) {
		databaseConnection.getUserName(email, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				System.out.println(result);
				User.getInstance().setUserName(result);
			}
		});
	}
	
	
	public void getPasswordFromDatabase(String emailString) {

		final String email = emailString;
		databaseConnection.getPasswordForAuthorization(email,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess(String result) {
						checkAuthantication(email, result);
					}
				});
	}
}
