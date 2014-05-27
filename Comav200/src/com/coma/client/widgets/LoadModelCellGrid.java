package com.coma.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.Comav200;
import com.coma.client.ModelInfo;
import com.coma.client.ModelInfoProperties;
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

public class LoadModelCellGrid {
	private static final ModelInfoProperties props = GWT.create(ModelInfoProperties.class);

	private static ContentPanel gridContentPanel;

	private static List<ModelInfo> modelInfoList;
	static ListStore<ModelInfo> store;

	public static ContentPanel createLoadModelCellGrid() {

		ColumnConfig<ModelInfo, String> groupNameColumn = new ColumnConfig<ModelInfo, String>(props.modelGroupName(), 75, ("Group"));
		ColumnConfig<ModelInfo, String> modelNameColumn = new ColumnConfig<ModelInfo, String>(props.modelName(), 100, "Model name");
		ColumnConfig<ModelInfo, String> creationDateColumn = new ColumnConfig<ModelInfo, String>(props.modelCreationDate(), 150, "Creation date");
		

		
		List<ColumnConfig<ModelInfo, ?>> l = new ArrayList<ColumnConfig<ModelInfo, ?>>();
		
		l.add(modelNameColumn);
		l.add(groupNameColumn);
		l.add(creationDateColumn);
		ColumnModel<ModelInfo> cm = new ColumnModel<ModelInfo>(l);

		store = new ListStore<ModelInfo>(props.key());
		store.addAll(modelInfoList);

		gridContentPanel = new ContentPanel();
		gridContentPanel.setHeight(600);
		gridContentPanel.setBodyBorder(false);
		gridContentPanel.setBorders(false);
		gridContentPanel.setHeaderVisible(false);

		final Grid<ModelInfo> grid = new Grid<ModelInfo>(store, cm);
		grid.getView().setAutoExpandColumn(modelNameColumn);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.setBorders(false);

		grid.setColumnReordering(false);

		grid.addRowClickHandler(new RowClickHandler() {

			@Override
			public void onRowClick(RowClickEvent event) {
				int modelID = event.getRowIndex();
				ModelInfo modelOnSelectedRow = store.get(modelID);

				Comav200.GetInstance().loadModelFromCellList(modelOnSelectedRow.getModelID());
				Comav200.GetInstance().setActiveModelID(modelOnSelectedRow.getModelID());
			}
		});

		VerticalLayoutContainer verticalContainer = new VerticalLayoutContainer();
		gridContentPanel.add(verticalContainer);

		verticalContainer.add(grid, new VerticalLayoutData(1, 1));

		return gridContentPanel;

	}

	public static List<ModelInfo> getModelInfoList() {
		return modelInfoList;
	}

	public static void setModelInfoList(List<ModelInfo> modelInfoList) {
		LoadModelCellGrid.modelInfoList = modelInfoList;
	}
}


