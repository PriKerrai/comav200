package com.coma.client.widgets;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.Slider;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class VoteDialogBox{
	final ListBox listBox = new ListBox(); 
	final String[] Items = { "0", "1", "2","3", "4", "5", "6", "7", "8", "9", "10" };
	TextField firstName = new TextField();

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	public int activeModelID;
	private Dialog dialog;

	public int getModelID() {
		return activeModelID;
	}

	public void setModelID(int modelID) {
		this.activeModelID = modelID;
	}

	public VoteDialogBox() {
	}

	public VoteDialogBox(int modelID) {
		setModelID(modelID);
	}

	public Dialog createDialogBox(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Leave a vote");
		dialog.setPixelSize(300, 150);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		final Slider slider = new Slider();
		slider.setMinValue(0);
		slider.setMaxValue(10);
		slider.setIncrement(1);
		slider.setValue(5);
		verticalLayoutContainer.add(new FieldLabel(slider, "Your vote:"), new VerticalLayoutData(1, -1));
		verticalLayoutContainer.add(new FieldLabel(firstName, "Your vote:"), new VerticalLayoutData(1, -1));

		slider.addValueChangeHandler(new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				firstName.setText(slider.getValue().toString());
			}
		});

		dialog.setWidget(verticalLayoutContainer);
		
		dialog.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				int index = slider.getValue();
				int userID = User.getInstance().getUserId();
				addVoteToModel(userID, getModelID(), index);
				dialog.hide();
			}
		});
		//Add a handler to close the dialog
		dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				System.out.println("Hejsan, CANCEL");
				dialog.hide();
			}
		});
		return dialog;
	}

	protected void addVoteToModel(int userID, int modelID, int index) {

		databaseConnection.addVoteToModel(userID, modelID, index, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Void result) {
				System.out.println("Success from addVoteToModel");
			}
		});
	}}
