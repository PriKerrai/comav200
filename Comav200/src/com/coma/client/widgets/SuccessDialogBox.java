package com.coma.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SuccessDialogBox {
	
	public DialogBox successDialogBox(String action){
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText("Success!");

		final Button sendButton = new Button("Send");
		sendButton.getElement().setId("sendButton");
		
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		if(action.equals("SAVE")){
			dialogVPanel.add(new Label("Saved successfully"));
		}else if(action.equals("PROPOSAL")){
			dialogVPanel.add(new Label("Proposal has been sent"));
		}
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
