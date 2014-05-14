package com.coma.client;

import java.util.HashMap;

import com.coma.client.widgets.CallbackHandler;
import com.coma.client.widgets.MessageFrame;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.info.Info;

public class SaveModel {

	public SaveModel(){	
	}

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);


	public void saveModel(MessageFrame orFrame){

		final MessageFrame oryxFrame = orFrame;
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
					return;
				}
				// Save the model that is in variable "message" (very long string/text)
				ModelInfo model = Comav200.GetInstance().getModel();
				if(model.isIsProposal() == 1){
					sendProposalToDatabase(User.getInstance().getActiveGroupID(), User.getInstance().getUserId(), model.getModelName(), model.getModelType() , data.get("message"), model.isIsProposal());   
				}else{
					saveModelToDatabase(User.getInstance().getActiveGroupID(), User.getInstance().getUserId(), model.getModelName(), model.getModelType() , data.get("message"), model.isIsProposal());   
					
				}
			}
		});
		oryxFrame.sendJSON(oryxCmd);
	}


	private void saveModelToDatabase(int groupID, int userID, String modelName, int modelType, String modelString, int isProposal) {
		databaseConnection.saveModel(groupID, userID, modelName, modelType, modelString, isProposal,
				new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(Void result) {
				Info.display("Saved", "Save successful");
				
			}
		});

	}

	private void sendProposalToDatabase(int groupID, int userID, String modelName, int modelType, String modelString, int isProposal) {
		databaseConnection.saveModel(groupID, userID, modelName, modelType, modelString, isProposal,
				new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(Void result) {
				Info.display("Proposal", "Proposal has been sent");
			}
		});

	}

}
