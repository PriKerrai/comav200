package com.coma.client;

import java.util.HashMap;
import java.util.List;

import com.coma.client.oryxhandlers.LoadingCompleteEventListener;
import com.coma.client.oryxhandlers.LoadingCompletehandler;
import com.coma.client.widgets.AcceptProposalDialog;
import com.coma.client.widgets.CallbackHandler;
import com.coma.client.widgets.CommentsDialog;
import com.coma.client.widgets.CreateNewGroupPanel;
import com.coma.client.widgets.InviteToGroupPanel;
import com.coma.client.widgets.LoadModelCellGrid;
import com.coma.client.widgets.LoadModelDialog;
import com.coma.client.widgets.MessageFrame;
import com.coma.client.widgets.ModelCellGrid;
import com.coma.client.widgets.NameModelDialog;
import com.coma.client.widgets.NewModelDialog;
import com.coma.client.widgets.SendProposalDialog;
import com.coma.client.widgets.UserProfileFormPanel;
import com.coma.client.widgets.VoteDialogBox;
import com.coma.client.widgets.VoteSummaryOnGroupProposalsDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Comav200 {

	private static Comav200 instance = null;
	public static String problemOwner;
	public static String problemLocation;
	private ModelInfo model;
	private List<String> userProfile;
	private boolean isFirstTime = true;	
	private Panel proposalButtonsPanel;

	public ModelInfo getModel() {
		return model;
	}

	public void setModel(ModelInfo model) {
		this.model = model;
	}

	private MessageFrame oryxFrame = null;
	public MessageFrame getOryxFrame() {
		return oryxFrame;
	}

	public void setOryxFrame(MessageFrame oryxFrame) {
		this.oryxFrame = oryxFrame;
	}

	public TabPanel tabPanel;

	public static Comav200 GetInstance(){
		if(instance == null){
			instance = new Comav200();
			return instance;
		}
		else{
			return instance;
		}
	}

	public TextButton newModelButton = new TextButton("New Model");
	public TextButton saveModelButton = new TextButton("Save Model");
	public TextButton loadModelButton = new TextButton("Load Model");
	public TextButton importModelButton = new TextButton("Import");
	public TextButton exportModelButton = new TextButton("Export");

	public TextButton editProfileButton = new TextButton("Edit profile");
	public TextButton invitesButton = new TextButton("My group invites");
	public TextButton proposeButton = new TextButton("Propose as group model");
	public TextButton createGroupButton = new TextButton("Create group");
	public TextButton inviteGroupButton = new TextButton("Invite to group");
	public TextButton switchGroupPreferencesTabButton = new TextButton("Switch group");
	public TextButton switchGroupGroupTabButton = new TextButton("Switch group");
	public TextButton leaveVoteButton = new TextButton("Leave vote");
	public TextButton modelCommentsButton = new TextButton("Model comments");
	public TextButton acceptProposalButton = new TextButton("Accept proposal");
	public TextButton showVotesOnAllProposals = new TextButton("Vote summary");


	LogIn logIn = new LogIn();
	SignUp signUp = new SignUp();
	VoteSummaryOnGroupProposalsDialog showVotesOnProposalDialog = new VoteSummaryOnGroupProposalsDialog();
	ModelCellGrid voteCellList = new ModelCellGrid();
	ProfileView profileView = new ProfileView();
	HorizontalPanel dockPanel = new HorizontalPanel();
	UserProfileFormPanel userProfilePanel = new UserProfileFormPanel();
	CreateNewGroupPanel createNewGroupPanel = new CreateNewGroupPanel();
	InviteToGroupPanel inviteToGroupPanel = new InviteToGroupPanel();

	public UserProfileFormPanel getUserProfilePanel() {
		return userProfilePanel;
	}
	
	public CreateNewGroupPanel getCreateNewGroupPanel() {
		return createNewGroupPanel;
	}
	
	public InviteToGroupPanel getInviteToGroupPanel() {
		return inviteToGroupPanel;
	}

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	private int activeModelID;
	
	public void initialize(){
		RootPanel.get("mainDiv").add(logIn.screen());
	}

	public TabPanel initTabPanel(){
		getUserProfile(User.getInstance().getUserId());
		tabPanel = new TabPanel();

		tabPanel.add(initMyModelView(), "My Model");
		tabPanel.add(initGroupModelView(), "Group Model");
		tabPanel.add(initProposalView(), "Proposals");	
		tabPanel.add(initPreferencesView(), "Preferences");	
		tabPanel.setSize("100%", "100%");	

		SelectionHandler<Widget> handler = new SelectionHandler<Widget>() {

			@Override
			public void onSelection(SelectionEvent<Widget> event) {
				TabPanel panel = (TabPanel) event.getSource();
				Widget w = event.getSelectedItem();
				int tabID = panel.getWidgetIndex(w);
				Panel p = (Panel)panel.getWidget(tabID);

				if (tabID == 0 || tabID == 1) {
					p.add(oryxFrame);
					oryxFrame.setWidth("100%");
					if(tabID == 1){
						oryxFrame.setVisible(true);
						new LoadModel().getActiveGroupModelFromDatabase(oryxFrame);
					}
				}

				if (tabID == 2) {
					model = new ModelInfo();
					activeModelID = -1;
					p.clear();
					dockPanel.clear();
					p.add(proposalButtonsPanel);

					double width = Window.getClientWidth();

					ContentPanel cp = new ContentPanel();
					cp.add(oryxFrame);
					cp.setWidth((int) (width*0.75));
					cp.setHeaderVisible(false);

					dockPanel.add(cp);

					getVoteMapData(dockPanel);

					p.add(dockPanel);	
				}
					if (tabID == 3) {
						if(isFirstTime){
							p.setWidth("100%");
							p.add(profileView.profileViewContainer());
							isFirstTime = false;
						}
					}
			}};
			tabPanel.addSelectionHandler(handler);

			return tabPanel;
	}

	private Panel topMenuButtonsMyModelView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		newModelButton.getElement().setClassName("utilityButton");
		saveModelButton.getElement().setClassName("utilityButton");
		loadModelButton.getElement().setClassName("utilityButton");
		proposeButton.getElement().setClassName("utilityButton");

		newModelButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				model = new ModelInfo();
				Dialog dialog = new NewModelDialog().createNewModelDialog();
				dialog.center();
				dialog.show();
			}
		});
		saveModelButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				if(model.getModelName() != null){
					model.setIsProposal(0);
					new SaveModel().saveModel(oryxFrame);
				} else{
					NameModelDialog nmd = new NameModelDialog(oryxFrame);
					Dialog dialog = nmd.createNameModelDialog();
					dialog.center();
					dialog.show();
				}	
			}

		});
		loadModelButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				//new LoadModel().getModelsFromDatabase(2,oryxFrame);
				getLoadModelCellListData();

			}

		});
		proposeButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				if(model.getModelName() != null){
					Dialog dialog = new SendProposalDialog(oryxFrame).createSendProposalDialog();
					dialog.center();
					dialog.show();
				} else{
					AlertMessageBox alert = new AlertMessageBox("Not saved", "Model needs to be saved before you can send it as a proposal");
					alert.show();
				}	
				
				

			}

		});

		panel.add(newModelButton);
		panel.add(saveModelButton);
		panel.add(loadModelButton);
		panel.add(proposeButton);
		panel.add(new Label("Logged in as: " + User.getInstance().getUserEmail()+ " id: " + User.getInstance().getUserId() + "Group: " +User.getInstance().getActiveGroupID()));

		return panel;  
	}

	private Panel topMenuButtonsGroupModelView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		importModelButton.setStyleName("testStyle");
		//importModelButton.getElement().setClassName("utilityButton");
		exportModelButton.getElement().setClassName("utilityButton");
		switchGroupGroupTabButton.getElement().setClassName("utilityButton");

		
		importModelButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				tabPanel.setActiveWidget(tabPanel.getWidget(0));
				new LoadModel().getActiveGroupModelFromDatabase(getOryxFrame());
			}
			
		});
		exportModelButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub

			}

		});
		
		switchGroupGroupTabButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				new HandleGroups().getUsersGroups();

			}

		});

		panel.add(importModelButton);
		panel.add(exportModelButton);
		panel.add(switchGroupGroupTabButton);

		return panel;  
	}

	private Panel topMenuButtonsProposalView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		modelCommentsButton.getElement().setClassName("utilityButton");
		leaveVoteButton.getElement().setClassName("utilityButton");
		acceptProposalButton.getElement().setClassName("utilityButton");
		showVotesOnAllProposals.getElement().setClassName("utilityButton");

		modelCommentsButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				if(activeModelID != -1){
					new CommentsDialog(activeModelID);
				}else{
					AlertMessageBox alert = new AlertMessageBox("No model choosen", "Please choose a model to read or leave a comment");
					alert.show();
				}
			}

		});
		leaveVoteButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				if(activeModelID != -1){
					VoteDialogBox vdb = new VoteDialogBox(activeModelID);
					Dialog dialogBox = vdb.createDialogBox();
					dialogBox.center();
					dialogBox.show();
				}else{
					AlertMessageBox alert = new AlertMessageBox("No model choosen", "Please choose a model to leave a vote");
					alert.show();
				}


			}

		});
		acceptProposalButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				AcceptProposalDialog apdb = new AcceptProposalDialog();
				apdb.setModelID(activeModelID);
				Dialog dialogBox = apdb.acceptProposalDialog();
				dialogBox.center();
				dialogBox.show();

			}

		});
		showVotesOnAllProposals.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				getProposalAvgVotes(User.getInstance().getActiveGroupID());
			}

		});

		panel.add(modelCommentsButton);
		panel.add(leaveVoteButton);
		panel.add(showVotesOnAllProposals);
		panel.add(acceptProposalButton);

		return panel;  
	}

	public void getVoteMapData (final Panel p) {
		databaseConnection.getAllModelsFromSpecificGroupThatIsProposed(User.getInstance().getActiveGroupID(), new AsyncCallback<List<ModelInfo>>() {
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
				double width = Window.getClientWidth();
				ModelCellGrid.setModelInfoList(result);

				ContentPanel cp = new ContentPanel();
				cp.add(ModelCellGrid.createModelCellGrid());
				cp.setWidth((int) (width*0.25));
				cp.setHeaderVisible(false);
				dockPanel.add(cp);

			}
		});
	}

	public void getLoadModelCellListData () {
		databaseConnection.getAllUserModels(User.getInstance().getUserId(), new AsyncCallback<List<ModelInfo>>() {

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
				//LoadModelCellList.setModelInfoList(result);
				LoadModelDialog lmdb = new LoadModelDialog();
				LoadModelCellGrid.setModelInfoList(result);

				Dialog dialog = lmdb.createLoadModelDialog(LoadModelCellGrid.createLoadModelCellGrid());
				dialog.center();
				dialog.show();
			}

		});
	}

	/**
	 *Initialize My Model view
	 */
	private Panel initMyModelView(){
		model = new ModelInfo();
		VerticalPanel panel = new VerticalPanel();
		initializeOryxFrame();
		panel.add(topMenuButtonsMyModelView());
		panel.add(oryxFrame);
		oryxFrame.setVisible(true);
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
		proposalButtonsPanel = topMenuButtonsProposalView();
		panel.add(proposalButtonsPanel);	
		return panel;
	}

	/**
	 * Initialize preferences view
	 */
	private Panel initPreferencesView(){
		VerticalPanel panel = new VerticalPanel();
		return panel;
	}

	/**
	 *Creates the frame which Oryx is loaded into
	 */
	public void initializeOryxFrame() {
		setOryxFrame(new MessageFrame("oryxFrame"));
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


	public void getProposalAvgVotes(int groupID){
		databaseConnection.getModelIDs(groupID, new AsyncCallback<List<Integer>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<Integer> result) {
				getVotesOnModel(result);

			}

		});
	}

	public void getVotesOnModel(List<Integer> modelIDs) {

		databaseConnection.getVotes(modelIDs, new AsyncCallback<List<ProposalAvgVote>>() {

			@Override
			public void onSuccess(List<ProposalAvgVote> result) {
				new ProposalAvgVotesData(result);
				//propAvgVote.setUpBarChart(result);
				VoteSummaryOnGroupProposalsDialog.setProposalAvgVotesList(result);

				Dialog dialogBox = showVotesOnProposalDialog.createVoteSummaryOnGoupProposalsDialog();
				dialogBox.center();
				dialogBox.show();

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

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
	/**
	 * Clear oryx workspace
	 */
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

	/**
	 * @param phoneNr 
	 * @param bDay 
	 * @param sName 
	 *
	 */
	public void addUserProfileToUser(String fName, String sName, String bDay, String phoneNr) {
		databaseConnection.addUserProfileToUser(User.getInstance().getUserId(), fName, sName, bDay, phoneNr, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
			}

		});
	}

	/**
	 * @param phoneNr 
	 * @param bDay 
	 * @param sName 
	 * @return 
	 *
	 */
	public void getUserProfile(int userID) {
		databaseConnection.getUserProfile(userID, new AsyncCallback<List<String>>() {

			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<String> result) {
				userProfilePanel.setUserProfile(result);
			}

		});

	}



}
