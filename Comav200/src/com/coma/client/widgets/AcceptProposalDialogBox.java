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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class AcceptProposalDialogBox{
        private TextBox versionBox;
       
        private final DatabaseConnectionAsync databaseConnection = GWT
                        .create(DatabaseConnection.class);

		private int activeModelID;
        
        public int getModelID() {
    		return activeModelID;
    	}

    	public void setModelID(int modelID) {
    		this.activeModelID = modelID;
    	}
       
        public DialogBox createDialogBox(){
        		// Create the popup dialog box
                final DialogBox dialogBox = new DialogBox();
                dialogBox.setAnimationEnabled(true);
                dialogBox.setText("Create group");
               
                final Button acceptProposalButton = new Button("Use as group model");
                acceptProposalButton.getElement().setId("acceptProposalButton");
                VerticalPanel dialogVPanel = new VerticalPanel();
                dialogVPanel.addStyleName("dialogVPanel");
                dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
                
                final Button cancelButton = new Button("Cancel");
                cancelButton.getElement().setId("cancelButton");
                dialogVPanel.addStyleName("dialogVPanel");
                dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
               
                dialogVPanel.add(new Label("Are you sure this is the diagram\n you want to accept as the new group model?\n"));
               
                versionBox = new TextBox();
                dialogVPanel.add(new Label("Please enter version: "));
                dialogVPanel.add(versionBox);
               
                dialogVPanel.add(acceptProposalButton);
                dialogVPanel.add(cancelButton);
                dialogBox.setWidget(dialogVPanel);

                // Add a handler to close the DialogBox
                acceptProposalButton.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                                int activeGroupID = User.getInstance().getActiveGroupID();
                                int modelID = getModelID();
                                String version = versionBox.getText();
                                
                                updateActiveGroupModel(1, modelID, version);
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

        public void updateActiveGroupModel(int activeGroupID, int modelID, String version) {
                databaseConnection.updateActiveGroupModel(activeGroupID, modelID, version, new AsyncCallback<Void>() {
                                        public void onFailure(Throwable caught) {
                                        }

                                        @Override
                                        public void onSuccess(Void result) {
                                                // TODO Auto-generated method stub
                                               
                                        }
                                });
                }
}
