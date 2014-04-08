package com.coma.client;

import java.util.HashMap;
import java.util.List;

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
public class Comav200 {

	private static Comav200 instance = null;
	public static String problemOwner;
	public static String problemLocation;
	private ModelInfo model;
	
	

	public ModelInfo getModel() {
		return model;
	}

	public void setModel(ModelInfo model) {
		this.model = model;
	}

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

	public Button newButton = new Button("New Model");
	public Button saveButton = new Button("Save Model");
	public Button loadButton = new Button("Load Model");
	public Button importButton = new Button("Import");
	public Button exportButton = new Button("Export");
	public Button editProfileButton = new Button("Edit profile");

	public Button proposeButton = new Button("Propose as group model");
	public Button createGroupButton = new Button("Create group");
	public Button inviteGroupButton = new Button("Invite to group");
	public Button switchGroupButton = new Button("Switch group");
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
	private int activeModelID;


	public void initialize(){
		RootPanel.get("mainDiv").add(logIn.screen());
	}
	
	class MyHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			if(event.getSource().equals(createGroupButton)){
				GroupDialogBox gdb = new GroupDialogBox();
				DialogBox dialogBox = gdb.createDialogBox();
				dialogBox.center();
				dialogBox.show();
			}
			else if(event.getSource().equals(switchGroupButton)){
				SwitchGroupDialogBox sgdb = new SwitchGroupDialogBox();
				DialogBox dialogBox = sgdb.createDialogBox();
				dialogBox.center();
				dialogBox.show();
			}
			else if(event.getSource().equals(inviteGroupButton)){
				InviteToGroupDialogBox itgdb = new InviteToGroupDialogBox();
				DialogBox dialogBox = itgdb.createDialogBox();
				dialogBox.center();
				dialogBox.show();
			}
			else if(event.getSource().equals(newButton)){
				model = new ModelInfo();
				NewModelDialogBox nmdb = new NewModelDialogBox();
				DialogBox dialogBox = nmdb.createDialogBox();
				dialogBox.center();
				dialogBox.show();
			}
			else if(event.getSource().equals(saveButton)){
				model.setIsProposal(0);
				new SaveModel().saveModel(oryxFrame);
			}
			else if(event.getSource().equals(loadButton)){
				new LoadModel().getModelFromDatabase(2,oryxFrame);
			}
			else if(event.getSource().equals(proposeButton)){
				model.setIsProposal(1);
				new SaveModel().saveModel(oryxFrame);
			}
			else if(event.getSource().equals(importButton)){

			}
			else if(event.getSource().equals(exportButton)){

			}
			else if(event.getSource().equals(writeCommentButton)){
				WriteCommentDialogBox wcdb = new WriteCommentDialogBox(activeModelID);

			}
			else if(event.getSource().equals(voteButtonButton)){
				VoteDialogBox vdb = new VoteDialogBox(activeModelID);
				DialogBox dialogBox = vdb.createDialogBox();
				dialogBox.center();
				dialogBox.show();
			}
			else if(event.getSource().equals(acceptProposalButton)){
				AcceptProposalDialogBox apdb = new AcceptProposalDialogBox();
				DialogBox dialogBox = apdb.createDialogBox();
				dialogBox.center();
				dialogBox.show();
			}

		}
	}

	public TabPanel initTabPanel(){
		
		final TabPanel panel = new TabPanel();
		panel.add(initMyModelView(), "My Model");
		panel.add(initGroupModelView(), "Group Model");
		panel.add(initProposalView(), "Proposals");	
		panel.add(initPreferencesView(), "Preferences");	
		panel.setSize("100%", "100%");
		panel.selectTab(0);
		
		panel.addSelectionHandler(new SelectionHandler<Integer>(){
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int tabId = event.getSelectedItem();
				Panel p = (Panel)panel.getWidget(tabId);
				
				if (tabId == 0 || tabId == 1) {
				p.add(oryxFrame);
				}
				
				if (tabId == 2) {
					p.clear();
					p.add(initProposalView());
					DockPanel dockPanel = new DockPanel();
					dockPanel.setWidth("100%");
					dockPanel.add(oryxFrame, DockPanel.CENTER);
					getVoteMapData(dockPanel);
					p.add(dockPanel);
				}		
		}});
		
		return panel;
	}

	private Panel topMenuButtonsMyModelView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		newButton.getElement().setClassName("utilityButton");
		saveButton.getElement().setClassName("utilityButton");
		loadButton.getElement().setClassName("utilityButton");
		proposeButton.getElement().setClassName("utilityButton");

		MyHandler handler = new MyHandler();
		newButton.addClickHandler(handler);
		saveButton.addClickHandler(handler);
		loadButton.addClickHandler(handler);
		proposeButton.addClickHandler(handler);

		panel.add(newButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(proposeButton);
		panel.add(new Label("Logged in as: " + User.getInstance().getUserEmail()+ " id: " + User.getInstance().getUserId()));

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

	private Panel topMenuButtonsPreferencesView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		createGroupButton.getElement().setClassName("utilityButton");
		inviteGroupButton.getElement().setClassName("utilityButton");
		switchGroupButton.getElement().setClassName("utilityButton");
		editProfileButton.getElement().setClassName("utilityButton");

		MyHandler handler = new MyHandler();
		createGroupButton.addClickHandler(handler);
		inviteGroupButton.addClickHandler(handler);
		switchGroupButton.addClickHandler(handler);
		editProfileButton.addClickHandler(handler);

		panel.add(createGroupButton);
		panel.add(inviteGroupButton);
		panel.add(switchGroupButton);
		panel.add(editProfileButton);

		return panel;  
	}
	
	public void getVoteMapData (DockPanel dPanel) {
		final DockPanel dockPanel = dPanel;
		int groupID = 1;
		databaseConnection.getAllModelsFromSpecificGroupThatIsProposed(groupID, new AsyncCallback<List<ModelInfo>>() {

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
			public void onSuccess(List<ModelInfo> result) {
				// TODO Auto-generated method stub
				VoteCellList.setModelInfoList(result);
				dockPanel.add(voteCellList.votingPanel(), DockPanel.EAST);
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
	* Initialize Proposal view
	*/
	private Panel initPreferencesView(){
		VerticalPanel panel = new VerticalPanel();
		panel.add(topMenuButtonsPreferencesView());
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
	
	/**
	*Gets active users ID from database and sets the ID in the User class
	*/
	public void getAndSetUserIDFromDatabase(String email) {
		databaseConnection.getUserID(email, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Integer result) {
				// TODO Auto-generated method stub
				User.getInstance().setUserId(result);
				initMainProgram();		
			}

		});
	}
	
	public void loadModelFromCellList(int modelID) {
		new LoadModel().getModelFromDatabase(modelID, oryxFrame);
	}

	public void initMainProgram() {
		RootPanel.get("mainDiv").clear();
		RootPanel.get("mainDiv").add(initTabPanel());
	}
	
	public void setActiveModelID(int modelID) {
		this.activeModelID = modelID;
	}
	
	public void clearOryx(){
		final MessageFrame oryxFrame = this.oryxFrame;
		final String model = "Empty model";
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
	
}
