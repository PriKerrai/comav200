package com.coma.client;

import java.awt.TextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditProfileView {
	
	Button editProfileButton = new Button("Edit Profile");
	Button saveProfileButton = new Button("Save Profile");
	Button cancelEditButton = new Button("Cancel");
	TextBox firstNameTextBox = new TextBox();
	TextBox surNameTextBox = new TextBox();
	//TextArea userDescriptionTextArea = new TextArea();
	TextBox birthDay = new TextBox();
	TextBox phoneNumber = new TextBox();

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
        public FormPanel screen(){
        	final FormPanel form = new FormPanel();
        	form.setEncoding(FormPanel.ENCODING_MULTIPART);
        	form.setMethod(FormPanel.METHOD_POST);
        	form.addStyleName("table-center");
        	form.addStyleName("demo-FormPanel");
        	
        	VerticalPanel holder = new VerticalPanel();
        	
        	holder.add(new Label("First Name"));
        	firstNameTextBox.setName("firstName");
        	firstNameTextBox.setEnabled(false);
        	holder.add(firstNameTextBox);
        	
        	holder.add(new Label("Surname"));
        	surNameTextBox.setName("surName");
        	surNameTextBox.setEnabled(false);
        	holder.add(surNameTextBox);

        	//holder.add(new Label("User description"));
        	//userDescriptionTextArea.setName("surName");
        	//holder.add(userDescriptionTextArea);
        	
        	holder.add(new Label("Bithday"));
        	birthDay.setName("birtday");
        	birthDay.setEnabled(false);
        	holder.add(birthDay);
        	
        	holder.add(new Label("Phone number"));
        	phoneNumber.setName("password");
        	phoneNumber.setEnabled(false);
        	holder.add(phoneNumber);

        	MyHandler handler = new MyHandler();
        	editProfileButton.addClickHandler(handler);
        	cancelEditButton.addClickHandler(handler);
        	cancelEditButton.setEnabled(false);
        	saveProfileButton.addClickHandler(handler);
        	saveProfileButton.setEnabled(false);
        	
        	holder.add(editProfileButton);
        	holder.add(saveProfileButton);
        	holder.add(cancelEditButton);

            form.add(holder);    
            return form;
        }

       
        class MyHandler implements ClickHandler{

            @Override
            public void onClick(ClickEvent event) {

                    if(event.getSource().equals(editProfileButton)){                  	
                    	firstNameTextBox.setEnabled(true);
                    	surNameTextBox.setEnabled(true);
                    	birthDay.setEnabled(true);
                    	phoneNumber.setEnabled(true);
                    	saveProfileButton.setEnabled(true);
                    	cancelEditButton.setEnabled(true);
                    }
                    if(event.getSource().equals(cancelEditButton)){
                    	firstNameTextBox.setEnabled(false);
                    	surNameTextBox.setEnabled(false);
                    	birthDay.setEnabled(false);
                    	phoneNumber.setEnabled(false);
                    	saveProfileButton.setEnabled(false);
                    	
                    }
                    if(event.getSource().equals(saveProfileButton)){
                    	String fName = firstNameTextBox.getText();
                    	String sName = surNameTextBox.getText();
                    	String bDay = birthDay.getText();
                    	String phoneNr = phoneNumber.getText();
                    	System.out.println(fName + sName + bDay + phoneNr);
                    	editProfileButton.setEnabled(false);
                    		
                    }
            }
    }
	
}
