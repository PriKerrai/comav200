package com.coma.client.widgets;

import com.coma.client.Comav200;
import com.coma.client.ModelInfo;
import com.coma.client.SaveModel;
import com.coma.client.User;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class NameModelDialog {
	
	private TextField modelNameBox;
	private MessageFrame oryxFrame;
	private Dialog dialog;

	public NameModelDialog(MessageFrame oryxFrame){
		this.oryxFrame = oryxFrame;
	}

	public Dialog createNameModelDialog(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Save model");
		dialog.setPixelSize(300, 110);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons();

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		modelNameBox = new TextField();
		modelNameBox.setAllowBlank(false);
		modelNameBox.setEmptyText("Enter model name");

		verticalLayoutContainer.add(new Label("Please name your model before saving"));
		verticalLayoutContainer.add(new FieldLabel(modelNameBox, "Model name"), new VerticalLayoutData(1, -1));

		dialog.setWidget(verticalLayoutContainer);
		
		
		dialog.addButton(new TextButton("Save", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				ModelInfo model = Comav200.GetInstance().getModel();
				model.setModelName(modelNameBox.getValue());
				model.setIsProposal(0);
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


