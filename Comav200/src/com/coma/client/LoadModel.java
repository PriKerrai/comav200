package com.coma.client;

import java.util.HashMap;

import com.coma.client.oryxhandlers.LoadingCompleteEventListener;
import com.coma.client.oryxhandlers.LoadingCompletehandler;
import com.coma.client.widgets.CallbackHandler;
import com.coma.client.widgets.MessageFrame;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoadModel {

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	
	public LoadModel(){	}
	
	public void loadModel(ModelInfo result, MessageFrame orFrame){
		final MessageFrame oryxFrame = orFrame;
		final String model = result.getModelString();
		// When loading a model that is stored in string variable "model"
		//oryxFrame.setVisible(false);
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

	public void getModelFromDatabase(int modelID, MessageFrame orFrame) {

		final MessageFrame oryxFrame = orFrame;
		databaseConnection.loadModel(modelID, new AsyncCallback<ModelInfo>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(ModelInfo result) {
						Comav200.GetInstance().setModel(result);
						loadModel(result, oryxFrame);
					}
				});
		}
	
}
