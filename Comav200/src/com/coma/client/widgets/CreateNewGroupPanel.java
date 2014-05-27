package com.coma.client.widgets;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.HandleGroups;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
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
public class CreateNewGroupPanel{
	private TextField nameBox;

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	TextButton createNewGroupButton = new TextButton("Create group");
	TextButton cancelButton = new TextButton("Cancel");

	/**
	 * 
	 * @return the created dialog for creating a group
	 */
	public VerticalLayoutContainer createNewGroupPanel(){
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		nameBox = new TextField();
		nameBox.setAllowBlank(false);
		nameBox.setEmptyText("Enter group name");
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

	    vlc.add(new FieldLabel(nameBox, "Group Name"), new VerticalLayoutData(1, -1));

		cancelButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				nameBox.clear();
				
			}
		});

		createNewGroupButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				String groupName = nameBox.getText();
				 int userID = User.getInstance().getUserId();

				 createNewGroup(userID, groupName);
				nameBox.clear();
			}
			
		});
		
		hPanel.add(createNewGroupButton);
		hPanel.add(cancelButton);
		vlc.add(hPanel);

		return vlc;

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
