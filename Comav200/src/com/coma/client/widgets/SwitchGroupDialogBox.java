package com.coma.client.widgets;

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

public class SwitchGroupDialogBox {
	private TextBox nameBox;
    
    private final DatabaseConnectionAsync databaseConnection = GWT
                    .create(DatabaseConnection.class);
   
    public DialogBox createDialogBox(){
    		// Create the popup dialog box
            final DialogBox dialogBox = new DialogBox();
            dialogBox.setAnimationEnabled(true);
            dialogBox.setText("Switch active group");
           
            final Button sendButton = new Button("Send");
            sendButton.getElement().setId("sendButton");
            VerticalPanel dialogVPanel = new VerticalPanel();
            dialogVPanel.addStyleName("dialogVPanel");
            dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
           
            dialogVPanel.add(new Label("Här skall man kunna byta grupp"));
           
            dialogVPanel.add(sendButton);
            dialogBox.setWidget(dialogVPanel);

            // Add a handler to close the DialogBox
            sendButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                            dialogBox.hide();

                    }
            });
           
            return dialogBox;
    }
}
