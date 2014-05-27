package com.coma.client.widgets;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class LoadModelDialogBox {

	Dialog dialog = new Dialog();




	public Dialog createDialogBox(ContentPanel panel){
		// Create the popup dialog box

		dialog.setHeadingText("Select model");
		dialog.setPixelSize(400, 320);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.CLOSE);

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		ScrollPanel scrollPanel = new ScrollPanel(panel);
		scrollPanel.setHeight("300px");
		dialogVPanel.add(scrollPanel);
		dialog.setWidget(dialogVPanel);

		dialog.getButton(PredefinedButton.CLOSE).addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();
			}	
		});

		return dialog;
	}

}
