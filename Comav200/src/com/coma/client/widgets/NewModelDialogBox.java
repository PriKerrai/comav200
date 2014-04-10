package com.coma.client.widgets;

import com.coma.client.Comav200;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.ModelInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NewModelDialogBox {

	
	private TextBox modelNameBox;
	private ListBox modelTypeBox;

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public DialogBox createDialogBox(){
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText("New model");

		final Button createButton = new Button("Create");
		createButton.getElement().setId("createButton");
		
		final Button cancelButton = new Button("Cancel");
		cancelButton.getElement().setId("cancelButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		dialogVPanel.add(new Label("Model name:"));
		modelNameBox = new TextBox();
		dialogVPanel.add(modelNameBox);

		modelTypeBox = new ListBox();
		modelTypeBox.addItem("UML Class Diagram");
		modelTypeBox.addItem("UML Activity Diagram");
		modelTypeBox.addItem("UML Use Case Diagram");
		modelTypeBox.addItem("UML Sequence Diagram");
		modelTypeBox.addItem("UML State Diagram");
		
		modelTypeBox.setVisibleItemCount(1);
		
		dialogVPanel.add(modelTypeBox);
		dialogVPanel.add(cancelButton);
		dialogVPanel.add(createButton);
		
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		createButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comav200.GetInstance().clearOryx();
				ModelInfo model = Comav200.GetInstance().getModel();
				model.setModelName(modelNameBox.getText());
				model.setModelType(modelTypeBox.getSelectedIndex());
				dialogBox.hide();
			}
		});
		
		// Add a handler to close the DialogBox
				cancelButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						dialogBox.hide();

					}
				});

		return dialogBox;
	}
	
}
