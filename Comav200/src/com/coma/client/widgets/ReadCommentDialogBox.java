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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ReadCommentDialogBox{
    private TextBox nameBox;
    
    private final DatabaseConnectionAsync databaseConnection = GWT
                    .create(DatabaseConnection.class);
   
    public DialogBox createDialogBox(){
    		// Create the popup dialog box
            final DialogBox dialogBox = new DialogBox();
            dialogBox.setAnimationEnabled(true);
            dialogBox.setText("Write comment");
           
            final Button sendButton = new Button("Send");
            sendButton.getElement().setId("sendButton");
            final Button closeButton = new Button("close");
            closeButton.getElement().setId("closeButton");
            
            VerticalPanel dialogVPanel = new VerticalPanel();
            dialogVPanel.addStyleName("dialogVPanel");
            dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
           
            dialogVPanel.add(new Label("Write comment:"));
           
            final TextArea ta = new TextArea();
            ta.setCharacterWidth(50);
            ta.setVisibleLines(20);
           
            dialogVPanel.add(ta);
           
            dialogVPanel.add(sendButton);
            dialogVPanel.add(closeButton);
            dialogBox.setWidget(dialogVPanel);
            dialogBox.setAnimationEnabled(false);

            // Add a handler to close the DialogBox
            sendButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                            String comment = ta.getText();
                            int userID = User.getInstance().getUserId();

                            addComment(userID, comment);

                            dialogBox.hide();

                    }
            });
            
            closeButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {

                        dialogBox.hide();

                }
        });
           
            return dialogBox;
    }

    protected void addComment(int userID, String comment) {
    	
    	databaseConnection.addCommentToModel(userID, comment, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Void result) {
                    // TODO Auto-generated method stub
                   
            }
    });
		
	}
}
