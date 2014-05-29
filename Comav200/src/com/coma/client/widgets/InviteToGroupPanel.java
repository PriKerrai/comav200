package com.coma.client.widgets;

import com.coma.client.HandleGroups;
import com.coma.client.User;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class InviteToGroupPanel {

	private Dialog dialog;
	private TextField emailTextBox;
	private TextButton sendGroupInviteButton = new TextButton("Send invite");
	private TextButton cancelButton = new TextButton("Cancel");
   
    public ContentPanel inviteToGroupPanel(){
    	
    	ContentPanel contentPanel = new ContentPanel();
    	VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    	
    	emailTextBox = new TextField();
		emailTextBox.setAllowBlank(false);
		emailTextBox.setEmptyText("Enter email to invite...");
		vlc.add(new FieldLabel(emailTextBox, "Email: "), new VerticalLayoutData(1, -1));
		
		HorizontalPanel hPanel = new HorizontalPanel();

		cancelButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				emailTextBox.clear();
			}
		});

		sendGroupInviteButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				new HandleGroups().sendGroupInvite(emailTextBox.getText());
				Info.display("Group invite", "Group invite sent" + " to " + emailTextBox.getText());
				System.out.println("Send email in the future");
				emailTextBox.clear();
			}
			
		});
		
		hPanel.add(sendGroupInviteButton);
		hPanel.add(cancelButton);
		
		vlc.add(hPanel);
		contentPanel.setHeadingHtml("Invite to group");
		contentPanel.add(vlc);
		return contentPanel;
    	
    }

}
