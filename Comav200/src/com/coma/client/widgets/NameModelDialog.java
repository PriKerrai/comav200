package com.coma.client.widgets;

import com.coma.client.Comav200;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.ModelInfo;
import com.coma.client.SaveModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ListBox;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class NameModelDialog {
	
	private TextField modelNameBox;
	private ListBox modelTypeBox;
	private MessageFrame oryxFrame;
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	private Dialog dialog;

	public NameModelDialog(MessageFrame oryxFrame){
		this.oryxFrame = oryxFrame;
	}


	public Dialog createDialogBox(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Model name");
		dialog.setPixelSize(300, 100);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.OK, PredefinedButton.CANCEL);

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		modelNameBox = new TextField();
		modelNameBox.setAllowBlank(false);
		modelNameBox.setEmptyText("Enter model name");

		verticalLayoutContainer.add(new FieldLabel(modelNameBox, "Model name"), new VerticalLayoutData(1, -1));


		dialog.setWidget(verticalLayoutContainer);

		// Add a handler to create the new group
		dialog.getButton(PredefinedButton.OK).addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				ModelInfo model = Comav200.GetInstance().getModel();
				model.setModelName(modelNameBox.getValue());
				model.setIsProposal(0);
				new SaveModel().saveModel(oryxFrame);
				dialog.hide();

			}
		});
		//Add a handler to close the dialog
		dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();
			}
		});

		dialog.show();
		return dialog;

	}
}


