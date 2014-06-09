package com.coma.client.dialogs;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class LoadModelDialog {

	Dialog dialog = new Dialog();


	public Dialog createLoadModelDialog(ContentPanel panel){
		// Create the popup dialog box

		dialog.setHeadingText("Select model to load");
		dialog.setPixelSize(400, 320);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.CLOSE);
		dialog.setWidget(panel);

		dialog.getButton(PredefinedButton.CLOSE).addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();
			}	
		});

		return dialog;
	}

}
