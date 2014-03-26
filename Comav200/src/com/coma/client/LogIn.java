package com.coma.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LogIn {

	
	public boolean hello = false;
	
        public Panel screen(){
                VerticalPanel vPanel = new VerticalPanel();
                TextBox emailTextBox = new TextBox();
                PasswordTextBox passwordTextBox = new PasswordTextBox();
                Label emailLabel = new Label("Email:");
                Label passwordLabel = new Label("Password:");
                Button logInButton = new Button("Log In");
                logInButton.getElement().setClassName("diagramButton");
               
                vPanel.add(emailLabel);
                vPanel.add(emailTextBox);
                vPanel.add(passwordLabel);
                vPanel.add(passwordTextBox);
                vPanel.add(logInButton);

               
                return vPanel;
        }

}
