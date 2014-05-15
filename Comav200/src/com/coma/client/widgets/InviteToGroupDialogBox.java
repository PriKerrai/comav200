package com.coma.client.widgets;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.HandleGroups;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

public class InviteToGroupDialogBox {
    
    private final DatabaseConnectionAsync databaseConnection = GWT
                    .create(DatabaseConnection.class);
	private Dialog dialog;
	private TextField emailTextBox;
   
    public Dialog createInviteToGroupDialog(){
    	// Create the popup dialog box
    			dialog = new Dialog();
    			dialog.setHeadingText("Invite to group");
    			dialog.setWidget(new HTML("Invite a user to join your group?"));
    			dialog.setPixelSize(300, 100);
    			dialog.setHideOnButtonClick(true);
    			dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

    			dialog.getButtonBar().getItemByItemId("YES").setTitle("changed");
    			
    			VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
    			verticalLayoutContainer.addStyleName("dialogVPanel");

    			emailTextBox = new TextField();
    			emailTextBox.setAllowBlank(false);
    			emailTextBox.setEmptyText("Enter email to invite...");
    		    verticalLayoutContainer.add(new FieldLabel(emailTextBox, "Email: "), new VerticalLayoutData(1, -1));
    		    

    		    dialog.setWidget(verticalLayoutContainer);

    			// Add a handler to create the new group
    			dialog.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {
    				
    				@Override
    				public void onSelect(SelectEvent event) {
    					// TODO Auto-generated method stub
    					System.out.println("Send email in the future");
    					dialog.hide();
    					
    				}
    			});
    			//Add a handler to close the dialog
    			dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
    				@Override
    				public void onSelect(SelectEvent event) {
    					dialog.hide();
    				}
    			});

    			dialog.show();
    			return dialog;
    }

}
