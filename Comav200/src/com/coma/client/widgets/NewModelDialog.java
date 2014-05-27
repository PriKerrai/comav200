package com.coma.client.widgets;

import com.coma.client.Comav200;
import com.coma.client.ModelInfo;
import com.coma.client.SaveModel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class NewModelDialog {


	private TextField modelNameBox;

	private Dialog dialog;

	public Dialog createNewModelDialog(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Create new model");
		dialog.setPixelSize(300, 100);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons();

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();

		modelNameBox = new TextField();
		modelNameBox.setAllowBlank(false);
		modelNameBox.setEmptyText("Enter model name");

		verticalLayoutContainer.add(new FieldLabel(modelNameBox, "Model name"), new VerticalLayoutData(1, -1));

		dialog.setWidget(verticalLayoutContainer);

		dialog.addButton(new TextButton("Create", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				Comav200.GetInstance().clearOryx();
				ModelInfo model = Comav200.GetInstance().getModel();
				model.setModelName(modelNameBox.getValue());
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

