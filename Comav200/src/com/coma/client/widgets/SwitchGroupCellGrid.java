package com.coma.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.WorkGroupInfo;
import com.coma.client.WorkGroupInfoProperties;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class SwitchGroupCellGrid {
	private static final WorkGroupInfoProperties props = GWT.create(WorkGroupInfoProperties.class);

	private static ContentPanel gridContentPanel;

	private static List<WorkGroupInfo> workGroupInfoList;
	static ListStore<WorkGroupInfo> store;

	public static ContentPanel createSwitchGroupCellGrid() {

		ColumnConfig<WorkGroupInfo, String> groupNameColumn = new ColumnConfig<WorkGroupInfo, String>(props.workGroupName(), 75, ("Group"));

		

		
		List<ColumnConfig<WorkGroupInfo, ?>> l = new ArrayList<ColumnConfig<WorkGroupInfo, ?>>();
		
		l.add(groupNameColumn);
		ColumnModel<WorkGroupInfo> cm = new ColumnModel<WorkGroupInfo>(l);

		store = new ListStore<WorkGroupInfo>(props.key());
		store.addAll(workGroupInfoList);

		gridContentPanel = new ContentPanel();
		gridContentPanel.setHeight(600);
		gridContentPanel.setBodyBorder(false);
		gridContentPanel.setBorders(false);
		gridContentPanel.setHeaderVisible(false);

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

				
			}
		});

		VerticalLayoutContainer verticalContainer = new VerticalLayoutContainer();
		gridContentPanel.add(verticalContainer);

		verticalContainer.add(grid, new VerticalLayoutData(1, 1));

		return gridContentPanel;

	}

	public static List<WorkGroupInfo> getWorkGroupInfoList() {
		return workGroupInfoList;
	}

	public static void setworkGroupInfoList(List<WorkGroupInfo> workGroupInfoList) {
		SwitchGroupCellGrid.workGroupInfoList = workGroupInfoList;
	}
}
