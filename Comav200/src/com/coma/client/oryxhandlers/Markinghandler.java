package com.coma.client.oryxhandlers;

import java.util.HashMap;

import com.coma.client.Comav200;
import com.coma.client.widgets.CallbackHandler;


/**
 * 
 * @author XePOS cvba
 *
 */
public class Markinghandler implements CallbackHandler {

    @Override
    public void callBack(final HashMap<String, String> data) {
        if (data.get("action").equals("ownermarked")) {
        	Comav200.problemOwner = data.get("message");
        } else if (data.get("action").equals("locationmarked")) {
        	Comav200.problemLocation = data.get("message");
        }
    }
}

