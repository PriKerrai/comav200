package com.coma.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;

public class EditProfileView {

	TextButton editProfileButton = new TextButton("Edit Profile");
	TextButton saveProfileButton = new TextButton("Save Profile");
	TextButton cancelEditButton = new TextButton("Cancel");
	TextField firstNameTextField = new TextField();
	TextField surNameTextField = new TextField();
	//TextArea userDescriptionTextArea = new TextArea();
	TextField birthDay = new TextField();
	TextField phoneNumber = new TextField();
	VerticalPanel holder = new VerticalPanel();

	public FormPanel screen(List<String> userProfile){
		final FormPanel form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.addStyleName("table-center");
		form.addStyleName("demo-FormPanel");

		VerticalPanel holder = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		HorizontalPanel fnamePanel = new HorizontalPanel();
		HorizontalPanel surNamePanel = new HorizontalPanel();
		HorizontalPanel birthdayPanel = new HorizontalPanel();
		HorizontalPanel phonePanel = new HorizontalPanel();
		
		
		//holder.add(new Label("First Name"));
		firstNameTextField.setName("firstName");
		firstNameTextField.setEnabled(false);

		//holder.add(new Label("Surname"));
		surNameTextField.setName("surName");
		surNameTextField.setEnabled(false);

		//holder.add(new Label("Bithday"));
		birthDay.setName("birtday");
		birthDay.setEnabled(false);

		//holder.add(new Label("Phone number"));
		phoneNumber.setName("password");
		phoneNumber.setEnabled(false);

		if (userProfile.size() > 1) {        	        	
			firstNameTextField.setText(userProfile.get(0));
			surNameTextField.setText(userProfile.get(1));
			birthDay.setText(userProfile.get(2));
			phoneNumber.setText(userProfile.get(3));
		}

		fnamePanel.add(new Label("First name: "));
		fnamePanel.add(firstNameTextField);
		holder.add(fnamePanel);
		
		surNamePanel.add(new Label("Last name: "));
		surNamePanel.add(surNameTextField);
		holder.add(surNamePanel);
		
		birthdayPanel.add(new Label("Birthday: "));
		birthdayPanel.add(birthDay);
		holder.add(birthdayPanel);

		phonePanel.add(new Label("Phone number: "));
		phonePanel.add(phoneNumber);
		holder.add(phonePanel);
		
		//holder.add(surNameTextBox);
		//holder.add(birthDay);        	
		//holder.add(phoneNumber);

		//holder.add(new Label("User description"));
		//userDescriptionTextArea.setName("surName");
		//holder.add(userDescriptionTextArea);


		editProfileButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				firstNameTextField.setEnabled(true);
				surNameTextField.setEnabled(true);
				birthDay.setEnabled(true);
				phoneNumber.setEnabled(true);
				saveProfileButton.setEnabled(true);
				cancelEditButton.setEnabled(true);
				editProfileButton.setEnabled(false);
				
			}
			
		});
		cancelEditButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				firstNameTextField.setEnabled(false);
				surNameTextField.setEnabled(false);
				birthDay.setEnabled(false);
				phoneNumber.setEnabled(false);
				saveProfileButton.setEnabled(false);
				editProfileButton.setEnabled(true);
				cancelEditButton.setEnabled(false);
				
			}
		});
		
		cancelEditButton.setEnabled(false);
		saveProfileButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				String fName = firstNameTextField.getText();
				String sName = surNameTextField.getText();
				String bDay = birthDay.getText();
				String phoneNr = phoneNumber.getText();
				Comav200.GetInstance().addUserProfileToUser(fName, sName, bDay, phoneNr);
				editProfileButton.setEnabled(true);
				cancelEditButton.setEnabled(false);
				saveProfileButton.setEnabled(false);
				
			}
			
		});
		saveProfileButton.setEnabled(false);

		hPanel.add(editProfileButton);
		hPanel.add(saveProfileButton);
		hPanel.add(cancelEditButton);
		holder.add(hPanel);


		form.add(holder);    
		return form;
	}

}
