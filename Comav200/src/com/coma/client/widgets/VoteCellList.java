package com.coma.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.coma.client.Comav200;
import com.coma.client.DiagramInfo;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class VoteCellList {

  private static final List<String> DAYS = Arrays.asList("Sunday", "Monday",
  	      "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
  
  
 
  private static class MyDataProvider extends AsyncDataProvider<String> {
	    /**
	     * {@link #onRangeChanged(HasData)} is called when the table requests a new
	     * range of data. You can push data back to the displays using
	     * {@link #updateRowData(int, List)}.
	     */
		@Override
		protected void onRangeChanged(HasData<String> display) {
			// Get the new range.
		      final Range range = display.getVisibleRange();

		      /*
		       * Query the data asynchronously. If you are using a database, you can
		       * make an RPC call here. We'll use a Timer to simulate a delay.
		       */
		        	int start = range.getStart();
			        int length = range.getLength();
			        List<String> newData = new ArrayList<String>();
		        	
			        for (DiagramInfo diagramInfo : diagramInfoList)
			        {
		        			String diagramCreator = diagramInfo.getDiagramCreator();
		        			String diagramName = diagramInfo.getDiagramName();
		        			String diagramCreationDate = diagramInfo.getDiagramCreationDate();
				            newData.add("Creator: " + diagramCreator + " Diagram name: " + diagramName + " Diagram creation date " + diagramCreationDate);
      
		        	}

		          // Push the data to the displays. AsyncDataProvider will only update
		          // displays that are within range of the data.
		          updateRowData(start, newData);
		}
	  }

public static List<DiagramInfo> diagramInfoList;
  
  public CellList<String> votingPanel()
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
    
      return cellList;
  }
  
  public void setMap(List<DiagramInfo> result) {
	  this.diagramInfoList = result;
  }
  }
	

