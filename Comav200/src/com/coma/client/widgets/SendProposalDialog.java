package com.coma.client.widgets;


import com.coma.client.Comav200;
import com.coma.client.ModelInfo;
import com.coma.client.SaveModel;
import com.coma.client.User;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class SendProposalDialog {

	private MessageFrame oryxFrame;
	private Dialog dialog;

	public SendProposalDialog(MessageFrame oryxFrame){
		this.oryxFrame = oryxFrame;
	}
		
	public Dialog createSendProposalDialog(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Send proposal?");
		dialog.add(new Label("Do you wish to send proposal for group: " + "GRUPPNAMN"));
		dialog.setPixelSize(300, 100);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons();

		dialog.addButton(new TextButton("Send", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				ModelInfo model = Comav200.GetInstance().getModel();
				model.setIsProposal(1);
				new SaveModel().saveModel(oryxFrame);
				dialog.hide();
			}
			
		}));
		
		dialog.addButton(new TextButton("Cancel", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();				
			}			
		}));

		dialog.show();
		return dialog;

	}
}
