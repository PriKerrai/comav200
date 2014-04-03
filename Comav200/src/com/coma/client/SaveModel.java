package com.coma.client;

import java.util.HashMap;

import com.coma.client.widgets.CallbackHandler;
import com.coma.client.widgets.MessageFrame;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SaveModel {
	
	public SaveModel(){	
	}
	
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	
	public void saveModel(MessageFrame orFrame){
		
		final MessageFrame oryxFrame = orFrame;
		 // When saving a model
		System.out.println("Savemodel2");
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
               	System.out.println("This was a triumph, I'm making a note here: Huge success! " + data.get("message"));
               }
               // Save the model that is in variable "message" (very long string/text)
              saveModelToDatabase(User.getInstance().getUserId(), "TYPE", data.get("message"));
               
           }
       });
       oryxFrame.sendJSON(oryxCmd);
	}
	
	
	 private void saveModelToDatabase(int userID, String type, String model) {
			
			databaseConnection.saveModel(userID, type, model,
					new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							
						}

						public void onSuccess(Void result) {
							System.out.println("Success saving to DB");
						}
					});

			}
	
}
