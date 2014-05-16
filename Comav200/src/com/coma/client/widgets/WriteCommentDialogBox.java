package com.coma.client.widgets;

import java.util.Date;
import java.util.List;

import com.coma.client.Comav200;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.ModelInfo;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextArea;


public class WriteCommentDialogBox{

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	private int activeModelID;
	private List<String> commentList;

	private Dialog dialog; 

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

	public Dialog createDialogBox(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Leave and read comments on the model");
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		final TextArea writeCommentTextArea = new TextArea();
		writeCommentTextArea.setEmptyText("Write a comment...");
		writeCommentTextArea.setPixelSize(500, 200);

		final TextArea readCommentTextArea = new TextArea();
		readCommentTextArea.setEmptyText("Comments on the model will be shown here...");
		readCommentTextArea.setPixelSize(500, 200);
		
		StringBuilder comments = new StringBuilder();
		for (int i = 0; i < commentList.size(); i++) {
			comments.append(commentList.get(i) + "\n");
		}

		readCommentTextArea.setText(comments.toString());
		readCommentTextArea.isEnabled();

		verticalLayoutContainer.add(new FieldLabel(writeCommentTextArea), new VerticalLayoutData(-1, -1));
		verticalLayoutContainer.add(new FieldLabel(readCommentTextArea), new VerticalLayoutData(-1, -1));

		dialog.setWidget(verticalLayoutContainer);

		// Add a handler to create the new group
		dialog.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				String comment = writeCommentTextArea.getText();
				int userID = User.getInstance().getUserId();

				addComment(userID, getModelID(), comment);
				dialog.hide();

			}
		});
		//Add a handler to close the dialog
		dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				System.out.println("Hejsan, CANCEL");
				dialog.hide();
			}
		});
		return dialog;
	}

	protected void getCommentsOnModel(int modelID) {

		databaseConnection.getCommentsOnModel(modelID, new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<String> result) {

				setCommentList(result);
				Dialog dialogBox = createDialogBox();
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
