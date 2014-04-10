package com.coma.client;

import java.util.List;

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
	VerticalPanel holder = new VerticalPanel();

	public FormPanel screen(List<String> userProfile){
		Comav200.GetInstance().getUserProfile(User.getInstance().getUserId());
		final FormPanel form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.addStyleName("table-center");
		form.addStyleName("demo-FormPanel");

		VerticalPanel holder = new VerticalPanel();
		holder.add(new Label("First Name"));
		firstNameTextBox.setName("firstName");
		firstNameTextBox.setEnabled(false);

		holder.add(new Label("Surname"));
		surNameTextBox.setName("surName");
		surNameTextBox.setEnabled(false);

		holder.add(new Label("Bithday"));
		birthDay.setName("birtday");
		birthDay.setEnabled(false);

		holder.add(new Label("Phone number"));
		phoneNumber.setName("password");
		phoneNumber.setEnabled(false);

		if (userProfile.size() > 1) {        	        	
			firstNameTextBox.setText(userProfile.get(0));
			surNameTextBox.setText(userProfile.get(1));
			birthDay.setText(userProfile.get(2));
			phoneNumber.setText(userProfile.get(3));
		}

		holder.add(firstNameTextBox);
		holder.add(surNameTextBox);
		holder.add(birthDay);        	
		holder.add(phoneNumber);

		//holder.add(new Label("User description"));
		//userDescriptionTextArea.setName("surName");
		//holder.add(userDescriptionTextArea);

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
				Comav200.GetInstance().addUserProfileToUser(fName, sName, bDay, phoneNr);
				editProfileButton.setEnabled(false);

			}
		}
	}

}
