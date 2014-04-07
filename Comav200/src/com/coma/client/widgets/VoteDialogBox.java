package com.coma.client.widgets;

import java.awt.TextField;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class VoteDialogBox{
	final ListBox listBox = new ListBox(); 
	final String[] Items = { "0", "1", "2","3", "4", "5", "6", "7", "8", "9", "10" };

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

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

		listBox.setVisibleItemCount(10);	        

		dialogVPanel.add(listBox);

		dialogVPanel.add(sendButton);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		dialogBox.setAnimationEnabled(false);

		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

		int index = listBox.getSelectedIndex();
		String myValue = listBox.getValue(index);
		System.out.print("\nIndex: " + index);
		System.out.print("\nValue:" + myValue);

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

	protected void addVoteToModel(int userID, int index) {

		databaseConnection.addVoteToModel(userID, index, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

			}
		});
	}}
