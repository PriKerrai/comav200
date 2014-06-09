package com.coma.client.dialogs;

import com.coma.client.Comav200;
import com.coma.client.LoadModel;
import com.coma.client.User;
import com.coma.client.cellgrids.SwitchGroupCellGridForDialog;
import com.google.gwt.user.client.ui.ListBox;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;

public class SwitchGroupDialog {
	private ListBox groupBox;
	private Dialog dialog;

	public Dialog createDialogBox(ContentPanel panel){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Switch active group");
		dialog.setPixelSize(400, 300);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.OK, PredefinedButton.CANCEL);


		dialog.add(panel);


		dialog.getButton(PredefinedButton.OK).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(SwitchGroupCellGridForDialog.getWorkGroupName() != null){
					User.getInstance().setActiveGroupID(SwitchGroupCellGridForDialog.getNewWorkGroupID());
					User.getInstance().setActiveGroupName(SwitchGroupCellGridForDialog.getWorkGroupName());
					new LoadModel().getActiveGroupModelFromDatabase(Comav200.GetInstance().getOryxFrame());
					Info.display("Group changed", "Active group: " + SwitchGroupCellGridForDialog.getWorkGroupName());
					dialog.hide();
				}
			}
		});
		//Add a handler to close the dialog
		dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();
			}
		});
		return dialog;
	}

}
