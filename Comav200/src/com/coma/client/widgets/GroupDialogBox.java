package com.coma.client.widgets;

import java.util.Date;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.HandleGroups;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

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
		dialog.setHeadingText("Create new group");
		dialog.setPixelSize(300, 100);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.OK, PredefinedButton.CANCEL);

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		nameBox = new TextField();
		nameBox.setAllowBlank(false);
		nameBox.setEmptyText("Enter group name");

	    verticalLayoutContainer.add(new FieldLabel(nameBox, "Group Name"), new VerticalLayoutData(1, -1));
		
		dialog.setWidget(verticalLayoutContainer);

		// Add a handler to create the new group
		dialog.getButton(PredefinedButton.OK).addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				String groupName = nameBox.getValue();
				int userID = User.getInstance().getUserId();

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
