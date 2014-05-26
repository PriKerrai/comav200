package com.coma.client.widgets;

import java.util.List;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;


public class CommentsDialogBox{

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	private final static int DIALOG_WIDTH = 500;
	private final static int DIALOG_HEIGHT = 400;
	
	private int activeModelID;
	private List<String> commentList;

	private Dialog dialog;

	private FramedPanel panel; 

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

	public CommentsDialogBox() {
	}

	public CommentsDialogBox(int modelID) {
		setModelID(modelID);
		getCommentsOnModel(activeModelID);
	}

	public Dialog createDialogBox(){

		// Create the popup dialog box
		dialog = new Dialog();
		//dialog.setHeadingText("Leave a comment on the model");
		dialog.setHideOnButtonClick(true);
		dialog.setWidth(DIALOG_WIDTH);
		dialog.setHeight(DIALOG_HEIGHT);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

		final TextArea writeCommentTextArea = new TextArea();
		writeCommentTextArea.setEmptyText("Write a comment...");
		writeCommentTextArea.setPixelSize(DIALOG_WIDTH-5, (DIALOG_HEIGHT/2)-5);

		final TextArea readCommentTextArea = new TextArea();
		readCommentTextArea.setPixelSize(DIALOG_WIDTH-5, (DIALOG_HEIGHT/2)-5);
		
		StringBuilder comments = new StringBuilder();
		for (int i = 0; i < commentList.size(); i++) {
			comments.append(commentList.get(i) + "\n");
		}

		readCommentTextArea.setText(comments.toString());
		readCommentTextArea.setEmptyText("Comments on the model will be shown here...");
		readCommentTextArea.setAllowTextSelection(false);
		readCommentTextArea.setEnabled(false);

		// Add a handler to create the new group
		dialog.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				
				// TODO Auto-generated method stub
				String comment = writeCommentTextArea.getText();
				if(comment.equals("")) {
					System.out.println("Tomt var det h�r");
					dialog.hide();
				}
				else {
				comment = comment + "  //" + User.getInstance().getUserName();
				int userID = User.getInstance().getUserId();
				
								
				addComment(userID, getModelID(), comment);
				dialog.hide();
				}
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
		
		panel = new FramedPanel();
		panel.setLayoutData(new MarginData(1));
		panel.setCollapsible(true);
		panel.setHeadingText("Comments on model");
		panel.setPixelSize(620, 500);
		panel.setBodyBorder(true);

		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		panel.add(layout);

		layout.add(writeCommentTextArea);
		layout.add(readCommentTextArea);
		
		panel.add(layout);
		dialog.setWidget(layout);
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
