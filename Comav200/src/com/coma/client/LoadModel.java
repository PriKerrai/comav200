package com.coma.client;

import java.util.HashMap;

import com.coma.client.oryxhandlers.LoadingCompleteEventListener;
import com.coma.client.oryxhandlers.LoadingCompletehandler;
import com.coma.client.widgets.CallbackHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoadModel {

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	
	public LoadModel(){	}
	
	public void getModel(int modelID){
		getModelFromDatabase(modelID);
	}
	/*
	private void loadModel(Model result){
		
		final String model = result.getMessage();
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
        //oryxFrame.setUrl("http://cpi2.xepos.be:9999/oryx/oryx.xhtml");
	}
	*/
	private void getModelFromDatabase(int modelID) {
		
		databaseConnection.getModel(modelID, new AsyncCallback<Model>() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Model result) {
						//loadModel(result);
					}
				});
		}
	
}
