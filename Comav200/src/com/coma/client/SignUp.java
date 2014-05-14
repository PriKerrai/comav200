package com.coma.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class SignUp {

	TextButton signUpButton = new TextButton("Sign Up");
	TextField emailTextField = new TextField();
	PasswordField passwordTextField = new PasswordField();
	PasswordField passwordRepeatedTextField = new PasswordField();

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public FormPanel screen() {
		final FormPanel form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.addStyleName("table-center");
		form.addStyleName("demo-FormPanel");

		VerticalPanel holder = new VerticalPanel();

		holder.add(new Label("User Email"));
		emailTextField.setName("emailTextBox");
		holder.add(emailTextField);

		holder.add(new Label("Password"));
		passwordTextField.setName("password");
		holder.add(passwordTextField);

		holder.add(new Label("Repeat password"));
		passwordRepeatedTextField.setName("passwordRepeated");
		holder.add(passwordRepeatedTextField);

		signUpButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				
				String password = passwordTextField.getValue();
				String passwordRepeated = passwordRepeatedTextField.getValue();

				if (event.getSource().equals(signUpButton)) {

					if (password.length() < 10) {
						AlertMessageBox alert = new AlertMessageBox("Too short!", "Password need to be between X and X");
						alert.show();
						return;
					}
					if (password.equals(passwordRepeated)) {
						addUserToDatabase(
						emailTextField.getText(), encryptPassword(passwordTextField.getValue()));					
					} else{
						AlertMessageBox alert = new AlertMessageBox("Incorrect", "Passwords doesn't match");
						alert.show();
						return;
					}
				}
			}
			
		});

		holder.add(signUpButton);

		form.add(holder);
		return form;
	}

	/**
	 * 
	 * Encrypts the password before it is written to the database
	 * 
	 * @param password
	 * @return Encrypted password
	 */
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

	public void addUserToDatabase(String emailString, String password) {
		final String email = emailString;
		databaseConnection.createNewUser(email, password,
				new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(Void result) {
				Comav200.GetInstance().getAndSetUserIDFromDatabase(email);
				
			}
		});

	}
}
