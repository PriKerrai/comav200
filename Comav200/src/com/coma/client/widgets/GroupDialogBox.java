package com.coma.client.widgets;

import java.util.Date;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.HandleGroups;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Creates the dialog for creating a new group
 * @author Martin Nilsson and Johan Magnusson
 *
 */
public class GroupDialogBox{
	private TextField nameBox;

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	private Dialog dialog;

	/**
	 * 
	 * @return the created dialog for creating a group
	 */
	public Dialog createDialogBox(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Save as new group model");
		dialog.setWidget(new HTML("Are you sure this is the diagram\n you want to accept as the new group model?\n"));
		dialog.setPixelSize(300, 100);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		nameBox = new TextField();
		nameBox.setAllowBlank(false);
		nameBox.setEmptyText("Enter the group name...");

	    verticalLayoutContainer.add(new FieldLabel(nameBox, "Group Name"), new VerticalLayoutData(1, -1));
		
		dialog.setWidget(verticalLayoutContainer);

		// Add a handler to create the new group
		dialog.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				String groupName = nameBox.getValue();
				int userID = User.getInstance().getUserId();
				java.util.Date date = new Date();

				createNewGroup(userID, groupName);

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

		dialog.show();
		return dialog;

	}

	/**
	 * 
	 * @param userID The userID of the logged in user
	 * @param groupName The specified group name that the user has inputed 
	 */
	public void createNewGroup(int userID, String groupName) {

		databaseConnection.createNewGroup(userID, groupName, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Integer result) {
				new HandleGroups().addUserToGroup(result);

			}
		});
	}
}
