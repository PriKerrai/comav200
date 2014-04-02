package com.coma.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.coma.shared.FieldVerifier;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.asm.tree.IntInsnNode;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.*;
import com.coma.client.oryxhandlers.LoadingCompleteEventListener;
import com.coma.client.oryxhandlers.LoadingCompletehandler;
import com.coma.client.widgets.*;

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

    public Button importButton = new Button("Import");
    public Button exportButton = new Button("Export");
    public Button createGroup = new Button("Create group");
    public Button inviteGroup = new Button("Invite to group");
    public Button switchGroup = new Button("Switch group");

     
    LogIn logIn = new LogIn();
    SignUp signUp = new SignUp();
    VoteCellList voteCellList = new VoteCellList();

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
        RootPanel.get("mainDiv").add(logIn.screen());
        getVoteMapData();
	}
	

    class MyHandler implements ClickHandler{

            @Override
            public void onClick(ClickEvent event) {

            		if(event.getSource().equals(createGroup)){
                    	GroupDialogBox gdb = new GroupDialogBox();
                		DialogBox b = gdb.createDialogBox();
                		b.center();
                		b.show();
                    }
                    else if(event.getSource().equals(switchGroup)){
                    	SwitchGroupDialogBox sgdb = new SwitchGroupDialogBox();
                		DialogBox b = sgdb.createDialogBox();
                		b.center();
                		b.show();
                    }
                    else if(event.getSource().equals(inviteGroup)){
                    	InviteToGroupDialogBox itgdb = new InviteToGroupDialogBox();
                		DialogBox b = itgdb.createDialogBox();
                		b.center();
                		b.show();
                    }

            }
    }
   
    public TabPanel initTabPanel(){

    	TabPanel panel = new TabPanel();
		panel.add(initMain(), "Main");
		panel.add(initializeOryx(), "Group Model");
		panel.add(diagramButtons(), "Proposals");		
		
		panel.setSize("100%", "100%");
		
		panel.selectTab(0);
		
		return panel;
		
	
    }
    
    private Panel topMenuButtons()
    { 	
            HorizontalPanel panel = new HorizontalPanel();
   
            importButton = new Button("Import");
            exportButton = new Button("Export");
            createGroup = new Button("Create group");
            inviteGroup = new Button("Invite to group");
            switchGroup = new Button("Switch group");


            importButton.getElement().setClassName("utilityButton");
            exportButton.getElement().setClassName("utilityButton");
            createGroup.getElement().setClassName("utilityButton");
            inviteGroup.getElement().setClassName("utilityButton");
            switchGroup.getElement().setClassName("utilityButton");
            
            MyHandler handler = new MyHandler();
            createGroup.addClickHandler(handler);
            inviteGroup.addClickHandler(handler);
            switchGroup.addClickHandler(handler);
            
            panel.add(importButton);
            panel.add(exportButton);
            panel.add(createGroup);
            panel.add(inviteGroup);
            panel.add(switchGroup);
    	
            return panel;  
    }
   
    //Buttons for diagrams in rightDiv
    private DockPanel diagramButtons()
    {
            Frame oryxFrame = initializeOryx();
            oryxFrame.setWidth("99%");
            
            DockPanel dockPanel = new DockPanel();
            dockPanel.setWidth("100%");
            dockPanel.add(oryxFrame, DockPanel.WEST);
            dockPanel.add(voteCellList.votingPanel(), DockPanel.EAST);

            return dockPanel;
    }
    
    private Panel initMain(){
    	VerticalPanel panel = new VerticalPanel();
    	panel.add(topMenuButtons());
    	panel.add(initializeOryx());
    	return panel;
    }
    
    public Frame initializeOryx() {
        Frame oryxFrame = new Frame("http://localhost/oryx/oryx.xhtml");
        oryxFrame.setHeight("600px");
        oryxFrame.setWidth("100%");
        return oryxFrame;

    }
    
public void initializeSignUp() {
    	
		RootPanel.get("mainDiv").clear();
        
		SignUp signUp = new SignUp();

        RootPanel.get("mainDiv").add(signUp.screen());
    }

public void getPasswordFromDatabase(String email) {
		
	databaseConnection.getPasswordForAuthorization(email, new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
				}

				public void onSuccess(String result) {
					setResult(result);	
				}
			});
	}

public void getVoteMapData () {
	databaseConnection.getVoteList(new AsyncCallback<List<DiagramInfo>>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			try {
				        throw caught;
				      } catch (IncompatibleRemoteServiceException e) {
				        // this client is not compatible with the server; cleanup and refresh the 
				        // browser
				    	  System.out.println("IncompatibleRemoteServiceException");
				      } catch (InvocationException e) {
				        // the call didn't complete cleanly
				    	  System.out.println("InvocationException");
				      } catch (Throwable e) {
				    	  System.out.println("Throwable");
				      }
				    }
				  
		

		@Override
		public void onSuccess(List<DiagramInfo> result) {
			// TODO Auto-generated method stub
			voteCellList.setMap(result);
		}
	});
}

public void getAndSetUserIDFromDatabase(String email) {
	
	databaseConnection.getUserID(email, new AsyncCallback<Integer>() {
				public void onFailure(Throwable caught) {
					
				}
				@Override
				public void onSuccess(Integer result) {
					User.getInstance().setUserId(result);
				}
			});
	}



public void addUserToDatabase(String email, String password) {
	
	databaseConnection.createNewUser(email, password,
			new AsyncCallback<Void>() {
				public void onFailure(Throwable caught) {
					
				}

				public void onSuccess(Void result) {
					initializeOryx();
				}
			});

	}

public void initMainProgram() {
	RootPanel.get("mainDiv").clear();
	RootPanel.get("mainDiv").add(initTabPanel());
	
}
}



