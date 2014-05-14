package com.coma.client.widgets;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.HasDialogHideHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class AcceptProposalDialog {

	Dialog dialog = new Dialog();

	private int activeModelID;

	public int getModelID() {
		return activeModelID;
	}

	public void setModelID(int modelID) {
		this.activeModelID = modelID;
	}

	public interface Display {
		HasSelectHandlers getYesButton();
		HasDialogHideHandlers getDialog();
	}

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public Dialog acceptProposalDialog() {

		dialog = new Dialog();
		dialog.setHeadingText("Save as new group model");
		dialog.setWidget(new HTML("Are you sure this is the diagram\n you want to accept as the new group model?\n"));
		dialog.setPixelSize(300, 100);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

		dialog.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				System.out.println("Hejsan, YES");
				 int activeGroupID = User.getInstance().getActiveGroupID();
                 int modelID = getModelID();
                 String version = "420";
                 
                 updateActiveGroupModel(activeGroupID, modelID, version);
                 dialog.hide();

			}
		});
		dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				System.out.println("Hejsan, CANCEL");
				dialog.hide();
			}
		});

		dialog.show();
		return dialog;

	}

	public void updateActiveGroupModel(int activeGroupID, int modelID, String version) {
		databaseConnection.updateActiveGroupModel(activeGroupID, modelID, version, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

			}
		});
	}

}
