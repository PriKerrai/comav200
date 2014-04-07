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

	public Button saveButton = new Button("Save");
	public Button loadButton = new Button("Load");
	public Button importButton = new Button("Import");
	public Button exportButton = new Button("Export");
	public Button importButton12 = new Button("Import12");
	public Button exportButton12 = new Button("Export12");

	public Button createGroup = new Button("Create group");
	public Button inviteGroup = new Button("Invite to group");
	public Button switchGroup = new Button("Switch group");
	public Button voteButtonButton = new Button("Leave vote");
	public Button writeCommentButton = new Button("Write comment");
	public Button readCommentButton = new Button("Read comments");
	public Button acceptProposalButton = new Button("Accept proposal");

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
		//RootPanel.get("mainDiv").add(logIn.screen());
		initMainProgram();
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
			else if(event.getSource().equals(saveButton)){
				new SaveModel().saveModel(oryxFrame);
				//saveModel();
			}
			else if(event.getSource().equals(loadButton)){
				loadModel("hej");
			}
			else if(event.getSource().equals(importButton)){
				//new SaveModel().saveModel(oryxFrame);
				//saveModel();
			}
			else if(event.getSource().equals(exportButton12)){
				loadModel("hej");
			}
			else if(event.getSource().equals(writeCommentButton)){
				WriteCommentDialogBox wcdb = new WriteCommentDialogBox();
				DialogBox b = wcdb.createDialogBox();
				b.center();
				b.show();
			}
			else if(event.getSource().equals(readCommentButton)){
				ReadCommentDialogBox rcdb = new ReadCommentDialogBox();
				DialogBox b = rcdb.createDialogBox();
				b.center();
				b.show();
			}
			else if(event.getSource().equals(voteButtonButton)){
				VoteDialogBox vdb = new VoteDialogBox();
				DialogBox b = vdb.createDialogBox();
				b.center();
				b.show();
			}
			else if(event.getSource().equals(acceptProposalButton)){
				AcceptProposalDialogBox apdb = new AcceptProposalDialogBox();
				DialogBox b = apdb.createDialogBox();
				b.center();
				b.show();
			}

		}
	}

	public TabPanel initTabPanel(){
		
		final TabPanel panel = new TabPanel();
		panel.add(initMyModelView(), "Main");
		panel.add(initGroupModelView(), "Group Model");
		panel.add(initProposalView(), "Proposals");		
		panel.setSize("100%", "100%");
		panel.selectTab(0);
		
		panel.addSelectionHandler(new SelectionHandler<Integer>(){
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int tabId = event.getSelectedItem();
				Panel p = (Panel)panel.getWidget(tabId);
				p.add(oryxFrame);
			}
			});
		
		return panel;
	}

	private Panel topMenuButtonsMyModelView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		saveButton.getElement().setClassName("utilityButton");
		loadButton.getElement().setClassName("utilityButton");
		createGroup.getElement().setClassName("utilityButton");
		inviteGroup.getElement().setClassName("utilityButton");
		switchGroup.getElement().setClassName("utilityButton");

		MyHandler handler = new MyHandler();
		saveButton.addClickHandler(handler);
		loadButton.addClickHandler(handler);
		createGroup.addClickHandler(handler);
		inviteGroup.addClickHandler(handler);
		switchGroup.addClickHandler(handler);

		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(createGroup);
		panel.add(inviteGroup);
		panel.add(switchGroup);

		return panel;  
	}
	
	private Panel topMenuButtonsGroupModelView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		importButton = new Button("Import");
		exportButton = new Button("Export");
		
		importButton.getElement().setClassName("utilityButton");
		exportButton.getElement().setClassName("utilityButton");

		MyHandler handler = new MyHandler();
		importButton.addClickHandler(handler);
		exportButton.addClickHandler(handler);

		panel.add(importButton);
		panel.add(exportButton);

		return panel;  
	}
	
	private Panel topMenuButtonsProposalView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		writeCommentButton.getElement().setClassName("utilityButton");
		readCommentButton.getElement().setClassName("utilityButton");
		voteButtonButton.getElement().setClassName("utilityButton");
		acceptProposalButton.getElement().setClassName("utilityButton");

		MyHandler handler = new MyHandler();
		writeCommentButton.addClickHandler(handler);
		readCommentButton.addClickHandler(handler);
		voteButtonButton.addClickHandler(handler);
		acceptProposalButton.addClickHandler(handler);		

		panel.add(writeCommentButton);
		panel.add(readCommentButton);
		panel.add(voteButtonButton);
		panel.add(acceptProposalButton);

		return panel;  
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

	/**
	*Initialize My Model view
	*/
	private Panel initMyModelView(){
		VerticalPanel panel = new VerticalPanel();
		initializeOryxFrame();
		panel.add(topMenuButtonsMyModelView());
		panel.add(oryxFrame);
		return panel;
	}
	
	/**
	*Initialize Group Model view
	*/
	private Panel initGroupModelView(){
		VerticalPanel panel = new VerticalPanel();
		panel.add(topMenuButtonsGroupModelView());
		return panel;
	}
	
	/**
	*Initialize Proposal view
	*/
	private Panel initProposalView(){
		VerticalPanel panel = new VerticalPanel();
		panel.add(topMenuButtonsProposalView());
		return panel;
	}

	/**
	*Creates the frame which Oryx is loaded into
	*/
	public void initializeOryxFrame() {
		oryxFrame = new MessageFrame("oryxFrame");
		oryxFrame.init();
		oryxFrame.setUrl("http://localhost/oryx/oryx.xhtml");
		oryxFrame.setHeight("600px");
		oryxFrame.setWidth("100%");
	}

	public void initializeSignUp() {
		RootPanel.get("mainDiv").clear();
		SignUp signUp = new SignUp();
		RootPanel.get("mainDiv").add(signUp.screen());
	}
	
	//Gets active users ID from database and sets the ID in the User class
	public void getAndSetUserIDFromDatabase(String email) {

		databaseConnection.getUserID(email, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Integer result) {
				User.getInstance().setUserId(result);
				initMainProgram();
			}
		});
	}

	public void initMainProgram() {
		RootPanel.get("mainDiv").clear();
		RootPanel.get("mainDiv").add(initTabPanel());
	}
}



