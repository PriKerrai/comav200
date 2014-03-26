package com.coma.client;

import com.coma.shared.FieldVerifier;
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
	TextBox userid = new TextBox();
	PasswordTextBox passwd = new PasswordTextBox();
	
        public FormPanel screen(){
        	final FormPanel form = new FormPanel();
        	form.setEncoding(FormPanel.ENCODING_MULTIPART);
        	form.setMethod(FormPanel.METHOD_POST);
        	form.addStyleName("table-center");
        	form.addStyleName("demo-FormPanel");
        	
        	VerticalPanel holder = new VerticalPanel();
        	
        	holder.add(new Label("User ID"));
        	userid.setName("userid");
        	holder.add(userid);
        	
        	holder.add(new Label("Password"));
        	passwd.setName("password");
        	holder.add(passwd);

        	MyHandler handler = new MyHandler();
            logInButton.addClickHandler(handler);
            signUpButton.addClickHandler(handler);
        	
        	holder.add(logInButton);
        	holder.add(signUpButton);

            form.add(holder);    
            return form;
        }
        
        class MyHandler implements ClickHandler{

            @Override
            public void onClick(ClickEvent event) {

                    if(event.getSource().equals(logInButton)){
                    	//check authentication
                    	//
                    	Comav200.GetInstance().initializeOryx();
                    }
                    if(event.getSource().equals(signUpButton)){
                    	System.out.print(userid.getText());
                    	Comav200.GetInstance().initializeSignUp();
                    }

            }
    }
        

}
