package com.coma.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.coma.client.Comav200;
import com.coma.client.ModelInfo;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class VoteCellList {

	private static class MyDataProvider extends AsyncDataProvider<String> {

		@Override
		protected void onRangeChanged(HasData<String> display) {
			// Get the new range.
			final Range range = display.getVisibleRange();

			/*
			 * Query the data asynchronously. If you are using a database, you can
			 * make an RPC call here. We'll use a Timer to simulate a delay.
			 */
			int start = range.getStart();
			List<String> newData = new ArrayList<String>();

			for (ModelInfo modelInfo : getModelInfoList())
			{
				String modelCreator = modelInfo.getModelCreator();
				String modelName = modelInfo.getModelName();
				String modelCreationDate = modelInfo.getModelCreationDate();
				newData.add("Creator: " + modelCreator + " Diagram name: " + modelName + " Diagram creation date " + modelCreationDate);
			}

			// Push the data to the displays. AsyncDataProvider will only update
			// displays that are within range of the data.
			updateRowData(start, newData);
		}
	}

	public static List<ModelInfo> modelInfoList;

	//public CellList<String> votingPanel()
	public Panel votingPanel()
	{
		// Create a CellList.
		CellList<String> cellList = new CellList<String>(new TextCell());

		// Create a data provider.
		MyDataProvider dataProvider = new MyDataProvider();

		// Add the cellList to the dataProvider.
		dataProvider.addDataDisplay(cellList);

		// Create paging controls.
		SimplePager pager = new SimplePager();
		pager.setDisplay(cellList);

		// Add a selection model to handle user selection.
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected = selectionModel.getSelectedObject();
				if (selected != null) {
					Window.alert("You selected: " + selected);
				}
			}
		});

		// Add the widgets to the root panel.
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(pager);
		vPanel.add(cellList);
		cellList.setWidth("100%");

		//return cellList;
		return vPanel;
	}

	public static List<ModelInfo> getModelInfoList() {
		System.out.println("VI ÄR I getModelInfoList");
		return modelInfoList;
	}

	public static void setModelInfoList(List<ModelInfo> modelInfoList) {
		System.out.println("VI ÄR I modelInfoList");
		VoteCellList.modelInfoList = modelInfoList;
	}
}


