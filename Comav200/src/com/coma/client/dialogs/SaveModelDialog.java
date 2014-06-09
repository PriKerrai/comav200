package com.coma.client.dialogs;

import com.coma.client.Comav200;
import com.coma.client.ModelInfo;
import com.coma.client.SaveModel;
import com.coma.client.User;
import com.coma.client.widgets.MessageFrame;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class SaveModelDialog {


	private TextField modelNameBox;
	private MessageFrame oryxFrame;
	private Dialog dialog;

	public SaveModelDialog(MessageFrame oryxFrame){
		this.oryxFrame = oryxFrame;
	}

	public Dialog createSaveModelDialog(String modelName){
		// Create the popup dialog box
		final CheckBox proposalCheckBox = new CheckBox();
		proposalCheckBox.setBoxLabel(" - Send as proposal for " + User.getInstance().getActiveGroupName());
		dialog = new Dialog();
		dialog.setHeadingText("Save model");
		dialog.setPixelSize(300, 110);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons();
		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();

		modelNameBox = new TextField();
		modelNameBox.setAllowBlank(false);
		modelNameBox.setValue(modelName);

		verticalLayoutContainer.add(new FieldLabel(modelNameBox, "Model name"), new VerticalLayoutData(1, -1));
		if(User.getInstance().getActiveGroupName() != null){
			dialog.setPixelSize(400, 140);
			verticalLayoutContainer.add(proposalCheckBox);
		}
		dialog.setWidget(verticalLayoutContainer);


		dialog.addButton(new TextButton("Save", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				ModelInfo model = Comav200.GetInstance().getModel();
				model.setModelName(modelNameBox.getValue());
				if(proposalCheckBox.getValue()){
					model.setIsProposal(1);
				}else{
					model.setIsProposal(0);
				}
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