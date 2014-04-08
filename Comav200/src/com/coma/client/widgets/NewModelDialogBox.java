package com.coma.client.widgets;

import java.util.Date;

import com.coma.client.Comav200;
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

	
	private TextBox modelNameBox;
	private ListBox modelTypeBox;

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
		modelNameBox = new TextBox();
		dialogVPanel.add(modelNameBox);

		modelTypeBox = new ListBox();
		modelTypeBox.addItem("UML Class Diagram");
		modelTypeBox.addItem("UML Activity Diagram");
		modelTypeBox.addItem("UML Use Case Diagram");
		modelTypeBox.addItem("UML Sequence Diagram");
		modelTypeBox.addItem("UML State Diagram");
		
		modelTypeBox.setVisibleItemCount(1);
		
		dialogVPanel.add(modelTypeBox);
		dialogVPanel.add(sendButton);
		
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//need a different entrypoint
				//Comav200.GetInstance().clearOryx();
				String modelName = modelNameBox.getText();
				int modelType = modelTypeBox.getSelectedIndex();
				int userID = User.getInstance().getUserId();

				dialogBox.hide();

			}
		});

		return dialogBox;
	}
	
}
