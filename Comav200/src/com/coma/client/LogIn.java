package com.coma.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.coma.shared.FieldVerifier;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;


public class LogIn {

	private final String LOGIN_FAILED = "Login Failed";
	private final String CHECK_CREDENTIALS = "Please check your credentials and try again.";

	private TextButton logInButton = new TextButton("Log In");
	private TextButton signUpButton = new TextButton("Sign Up");
	private TextField emailTextField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private String password = null;

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	private final int PANEL_WIDTH = 270;
	private final int PANEL_HEIGHT= 100;
	private boolean validEmail;

	public VerticalLayoutContainer screen() {
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.setWidth(PANEL_WIDTH);
		vlc.setHeight(PANEL_HEIGHT);
		final FormPanel form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.addStyleName("table-center");
		form.addStyleName("demo-FormPanel");

		VerticalPanel holder = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();

		emailTextField.setAllowBlank(false);
		holder.add(new Image("http://i.imgur.com/DVvpqtk.png"));
		holder.add(new FieldLabel(emailTextField, "Email"));

		holder.add(new FieldLabel(passwordField, "Password"));

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

		int windowHeight = Window.getClientHeight();
		int windowWidth = Window.getClientWidth();
		vlc.add(form);
		vlc.setPosition((windowWidth-PANEL_WIDTH)/2, (windowHeight-PANEL_HEIGHT)/2);

		return vlc;
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

		AlertMessageBox alert = new AlertMessageBox(LOGIN_FAILED, CHECK_CREDENTIALS);
		String encryptedPassword = encryptPassword(passwordField.getValue());
		String emailText = emailTextField.getText();

		boolean isValidEmail = FieldVerifier.isValidEmail(email);
		boolean isValidPassword = FieldVerifier.isValidPassword(password);

		if(!isValidEmail) {
			alert.show();
			return;
		}
		if(!isValidPassword) {
			alert.show();
			return;
		}
		if (encryptedPassword.equals(password)) {
			initUserInfo(emailText);    
			User.getInstance().setUserEmail(emailTextField.getText());
		} else {
			alert.show();
			return;
		}
	}

	/**
	 *Gets active users ID from database and sets the ID in the User class
	 */
	public void initUserInfo(final String email) {
		databaseConnection.getUserID(email, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Integer result) {
				// TODO Auto-generated method stub
				User.getInstance().setUserId(result);
				getAndSetUserName(email);			             
			}
		});
	}

	private void getAndSetUserName(String email) {
		databaseConnection.getUserName(email, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(String result) {
				User.getInstance().setUserName(result);
				setActiveGroup();
			}
		});
	}

	private void setActiveGroup(){
		databaseConnection.getLatestJoinedGroup(User.getInstance().getUserId(), new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Integer result) {
				databaseConnection.getGroupInfo(result, new AsyncCallback<WorkGroupInfo>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(WorkGroupInfo result) {
						User.getInstance().setActiveGroupID(result.getWorkGroupID());
						User.getInstance().setActiveGroupName(result.getWorkGroupName());
						Comav200.GetInstance().initMainProgram(); 
					}
				});
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
