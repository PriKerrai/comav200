package com.coma.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.coma.client.Comav200;
import com.coma.shared.FieldVerifier;

public class SignUp {

	TextButton signUpButton = new TextButton("Sign Up");
	TextButton backToLoginButton = new TextButton("Back to login");
	TextField emailTextField = new TextField();
	TextField nameTextField = new TextField();
	PasswordField passwordField = new PasswordField();
	PasswordField passwordFieldRepeated = new PasswordField();

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	private int PANEL_WIDTH = 270;
	private int PANEL_HEIGHT = 300;

	public VerticalLayoutContainer screen() {
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.setWidth(PANEL_WIDTH);
		final FormPanel form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.addStyleName("table-center");
		form.addStyleName("demo-FormPanel");

		VerticalPanel holder = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();

		nameTextField.setAllowBlank(false);
		holder.add(new FieldLabel(nameTextField, "First name"));

		emailTextField.setAllowBlank(false);
		holder.add(new FieldLabel(emailTextField, "Email"));
		holder.add(new FieldLabel(passwordField, "Password"));
		holder.add(new FieldLabel(passwordFieldRepeated, "Repeat password"));
		signUpButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {

				String name = nameTextField.getText();
				String email = emailTextField.getText();
				
				String password = passwordField.getValue();
				String passwordRepeated = passwordFieldRepeated.getValue();
				
				boolean isValidEmail = FieldVerifier.isValidEmail(email);
				boolean isValidPassword = FieldVerifier.isValidEmail(password);

				if (event.getSource().equals(signUpButton)) {

					if(name.length() <1){
						AlertMessageBox alert = new AlertMessageBox("No name?", "First name is required");
						alert.show();
						return;
					}

					if(!isValidEmail) {
						AlertMessageBox alert = new AlertMessageBox("Too short!", "Please enter a valid email adress");
						alert.show();
						return;
					}
					if(passwordField.getValue() == null) {
						AlertMessageBox alert = new AlertMessageBox("Too short!", "Password need to be between atleast 5 characters long");
						alert.show();
						return;
					}
					if (!isValidPassword) {
						AlertMessageBox alert = new AlertMessageBox("Too short!", "Password need to be between atleast 5 characters long");
						alert.show();
						return;
					}
					if (password.equals(passwordRepeated)) {
						addUserToDatabase(
								email, encryptPassword(password), name);					
					} else{
						AlertMessageBox alert = new AlertMessageBox("Incorrect", "Passwords doesn't match");
						alert.show();
						return;
					}
				}
			}

		});

		backToLoginButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				Comav200.GetInstance().initialize();
			}
		});

		hPanel.add(signUpButton);
		hPanel.add(backToLoginButton);
		holder.add(hPanel);
		form.add(holder);

		int windowHeight = Window.getClientHeight();
		int windowWidth = Window.getClientWidth();
		vlc.add(form);
		vlc.setPosition((windowWidth-PANEL_WIDTH )/2, (windowHeight-PANEL_HEIGHT)/2);
		return vlc;
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


	public void addUserToDatabase(final String email, final String password,final String name) {
		//getUserID is called to see if email exists
		databaseConnection.getUserID(email, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(Integer result) {
				if(result == 0){
					databaseConnection.createNewUser(email, password,
							new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {

						}

						public void onSuccess(Void result) {
							getAndSetUserIDFromDatabase(email, name);

						}
					});
				}else{
					AlertMessageBox alert = new AlertMessageBox("Email already exists", "An account with this email has already been registered");
					alert.show();
					return;
				}
			}	
		});


	}

	public void getAndSetUserIDFromDatabase(String email, String name) {
		final String fName = name;
		databaseConnection.getUserID(email, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Integer result) {
				User.getInstance().setUserId(result);
				User.getInstance().setUserName(fName);
				Comav200.GetInstance().addUserProfileToUser(fName, "", "", "");
				Comav200.GetInstance().initMainProgram();		
			}

		});
	}
	
	
}
