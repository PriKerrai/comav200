package com.coma.client.widgets;

import java.util.List;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.coma.client.WorkGroupInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SwitchGroupDialogBox {
	private ListBox groupBox;
    private final DatabaseConnectionAsync databaseConnection = GWT
                    .create(DatabaseConnection.class);
   
    public DialogBox createDialogBox(List<WorkGroupInfo> groups){
    		// Create the popup dialog box
    		final List<WorkGroupInfo> groupList = groups;
            final DialogBox dialogBox = new DialogBox();
            dialogBox.setAnimationEnabled(true);
            dialogBox.setText("Switch active group");
           
            final Button sendButton = new Button("Send");
            sendButton.getElement().setId("sendButton");
            final Button cancelButton = new Button("Cancel");
    		cancelButton.getElement().setId("cancelButton");
            VerticalPanel dialogVPanel = new VerticalPanel();
            dialogVPanel.addStyleName("dialogVPanel");
            dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
           
            groupBox = new ListBox();
            for(WorkGroupInfo wgi: groupList){
            	groupBox.addItem(wgi.getWorkGroupName());
            }
            
            dialogVPanel.add(new Label("Choose group"));
            dialogVPanel.add(groupBox);
            dialogVPanel.add(cancelButton);
            dialogVPanel.add(sendButton);
            dialogBox.setWidget(dialogVPanel);

            // Add a handler to close the DialogBox
            sendButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                    	User.getInstance().setActiveGroupID(groupList.get(groupBox.getSelectedIndex()).getWorkGroupID());
                        dialogBox.hide();
                    }
            });
            cancelButton.addClickHandler(new ClickHandler() {
    			public void onClick(ClickEvent event) {
    				dialogBox.hide();

    			}
    		});
            return dialogBox;
    }
    
}
