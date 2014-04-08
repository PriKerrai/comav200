package com.coma.client.widgets;

import java.awt.TextField;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class WriteCommentDialogBox{

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	private int activeModelID;
	private List<String> commentList; 

	public List<String> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<String> commentList) {
		this.commentList = commentList;
	}
	
	public int getModelID() {
		return activeModelID;
	}

	public void setModelID(int modelID) {
		this.activeModelID = modelID;
	}

	public WriteCommentDialogBox() {
	}

	public WriteCommentDialogBox(int modelID) {
		setModelID(modelID);
		getCommentsOnModel(activeModelID);
	}

	public DialogBox createDialogBox(){
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText("Write comment");

		final Button sendButton = new Button("Send");
		sendButton.getElement().setId("sendButton");
		final Button closeButton = new Button("close");
		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		dialogVPanel.add(new Label("Write comment:"));
		
		final TextArea writeCommentTextArea = new TextArea();
		writeCommentTextArea.setCharacterWidth(50);
		writeCommentTextArea.setVisibleLines(10);
		
		final TextArea readCommentTextArea = new TextArea();
		readCommentTextArea.setCharacterWidth(50);
		readCommentTextArea.setVisibleLines(10);
		
		StringBuilder comments = new StringBuilder();
		for (int i = 0; i < commentList.size(); i++) {
				comments.append(commentList.get(i) + "\n");
				
			}
		
		readCommentTextArea.setText(comments.toString());

		dialogVPanel.add(readCommentTextArea);
		dialogVPanel.add(writeCommentTextArea);

		dialogVPanel.add(sendButton);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		dialogBox.setAnimationEnabled(false);

		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String comment = writeCommentTextArea.getText();
				int userID = User.getInstance().getUserId();

				addComment(userID, getModelID(), comment);
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

	protected void getCommentsOnModel(int modelID) {

		databaseConnection.getCommentsOnModel(modelID, new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<String> result) {
				
				setCommentList(result);
				DialogBox dialogBox = createDialogBox();
				dialogBox.center();
				dialogBox.show();
			}
		});

	}
	
	protected void addComment(int userID, int modelID, String comment) {

		databaseConnection.addCommentToModel(userID, modelID, comment, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Success from addComment");

			}
		});

	}

}
