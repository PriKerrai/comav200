package com.coma.client.cellgrids;

import java.util.ArrayList;
import java.util.List;

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
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class SwitchGroupCellGridForDialog {
	private static final WorkGroupInfoProperties props = GWT.create(WorkGroupInfoProperties.class);

	private static ContentPanel gridContentPanel;

	private static List<WorkGroupInfo> workGroupInfoList;
	static ListStore<WorkGroupInfo> store;
	private static int newWorkGroupID;
	private static String workGroupName;
	
	public static List<WorkGroupInfo> getWorkGroupInfoList() {
		return workGroupInfoList;
	}

	public static void setworkGroupInfoList(List<WorkGroupInfo> workGroupInfoList) {
		SwitchGroupCellGridForDialog.workGroupInfoList = workGroupInfoList;
	}

	public static ContentPanel createSwitchGroupCellGrid() {

		ColumnConfig<WorkGroupInfo, String> groupNameColumn = new ColumnConfig<WorkGroupInfo, String>(props.workGroupName(), 75, ("Group"));

		List<ColumnConfig<WorkGroupInfo, ?>> l = new ArrayList<ColumnConfig<WorkGroupInfo, ?>>();
		l.add(groupNameColumn);
		ColumnModel<WorkGroupInfo> cm = new ColumnModel<WorkGroupInfo>(l);

		store = new ListStore<WorkGroupInfo>(props.key());
		store.addAll(workGroupInfoList);

		gridContentPanel = new ContentPanel();
		gridContentPanel.setHeight(600);

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

		HorizontalPanel hp = new HorizontalPanel();

		VerticalLayoutContainer verticalContainer = new VerticalLayoutContainer();
		gridContentPanel.add(verticalContainer);

		verticalContainer.add(grid);
		verticalContainer.add(hp);
		return gridContentPanel;

	}

	public static int getNewWorkGroupID() {
		return newWorkGroupID;
	}

	public static void setNewWorkGroupID(int newWorkGroupID) {
		SwitchGroupCellGridForDialog.newWorkGroupID = newWorkGroupID;
	}

	public static String getWorkGroupName() {
		return workGroupName;
	}

	public static void setWorkGroupName(String workGroupName) {
		SwitchGroupCellGridForDialog.workGroupName = workGroupName;
	}

	
}
