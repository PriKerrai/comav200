package com.coma.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.NamedFrame;

/**
 * 
 * @author XePOS cvba
 * 
 *         Frame providing the possibility to communicate with the inner frame (cross-domain).
 */
public class MessageFrame extends NamedFrame {

    private final ArrayList<CallbackHandler> callbackHandlers = new ArrayList<CallbackHandler>();
    private final HashMap<String, String> callbackData = new HashMap<String, String>();
    private final String name;

    public MessageFrame(String name) {
        super(name);
        this.name = name;
    }

    public void init() {
        initNative();
    }

    @Override
    public void setUrl(String url) {
        super.setUrl(url);
        // initNative();
    }

    public boolean addCallbackHandler(CallbackHandler callbackHandler) {
        if (callbackHandlers.contains(callbackHandler)) {
            return false;
        }
        callbackHandlers.add(callbackHandler);
        return true;
    }

    public boolean removeCallbackHandler(CallbackHandler callbackHandler) {
        return callbackHandlers.remove(callbackHandler);
    }

    public void removeAllCallbackHandlers() {
        callbackHandlers.clear();
    }

    public boolean hasCallbackHandler(CallbackHandler callbackHandler) {
        return callbackHandlers.contains(callbackHandler);
    }

    public void sendString(String data) {
        try {
            sendStringNative(data);
        } catch (Exception e) {
            Window.alert("sendString error: " + e.getMessage());
        }
    }

    public void sendJSON(HashMap<String, String> data) {
        JSONObject jsondata = new JSONObject();
        for (String key : data.keySet()) {
            jsondata.put(key, new JSONString(data.get(key)));
        }
        sendString(jsondata.toString());
    }

    private void callBack() {

        for (CallbackHandler cbh : callbackHandlers) {
            cbh.callBack(callbackData);
        }
        callbackData.clear();
        /*
         * HashMap<String, String> testMessage = new HashMap<String, String>(); testMessage.put("target", "oryx");
         * testMessage.put("action", "test"); testMessage.put("message", "GWT test message"); sendJSON(testMessage);
         */
    }

    public native void initNative() /*-{
        $wnd.addEventListener("message", callback, false);
        var messageframe = this;
        
        function callback(event) {
            var javascriptData = JSON.parse(event.data);
            
            if (javascriptData.target != undefined) {
                try {
                    messageframe.@com.coma.client.widgets.MessageFrame::addData(Ljava/lang/String;Ljava/lang/String;)(String("target"), String(javascriptData.target));
                } catch (error) {
                    alert("Targeterror: " + error);
                }
            } else {
                try {
                    messageframe.@com.coma.client.widgets.MessageFrame::addData(Ljava/lang/String;Ljava/lang/String;)(String("target"), String(""));
                } catch (error) {
                    alert("Targeterror undefined: " + error);
                }
            }
            if (javascriptData.action != undefined) {
                try {
                    messageframe.@com.coma.client.widgets.MessageFrame::addData(Ljava/lang/String;Ljava/lang/String;)(String("action"), String(javascriptData.action));
                } catch (error) {
                    alert("Actionerror: " + error);
                }
            } else {
                try {
                    messageframe.@com.coma.client.widgets.MessageFrame::addData(Ljava/lang/String;Ljava/lang/String;)(String("action"), String(""));
                } catch (error) {
                    alert("Actionerror undefined: " + error);
                }
            }
            if (javascriptData.message != undefined) {
                try {
                    messageframe.@com.coma.client.widgets.MessageFrame::addData(Ljava/lang/String;Ljava/lang/String;)(String("message"), String(javascriptData.message));
                } catch (error) {
                    alert("Messageerror: " + error);
                }
            } else {
                try {
                    messageframe.@com.coma.client.widgets.MessageFrame::addData(Ljava/lang/String;Ljava/lang/String;)(String("message"), String(""));
                } catch (error) {
                    alert("Messageerror undefined: " + error);
                }
            }
            try {
                messageframe.@com.coma.client.widgets.MessageFrame::callBack()();
            } catch (error) {  }
        }
        
    }-*/;

    private native void sendStringNative(String data) /*-{
		var messageframe = $doc
				.getElementsByName(this.@com.coma.client.widgets.MessageFrame::name)[0].contentWindow;
		messageframe.postMessage(data, "*");
    }-*/;

    private native void sendJSONNative(HashMap<String, String> data, Object[] keys) /*-{
		var javascriptData = new Array();

		for ( var key in keys) {
			javascriptData[key] = data.get(key);
		}

		var messageframe = $doc
				.getElementsByName(this.@com.coma.client.widgets.MessageFrame::name)[0].contentWindow;
		messageframe.postMessage(JSON.stringify(javascriptData), "*");
    }-*/;

    private void addData(String key, String value) {
        callbackData.put(key, value);
    }

}
