package com.coma.client.widgets;

import java.util.Date;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NewModelDialogBox {

	
	private TextBox nameBox;

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public DialogBox createDialogBox( ){
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText("New model");

		final Button sendButton = new Button("Send");
		sendButton.getElement().setId("sendButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		dialogVPanel.add(new Label("Model name:"));
		nameBox = new TextBox();
		dialogVPanel.add(nameBox);

		ListBox typeBox = new ListBox();
		typeBox.addItem("UML Class Diagram");
		typeBox.addItem("UML Activity Diagram");
		typeBox.addItem("UML Use Case Diagram");
		typeBox.addItem("UML Sequence Diagram");
		typeBox.addItem("UML State Diagram");
		
		typeBox.setVisibleItemCount(1);
		
		dialogVPanel.add(typeBox);
		dialogVPanel.add(sendButton);
		
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String groupName = nameBox.getText();
				int userID = User.getInstance().getUserId();
				java.util.Date date = new Date();

				//createNewGroup(userID, groupName);

				dialogBox.hide();

			}
		});

		return dialogBox;
	}
	
}
