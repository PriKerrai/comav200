package com.coma.client;

import com.coma.client.LogIn.MyHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;

public class SignUp {

	Button signUpButton = new Button("Sign Up");
	TextBox emailTextBox = new TextBox();
	PasswordTextBox passwordTextbox = new PasswordTextBox();
	PasswordTextBox passwordRepeatedTextbox = new PasswordTextBox();

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
		emailTextBox.setName("emailTextBox");
		holder.add(emailTextBox);

		holder.add(new Label("Password"));
		passwordTextbox.setName("password");
		holder.add(passwordTextbox);

		holder.add(new Label("Repeat password"));
		passwordRepeatedTextbox.setName("passwordRepeated");
		holder.add(passwordRepeatedTextbox);

		MyHandler handler = new MyHandler();
		signUpButton.addClickHandler(handler);

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
	
	class MyHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			String password = passwordTextbox.getValue();
			String passwordRepeated = passwordRepeatedTextbox.getValue();

			if (event.getSource().equals(signUpButton)) {

				if (password.length() < 1) {
					return;
				}
				if (password.equals(passwordRepeated)) {
					addUserToDatabase(
							emailTextBox.getText(), encryptPassword(passwordTextbox.getValue()));
					
				} else {
					return;
				}

			}
		}
	}
}
