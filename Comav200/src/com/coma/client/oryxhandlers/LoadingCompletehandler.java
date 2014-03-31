package com.coma.client.oryxhandlers;

import java.util.HashMap;

import com.coma.client.widgets.CallbackHandler;
/**
 * 
 * @author XePOS cvba
 *
 */
public class LoadingCompletehandler implements CallbackHandler {
    
    private LoadingCompleteEventListener listener;
    
    public LoadingCompletehandler(LoadingCompleteEventListener loadingCompleteEventListener) {
        this.listener = loadingCompleteEventListener;
    }

    @Override
    public void callBack(HashMap<String, String> data) {
        if (data.get("action").equals("loaded")) {
            listener.loadingComplete();
        }
    }

}

