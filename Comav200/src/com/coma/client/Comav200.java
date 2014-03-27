package com.coma.client;

import com.coma.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.coma.client.oryxhandlers.LoadingCompleteEventListener;
import com.coma.client.oryxhandlers.LoadingCompletehandler;
import com.coma.client.widgets.CallbackHandler;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Comav200 implements EntryPoint {
	
	private static Comav200 instance = null;
	public static String problemOwner;
	public static String problemLocation;
	
	public static Comav200 GetInstance(){
		if(instance == null){
			instance = new Comav200();
			return instance;
		}
		else{
			return instance;
		}
	}
	
	private String result;
	
	public String getResult() {
		return result;
	}

	public void setResult(String value) {
		result = value;
	}

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
     
        LogIn login = new LogIn();

        RootPanel.get("oryxDiv").add(login.screen());


		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		diagramButton1.addClickHandler(handler);
		
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
    
    public void initializeOryx() {
    	
        RootPanel.get("rightDivBot").add(votingPanel("title", "preview", 1));
        
        RootPanel.get("topDiv").add(topMenuButtons());
        RootPanel.get("rightDivTop").add(diagramButtons());
        Frame testOryxFrame = new Frame("http://localhost/oryx/oryx.xhtml");
        testOryxFrame.setHeight("100%");
        testOryxFrame.setWidth("99%");
        RootPanel.get("oryxDiv").clear();
        RootPanel.get("oryxDiv").add(testOryxFrame);
    }
    
public void initializeSignUp() {
    	
		RootPanel.get("oryxDiv").clear();
        
		SignUp signUp = new SignUp();

        RootPanel.get("oryxDiv").add(signUp.screen());
    }

public void getPasswordFromDatabase(String email) {
	
System.out.println("getPasswordFromDatabase");
	
	databaseConnection.databaseServer(email, new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					System.out.println("onFailure");
				}

				public void onSuccess(String result) {
					setResult(result);	
					System.out.println("success");
				}
			});
	}

public void addUserToDatabase(String email, String password) {
	
	databaseConnection.databaseServer(email, password,
			new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					
				}

				public void onSuccess(String result) {
					initializeOryx();
				}
			});

	}
}



