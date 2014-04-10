package com.coma.client.widgets;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class VoteDialogBox{
	final ListBox listBox = new ListBox(); 
	final String[] Items = { "0", "1", "2","3", "4", "5", "6", "7", "8", "9", "10" };

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	public int activeModelID;

	public int getModelID() {
		return activeModelID;
	}

	public void setModelID(int modelID) {
		this.activeModelID = modelID;
	}

	public VoteDialogBox() {
	}

	public VoteDialogBox(int modelID) {
		setModelID(modelID);
	}

	public DialogBox createDialogBox(){
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText("Leave a vote");

		final Button sendButton = new Button("Send");
		sendButton.getElement().setId("sendButton");
		final Button closeButton = new Button("close");
		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		dialogVPanel.add(new Label("Write comment:"));

		// Initialize the List with data from Item0
		for (int i = 0; i < Items.length; i++) {
			listBox.addItem(Items[i]);
		}

		listBox.setVisibleItemCount(1);	        

		dialogVPanel.add(listBox);

		dialogVPanel.add(sendButton);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		dialogBox.setAnimationEnabled(false);

		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = listBox.getSelectedIndex();
				int userID = User.getInstance().getUserId();
				addVoteToModel(userID, getModelID(), index);
				dialogBox.hide();

			}
		});

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				dialogBox.hide();
			}
		});

		return dialogBox;
	}

	protected void addVoteToModel(int userID, int modelID, int index) {

		databaseConnection.addVoteToModel(userID, modelID, index, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Success from addVoteToModel");

			}
		});
	}}
