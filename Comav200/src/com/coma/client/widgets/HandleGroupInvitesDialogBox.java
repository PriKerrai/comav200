package com.coma.client.widgets;


import com.coma.client.ActiveInvite;
import com.coma.client.HandleGroups;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HandleGroupInvitesDialogBox {

	
	public DialogBox createDialogBox(CellList<String> cellList){
		// Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setAnimationEnabled(true);
        dialogBox.setText("Handle group invites");
       
        final Button acceptButton = new Button("Accept invite");
        acceptButton.getElement().setId("acceptButton");
        final Button declineButton = new Button("Decline invite");
		declineButton.getElement().setId("declineButton");
		final Button cancelButton = new Button("Close");
		cancelButton.getElement().setId("cancelButton");
        
        VerticalPanel dialogVPanel = new VerticalPanel();
        HorizontalPanel dialogHPanel = new HorizontalPanel();
        
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
       
        ScrollPanel scrollPanel = new ScrollPanel(cellList);
		scrollPanel.setHeight("300px");
		scrollPanel.setWidth("300px");
		dialogVPanel.add(scrollPanel);
		
		dialogHPanel.add(acceptButton);
		dialogHPanel.add(declineButton);
		dialogHPanel.add(cancelButton);
		
		dialogVPanel.add(dialogHPanel);

        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        acceptButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                	new HandleGroups().acceptGroupInvite(ActiveInvite.getInstance().getGroupID(), ActiveInvite.getInstance().getInviteID());
                    //dialogBox.hide();
                }
        });
        
        declineButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				new HandleGroups().declineGroupInvite(ActiveInvite.getInstance().getInviteID());
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
