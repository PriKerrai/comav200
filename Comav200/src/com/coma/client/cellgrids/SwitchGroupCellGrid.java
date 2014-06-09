package com.coma.client.cellgrids;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.Comav200;
import com.coma.client.HandleGroups;
import com.coma.client.LoadModel;
import com.coma.client.User;
import com.coma.client.WorkGroupInfo;
import com.coma.client.WorkGroupInfoProperties;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

public class SwitchGroupCellGrid {
	private static final WorkGroupInfoProperties props = GWT.create(WorkGroupInfoProperties.class);

	private static ContentPanel gridContentPanel;

	private static List<WorkGroupInfo> workGroupInfoList;
	static ListStore<WorkGroupInfo> store;
	private TextButton switchGroupButton = new TextButton("Switch group");
	private int newWorkGroupID;
	private String workGroupName;
	
	public static List<WorkGroupInfo> getWorkGroupInfoList() {
		return workGroupInfoList;
	}

	public void setworkGroupInfoList(List<WorkGroupInfo> workGroupInfoList) {
		SwitchGroupCellGrid.workGroupInfoList = workGroupInfoList;
	}

	public ContentPanel createSwitchGroupCellGrid() {

		ColumnConfig<WorkGroupInfo, String> groupNameColumn = new ColumnConfig<WorkGroupInfo, String>(props.workGroupName(), 75, ("Group"));

		List<ColumnConfig<WorkGroupInfo, ?>> l = new ArrayList<ColumnConfig<WorkGroupInfo, ?>>();
		l.add(groupNameColumn);
		ColumnModel<WorkGroupInfo> cm = new ColumnModel<WorkGroupInfo>(l);

		store = new ListStore<WorkGroupInfo>(props.key());
		store.addAll(workGroupInfoList);

		gridContentPanel = new ContentPanel();
		gridContentPanel.setHeight(600);
		gridContentPanel.setHeadingHtml("Switch group");

		final Grid<WorkGroupInfo> grid = new Grid<WorkGroupInfo>(store, cm);
		grid.getView().setAutoExpandColumn(groupNameColumn);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.setBorders(false);

		grid.setColumnReordering(false);

		grid.addRowClickHandler(new RowClickHandler() {
			@Override
			public void onRowClick(RowClickEvent event) {
				int workGroupID = event.getRowIndex();
				WorkGroupInfo workGroupOnSelectedRow = store.get(workGroupID);
				
				newWorkGroupID = workGroupOnSelectedRow.getWorkGroupID();
				workGroupName = workGroupOnSelectedRow.getWorkGroupName();
			}
		});
		
		switchGroupButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				
				User.getInstance().setActiveGroupID(newWorkGroupID);
				Info.display("Group changed", "Active group: " + workGroupName);
				newWorkGroupID = 0;
				workGroupName = "";
				
			}

		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(switchGroupButton);

		VerticalLayoutContainer verticalContainer = new VerticalLayoutContainer();
		gridContentPanel.add(verticalContainer);

		verticalContainer.add(grid);
		verticalContainer.add(hp);
		return gridContentPanel;

	}

	
}
