package com.coma.client.widgets;

import java.util.List;

import com.coma.client.Comav200;
import com.coma.client.LoadModel;
import com.coma.client.User;
import com.coma.client.WorkGroupInfo;
import com.google.gwt.user.client.ui.ListBox;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.info.Info;

public class SwitchGroupDialog {
	private ListBox groupBox;
    private Dialog dialog;
   
    public Dialog createDialogBox(ContentPanel panel){
    		// Create the popup dialog box
    			dialog = new Dialog();
    			dialog.setHeadingText("Switch active group");
    			dialog.setPixelSize(300, 100);
    			dialog.setHideOnButtonClick(true);
    			dialog.setPredefinedButtons(PredefinedButton.OK, PredefinedButton.CANCEL);

    			VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
    			verticalLayoutContainer.addStyleName("dialogVPanel");
    	
    	
    		dialog.add(panel);

            
        	dialog.getButton(PredefinedButton.OK).addSelectHandler(new SelectHandler() {
    			@Override
    			public void onSelect(SelectEvent event) {
	
    				User.getInstance().setActiveGroupID(SwitchGroupCellGridForDialog.getNewWorkGroupID());
    				User.getInstance().setActiveGroupName(SwitchGroupCellGridForDialog.getWorkGroupName());
    				new LoadModel().getActiveGroupModelFromDatabase(Comav200.GetInstance().getOryxFrame());
    				Info.display("Group changed", "Active group: " + SwitchGroupCellGridForDialog.getWorkGroupName());
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
    
}
