package com.coma.client;

import com.coma.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Comav200 implements EntryPoint {
	
	 public Button diagramButton1 = new Button("diagramButton1");
     public Button diagramButton2 = new Button("diagramButton2");
     public Button diagramButton3 = new Button("diagramButton3");
     public Button diagramButton4 = new Button("diagramButton4");
     public Button diagramButton5 = new Button("diagramButton5");
     LogIn logIn = new LogIn();

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Send");
        final TextBox nameField = new TextBox();
        nameField.setText("GWT User");
        final Label errorLabel = new Label("hjehej");
       
        LogIn login = new LogIn();
        
        
        RootPanel.get("topDiv").add(login.screen());
        
        
        //RootPanel.get("centerDiv").add(errorLabel);
        RootPanel.get("rightDivBot").add(votingPanel("title", "preview", 1));
        RootPanel.get("topDiv").add(topMenuButtons());
        RootPanel.get("rightDivTop").add(diagramButtons());
        Frame testOryxFrame = new Frame("http://localhost/oryx/oryx.xhtml");
       
        testOryxFrame.setHeight("100%");
        testOryxFrame.setWidth("99%");
        RootPanel.get("oryxDiv").add(testOryxFrame);
        //RootPanel.get("oryxDiv").add(logIn.screen());
        // We can add style names to widgets
        sendButton.addStyleName("sendButton");
        
        // Focus the cursor on the name field when the app loads
        nameField.setFocus(true);
        nameField.selectAll();

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                        dialogBox.hide();
                        sendButton.setEnabled(true);
                        sendButton.setFocus(true);
                }
        });


		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				databaseConnection.databaseServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		diagramButton1.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	

    class MyHandler implements ClickHandler{

            @Override
            public void onClick(ClickEvent event) {

                    if(event.getSource().equals(diagramButton1)){

                    }
                    else if(event.getSource().equals(diagramButton2)){

                    }
                    else if(event.getSource().equals(diagramButton3)){

                    }
                    else if(event.getSource().equals(diagramButton4)){

                    }
                    else if(event.getSource().equals(diagramButton5)){

                    }
            }
    }
   
    private Panel topMenuButtons()
    {
            HorizontalPanel panel = new HorizontalPanel();
   
            final Button importButton = new Button("Import");
            final Button exportButton = new Button("Export");

            importButton.getElement().setClassName("utilityButton");
            exportButton.getElement().setClassName("utilityButton");
           
            panel.add(importButton);
            panel.add(exportButton);
            return panel;  
    }
   
    //Buttons for diagrams in rightDiv
    private Panel diagramButtons()
    {
            diagramButton1 = new Button("diagramButton1");
            diagramButton2 = new Button("diagramButton2");
            diagramButton3 = new Button("diagramButton3");
            diagramButton4 = new Button("diagramButton4");
            diagramButton5 = new Button("diagramButton5");
           
            MyHandler m = new MyHandler();
            diagramButton1.addClickHandler(m);
            diagramButton2.addClickHandler(m);
           
            diagramButton1.getElement().setClassName("diagramButton");
            diagramButton2.getElement().setClassName("diagramButton");
            diagramButton3.getElement().setClassName("diagramButton");
            diagramButton4.getElement().setClassName("diagramButton");
            diagramButton5.getElement().setClassName("diagramButton");
           
            FlowPanel flowPanel = new FlowPanel();
            flowPanel.add(diagramButton1);
            flowPanel.add(diagramButton2);
            flowPanel.add(diagramButton3);
            flowPanel.add(diagramButton4);
            flowPanel.add(diagramButton5);

            return flowPanel;
    }
   
   
    private Panel votingPanel(String title, String preview, int id)
    {
            ScrollPanel cp = new ScrollPanel();
            cp.setHeight("100%");
           
            VerticalPanel mainPanel = new VerticalPanel();
            for(int i = 0; i<10; i++){
            HorizontalPanel panel = new HorizontalPanel();
            VerticalPanel vPanel = new VerticalPanel();
            HorizontalPanel hPanel = new HorizontalPanel();
            panel.add(new Label(preview));
            panel.add(vPanel);
            vPanel.add(new Label(title));
            vPanel.add(hPanel);

            hPanel.add(new Button("1"));
            hPanel.add(new Button("2"));
            hPanel.add(new Button("3"));
            hPanel.add(new Button("4"));
            hPanel.add(new Button("5"));
           
            mainPanel.add(panel);
            }
            cp.add(mainPanel);
                      
            return cp;
    }

}
