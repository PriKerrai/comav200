package com.coma.client.widgets;


import com.coma.client.ActiveInvite;
import com.coma.client.HandleGroups;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class HandleGroupInvitesDialogBox {

	Dialog dialog = new Dialog();
		
	TextButton declineButton = new TextButton("Decline invite");
	TextButton acceptButton = new TextButton("Accept invite");
	TextButton cancelButton = new TextButton("Cancel");
	
	public Dialog createDialogBox(CellList<String> cellList){
		// Create the popup dialog box
              
        dialog.setHeadingText("Handle group invites");
        dialog.setPixelSize(400, 400);
		dialog.setHideOnButtonClick(true);
             
        acceptButton.getElement().setId("acceptButton");       
		declineButton.getElement().setId("declineButton");	
		cancelButton.getElement().setId("cancelButton");

		
		dialog.addButton(new TextButton("TEST", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		}));
		
		
        VerticalPanel dialogVPanel = new VerticalPanel();
        HorizontalPanel dialogHPanel = new HorizontalPanel();
        
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
       
        ScrollPanel scrollPanel = new ScrollPanel(cellList);
		scrollPanel.setHeight("600px");
		scrollPanel.setWidth("600px");
		dialogVPanel.add(scrollPanel);
		
		dialogHPanel.add(acceptButton);
		dialogHPanel.add(declineButton);
		dialogHPanel.add(cancelButton);
		
		dialogVPanel.add(dialogHPanel);

        //dialog.setWidget(dialogVPanel);

        acceptButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				new HandleGroups().acceptGroupInvite(ActiveInvite.getInstance().getGroupID(), ActiveInvite.getInstance().getInviteID());
                //dialogBox.hide();
			}
        	
        });
               
        declineButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				new HandleGroups().declineGroupInvite(ActiveInvite.getInstance().getInviteID());
			}
		});
        
        cancelButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				dialog.hide();
			}
		});
       
        return dialog;
}
}
