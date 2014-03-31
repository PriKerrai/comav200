package com.coma.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GroupDialogBox{

	 public static interface CwConstants extends Constants {
		    String cwDialogBoxCaption();

		    String cwDialogBoxClose();

		    String cwDialogBoxDescription();

		    String cwDialogBoxDetails();

		    String cwDialogBoxItem();

		    String cwDialogBoxListBoxInfo();

		    String cwDialogBoxMakeTransparent();

		    String cwDialogBoxName();

		    String cwDialogBoxShowButton();
		  }
	
	  private CwConstants constants;

	  public Widget onInitialize() {
	    // Create the dialog box
	    final DialogBox dialogBox = createDialogBox();
	    dialogBox.setGlassEnabled(true);
	    dialogBox.setAnimationEnabled(true);
	    return dialogBox;
	  }

	  /**
	   * Create the dialog box for this example.
	   *
	   * @return the new dialog box
	   */
	  public DialogBox createDialogBox() {
	    // Create a dialog box and set the caption text
	    final DialogBox dialogBox = new DialogBox();
	    dialogBox.ensureDebugId("cwDialogBox");
	    dialogBox.setText(constants.cwDialogBoxCaption());

	    // Create a table to layout the content
	    VerticalPanel dialogContents = new VerticalPanel();
	    dialogContents.setSpacing(4);
	    dialogBox.setWidget(dialogContents);

	    // Add some text to the top of the dialog
	    HTML details = new HTML(constants.cwDialogBoxDetails());
	    dialogContents.add(details);
	    dialogContents.setCellHorizontalAlignment(
	        details, HasHorizontalAlignment.ALIGN_CENTER);

	    
	    // Add a close button at the bottom of the dialog
	    Button closeButton = new Button(
	        constants.cwDialogBoxClose(), new ClickHandler() {
	          @Override
	        	public void onClick(ClickEvent event) {
	            dialogBox.hide();
	          }
	        });
	    dialogContents.add(closeButton);
	    if (LocaleInfo.getCurrentLocale().isRTL()) {
	      dialogContents.setCellHorizontalAlignment(
	          closeButton, HasHorizontalAlignment.ALIGN_LEFT);

	    } else {
	      dialogContents.setCellHorizontalAlignment(
	          closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
	    }

	    // Return the dialog box
	    return dialogBox;
	  }
}

