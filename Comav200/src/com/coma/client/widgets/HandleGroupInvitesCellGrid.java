
package com.coma.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.HandleGroups;
import com.coma.client.User;
import com.coma.client.WorkGroupInfo;
import com.coma.client.WorkGroupInfoProperties;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

public class HandleGroupInvitesCellGrid {
	private static final WorkGroupInfoProperties props = GWT.create(WorkGroupInfoProperties.class);
	
	private static ContentPanel gridContentPanel;
	
	public static List<WorkGroupInfo> inviteList;
	static ListStore<WorkGroupInfo> store;
	private int groupID;
	private int inviteID;

	public static List<WorkGroupInfo> getInviteList() {
		return inviteList;
	}

	private TextButton acceptGroupInviteButton = new TextButton("Accept invite");
	private TextButton declineGroupInviteButton = new TextButton("Decline invite");

	public void setInviteList(List<WorkGroupInfo> inviteList) {
		HandleGroupInvitesCellGrid.inviteList = inviteList;
	}
	
	public ContentPanel handleGroupInvitesCellGrid() {

		ColumnConfig<WorkGroupInfo, String> facilitatorName = new ColumnConfig<WorkGroupInfo, String>(props.facilitatorName(), 75, ("Invite from"));
		ColumnConfig<WorkGroupInfo, String> groupName = new ColumnConfig<WorkGroupInfo, String>(props.workGroupName(), 150, "Group name");
		//ColumnConfig<ModelInfo, String> creationDateColumn = new ColumnConfig<ModelInfo, String>(props.modelCreationDate(), 150, "Creation date");

		List<ColumnConfig<WorkGroupInfo, ?>> l = new ArrayList<ColumnConfig<WorkGroupInfo, ?>>();
		l.add(facilitatorName);
		l.add(groupName);
		//l.add(creationDateColumn);
		ColumnModel<WorkGroupInfo> cm = new ColumnModel<WorkGroupInfo>(l);

		store = new ListStore<WorkGroupInfo>(props.inviteKey());
		store.addAll(inviteList);
		
		gridContentPanel = new ContentPanel();
		gridContentPanel.setHeadingHtml("Your group invites");

		final Grid<WorkGroupInfo> grid = new Grid<WorkGroupInfo>(store, cm);
		grid.getView().setAutoExpandColumn(facilitatorName);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.setBorders(false);

		grid.setColumnReordering(false);

		grid.addRowClickHandler(new RowClickHandler() {

			

			@Override
			public void onRowClick(RowClickEvent event) {
				
				int workGroupID = event.getRowIndex();
				WorkGroupInfo workGroupOnSelectedRow = store.get(workGroupID);
				groupID = workGroupOnSelectedRow.getWorkGroupID();
				inviteID = workGroupOnSelectedRow.getWorkGroupInviteID();
				User.getInstance().setActiveGroupID(workGroupOnSelectedRow.getWorkGroupID());
			
				Info.display("Group changed", "Active group: " + workGroupOnSelectedRow.getWorkGroupName());
			}
		});
		
		acceptGroupInviteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {		
				new HandleGroups().acceptGroupInvite(groupID, inviteID);
			}
		});
		
		declineGroupInviteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				new HandleGroups().declineGroupInvite(inviteID);
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(acceptGroupInviteButton);
		hp.add(declineGroupInviteButton);
		VerticalLayoutContainer verticalContainer = new VerticalLayoutContainer();
		gridContentPanel.add(verticalContainer);

		verticalContainer.add(grid);
		verticalContainer.add(acceptGroupInviteButton);

		return gridContentPanel;

	}

}

