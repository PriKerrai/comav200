package com.coma.client.widgets;

import java.util.List;

import com.coma.client.Comav200;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

public class UserProfileFormPanel {

	TextButton editProfileButton = new TextButton("Edit Profile");
	TextButton saveProfileButton = new TextButton("Save Profile");
	TextButton cancelEditButton = new TextButton("Cancel");
	TextField firstNameTextField = new TextField();
	TextField lastNametextField = new TextField();
	TextArea userBiography = new TextArea();
	TextField birthdate = new TextField();
	TextField phoneNumber = new TextField();
	
	private List<String> userProfile;
	
	public List<String> getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(List<String> userProfile) {
		this.userProfile = userProfile;
	}

	public ContentPanel createUserProfilePanel(){
		ContentPanel contentPanel = new ContentPanel();
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		final FormPanel form = new FormPanel();

		VerticalPanel holder = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();

		firstNameTextField.setEmptyText("Your first name..");
		lastNametextField.setEmptyText("Your last name..");
		birthdate.setEmptyText("When were you born..");
		phoneNumber.setEmptyText("Your phone number..");
		userBiography.setEmptyText("Write something about you..");
		userBiography.setHeight(200);
		
        holder.add(new FieldLabel(firstNameTextField, "First name"));
        holder.add(new FieldLabel(lastNametextField, "Last name"));
        holder.add(new FieldLabel(birthdate, "Birthdate"));
        holder.add(new FieldLabel(phoneNumber, "Phone number"));
        holder.add(new FieldLabel(userBiography, "User Biography"));
        
		if (userProfile.size() > 1) {        	        	
			firstNameTextField.setText(userProfile.get(0));
			lastNametextField.setText(userProfile.get(1));
			birthdate.setText(userProfile.get(2));
			phoneNumber.setText(userProfile.get(3));
		}
		
		firstNameTextField.setEnabled(false);
		lastNametextField.setEnabled(false);
		birthdate.setEnabled(false);
		phoneNumber.setEnabled(false);
		userBiography.setEnabled(false);
		editProfileButton.setEnabled(true);
		cancelEditButton.setEnabled(false);
		saveProfileButton.setEnabled(false);

		editProfileButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				firstNameTextField.setEnabled(true);
				lastNametextField.setEnabled(true);
				birthdate.setEnabled(true);
				phoneNumber.setEnabled(true);
				saveProfileButton.setEnabled(true);
				cancelEditButton.setEnabled(true);
				editProfileButton.setEnabled(false);
				userBiography.setEnabled(true);
				
			}
			
		});
		cancelEditButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				firstNameTextField.setEnabled(false);
				lastNametextField.setEnabled(false);
				birthdate.setEnabled(false);
				phoneNumber.setEnabled(false);
				saveProfileButton.setEnabled(false);
				userBiography.setEnabled(false);
				editProfileButton.setEnabled(true);
				cancelEditButton.setEnabled(false);
				userBiography.setEnabled(false);
				
			}
		});

		saveProfileButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				String fName = firstNameTextField.getText();
				String sName = lastNametextField.getText();
				String bDay = birthdate.getText();
				String phoneNr = phoneNumber.getText();
				Comav200.GetInstance().addUserProfileToUser(fName, sName, bDay, phoneNr);
				editProfileButton.setEnabled(true);
				cancelEditButton.setEnabled(false);
				saveProfileButton.setEnabled(false);
				
			}
			
		});

		hPanel.add(editProfileButton);
		hPanel.add(saveProfileButton);
		hPanel.add(cancelEditButton);
		holder.add(hPanel);

		form.setTitle("User Profile");
		form.add(holder);
		vlc.add(form);
		contentPanel.add(vlc);
		contentPanel.setHeadingHtml("User profile");
		return contentPanel;
	}

}
