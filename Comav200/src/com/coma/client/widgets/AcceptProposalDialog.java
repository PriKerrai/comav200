package com.coma.client.widgets;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.coma.client.WorkGroupInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.HasDialogHideHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Creates the dialog for accepting a proposal as the new group model. 
 * @author Martin Nilsson and Johan Magnusson
 *
 */

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

	/**
	 * 
	 * @return the dialog for accepting the proposal as the new group model
	 */
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
				 int activeGroupID = User.getInstance().getActiveGroupID();
                 int modelID = getModelID();
                 String version = "420";
                 
                 checkGroupFacilitator(activeGroupID, modelID, version);
                 
                 dialog.hide();

			}
		});
		dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();
			}
		});

		dialog.show();
		return dialog;

	}

	private void checkGroupFacilitator(final int activeGroupID, final int modelID, final String version){
		databaseConnection.getGroupInfo(activeGroupID, new AsyncCallback<WorkGroupInfo>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSuccess(WorkGroupInfo result) {
				System.out.println(result.getWorkGroupFacilitator()+ ": DB");
				System.out.println(User.getInstance().getUserId()+ ": user");
				if(result.getWorkGroupFacilitator() == User.getInstance().getUserId()){
					updateActiveGroupModel(activeGroupID, modelID, version);
					Info.display("New group model", "Model set as group model");
				}else{
					AlertMessageBox alert = new AlertMessageBox("Forbidden", "Only group facilitator can accept proposals");
					alert.show();
				}
				
			}
		});
	}
	
	/**
	 * 
	 * @param activeGroupID The activeGroupID that the current user has selected
	 * @param modelID	The modelID of the proposed model
	 * @param version	AutoIncremented versionID
	 */
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
