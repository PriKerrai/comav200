package com.coma.client.widgets;

import com.coma.client.HandleGroups;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GroupInvitesDialogBox {


	public DialogBox createDialogBox(){
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText("Invite to group");

		final Button sendButton = new Button("Send invite");
		sendButton.getElement().setId("sendButton");
		final Button cancelButton = new Button("Cancel");
		cancelButton.getElement().setId("cancelButton");

		final TextBox emailBox = new TextBox();

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		dialogVPanel.add(new Label("Email"));
		dialogVPanel.add(emailBox);
		dialogVPanel.add(cancelButton);
		dialogVPanel.add(sendButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String email = emailBox.getText();
				new HandleGroups().sendGroupInvite(email);
				dialogBox.hide();

			}
		});

		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();

			}
		});

		return dialogBox;
	}
}
