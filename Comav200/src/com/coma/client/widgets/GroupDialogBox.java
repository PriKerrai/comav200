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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class GroupDialogBox{
        private TextBox nameBox;
       
        private final DatabaseConnectionAsync databaseConnection = GWT
                        .create(DatabaseConnection.class);
       
        public DialogBox createDialogBox(){
        		// Create the popup dialog box
                final DialogBox dialogBox = new DialogBox();
                dialogBox.setAnimationEnabled(true);
                dialogBox.setText("Create group");
               
                final Button sendButton = new Button("Send");
                sendButton.getElement().setId("sendButton");
                VerticalPanel dialogVPanel = new VerticalPanel();
                dialogVPanel.addStyleName("dialogVPanel");
                dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
               
                dialogVPanel.add(new Label("Group name:"));
               
                nameBox = new TextBox();
               
                dialogVPanel.add(nameBox);
               
                dialogVPanel.add(sendButton);
                dialogBox.setWidget(dialogVPanel);

                // Add a handler to close the DialogBox
                sendButton.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                                String groupName = nameBox.getText();
                                int userID = User.getInstance().getUserId();
                                java.util.Date date = new Date();

                                createNewGroup(userID, groupName);

                                dialogBox.hide();

                        }
                });
               
                return dialogBox;
        }

        public void createNewGroup(int userID, String groupName) {
               
                databaseConnection.createNewGroup(userID, groupName, new AsyncCallback<Void>() {
                                        public void onFailure(Throwable caught) {
                                        }

                                        @Override
                                        public void onSuccess(Void result) {
                                                // TODO Auto-generated method stub
                                               
                                        }
                                });
                }
}
