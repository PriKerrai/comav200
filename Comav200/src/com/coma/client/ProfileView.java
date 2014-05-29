package com.coma.client;

import java.util.List;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class ProfileView {

	public HorizontalLayoutContainer profileViewContainer(){
		
		HorizontalLayoutContainer vlc = new HorizontalLayoutContainer();

		int windowHeight = Window.getClientHeight();
        int windowWidth = Window.getClientWidth();
        
        System.out.print(windowHeight + " " + windowWidth);
        VerticalLayoutContainer vp = new VerticalLayoutContainer();
        vp.setPixelSize((int) (windowWidth*0.33), windowHeight);
		VerticalLayoutContainer vp2 = new VerticalLayoutContainer();
		vp2.setWidth((int) (windowWidth*0.3));
		vp2.setHeight(windowHeight);
		VerticalLayoutContainer vp3 = new VerticalLayoutContainer();
		vp3.setWidth((int) (windowWidth*0.3));
		vp3.setHeight(windowHeight);
		
		ContentPanel vp4 = Comav200.GetInstance().getCreateNewGroupPanel().createNewGroupPanel();
		vp4.setHeight(300);
		ContentPanel vp5 = Comav200.GetInstance().getInviteToGroupPanel().inviteToGroupPanel();
		vp5.setHeight(300);
		
		ContentPanel vp6 = Comav200.GetInstance().getSwitchGroupCellPanel().createSwitchGroupCellGrid();
		vp6.setHeight(300);
		ContentPanel vp7 = Comav200.GetInstance().getHandleGroupInvitesPanel().handleGroupInvitesCellGrid();
		vp7.setHeight(300);
		
		vp.add(Comav200.GetInstance().getUserProfilePanel().createUserProfilePanel());
		
		vp2.add(vp4);
		vp2.add(vp5);
		
		vp3.add(vp6);
		vp3.add(vp7);
		
		vlc.add(vp);
		vlc.add(vp2);
		vlc.add(vp3);
		
		vp4.setBorders(true);
		return vlc;
		
	}

}
