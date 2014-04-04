package com.coma.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
	public static String HEJ;

	private MessageFrame oryxFrame = null;


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
		//initMainProgram();
	}

	public void saveModel(){
		// When saving a model
		HashMap<String, String> oryxCmd = new HashMap<String, String>();
		oryxCmd.put("target", "oryx");
		oryxCmd.put("action", "sendshapes");
		oryxCmd.put("message", "");
		oryxFrame.removeAllCallbackHandlers();
		oryxFrame.addCallbackHandler(new CallbackHandler() {

			@Override
			public void callBack(final HashMap<String, String> data) {
				oryxFrame.removeAllCallbackHandlers();
				if (!data.get("action").equals("receiveshapes")) {
					// Display error message that editor does not respond
					System.out.println("This was a triumph, I'm making a note here: Not a success");
					return;
				}else{
					System.out.println("This was a triumph, I'm making a note here: Huge success!");
				}
				// Save the model that is in variable "message" (very long string/text)
				String veryLongText = data.get("message");
				HEJ = veryLongText;
				//shave to database
			}
		});
		oryxFrame.sendJSON(oryxCmd);
	}

	public void loadModel(String dbModel){
		//final String model = dbModel;

		final String model = HEJ;
		// When loading a model that is stored in string variable "model"
		oryxFrame.setVisible(false);
		oryxFrame.removeAllCallbackHandlers();
		oryxFrame.addCallbackHandler(new LoadingCompletehandler(new LoadingCompleteEventListener() {

			@Override
			public void loadingComplete() {
				oryxFrame.removeAllCallbackHandlers();
				oryxFrame.setVisible(true);
				oryxFrame.addCallbackHandler(new CallbackHandler() {
					@Override
					public void callBack(final HashMap<String, String> data) {
						oryxFrame.removeAllCallbackHandlers();
						if (!data.get("action").equals("shapesloaded")) {
							// Display error message that model cannot be loaded
							return;
						}
					}
				});
				HashMap<String, String> oryxCmd = new HashMap<String, String>();
				oryxCmd.put("target", "oryx");
				oryxCmd.put("action", "loadshapes");
				oryxCmd.put("message", model);
				oryxFrame.sendJSON(oryxCmd);
			}

		}));
		oryxFrame.setUrl("http://localhost/oryx/oryx.xhtml");
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
			else if(event.getSource().equals(importButton)){
				//new SaveModel().saveModel(oryxFrame);
				saveModel();
			}
			else if(event.getSource().equals(exportButton)){
				loadModel("hej");
			}

		}
	}

	public TabPanel initTabPanel(){

		//SpecialFrame myFrame = new SpecialFrame();
		
		final TabPanel panel = new TabPanel();
		panel.add(initMain(), "Main");
		panel.add(topMenuButtons(), "Group Model");
		//panel.add(diagramButtons(), "Proposals");		
		panel.setSize("100%", "100%");
		panel.selectTab(0);
		
		panel.addSelectionHandler(new SelectionHandler<Integer>(){
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int tabId = event.getSelectedItem();
				
				
			}
			});


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
		importButton.addClickHandler(handler);
		exportButton.addClickHandler(handler);
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
	/*
	private DockPanel diagramButtons()
	{
		Frame oryxFrame = initializeOryx();
		oryxFrame.setWidth("99%");

		DockPanel dockPanel = new DockPanel();
		dockPanel.setWidth("100%");
		dockPanel.add(oryxFrame, DockPanel.WEST);
		dockPanel.add(voteCellList.votingPanel(), DockPanel.EAST);

		return dockPanel;
	}*/

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

	private Panel votingPanel(String title, String preview, int id)
	{
		ScrollPanel cp = new ScrollPanel();
		cp.setHeight("100%");

		VerticalPanel mainPanel = new VerticalPanel();
		for(int i = 0; i<5; i++){
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

	private Panel initMain(){
		VerticalPanel panel = new VerticalPanel();
		initializeOryx();
		panel.add(topMenuButtons());
		panel.add(oryxFrame);
		return panel;
	}

	public void initializeOryx() {

		oryxFrame = new MessageFrame("oryxFrame");
		oryxFrame.init();
		oryxFrame.setUrl("http://localhost/oryx/oryx.xhtml");
		oryxFrame.setHeight("600px");
		oryxFrame.setWidth("100%");
		//return oryxFrame;
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



