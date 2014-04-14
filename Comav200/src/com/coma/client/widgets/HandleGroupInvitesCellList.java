
package com.coma.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.ActiveInvite;
import com.coma.client.Comav200;
import com.coma.client.ModelInfo;
import com.coma.client.WorkGroupInfo;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class HandleGroupInvitesCellList {


	public static List<WorkGroupInfo> inviteList;

	private static class MyDataProvider extends AsyncDataProvider<String> {

		@Override
		protected void onRangeChanged(HasData<String> display) {
			// Get the new range.
			final Range range = display.getVisibleRange();

			int start = range.getStart();
			List<String> newData = new ArrayList<String>();

			for (WorkGroupInfo inviteInfo : getInviteList())
			{
				
				int inviteID = inviteInfo.getWorkGroupInviteID();
				int groupID = inviteInfo.getWorkGroupID();
				
				System.out.println("inv: " + inviteID);
				System.out.println("grupp: " + groupID);
				
				String groupName = inviteInfo.getWorkGroupName();
				String facilitatorName = inviteInfo.getFacilitatorName();
				newData.add(inviteID + "/" +  groupID + "/ " + groupName + " - " + facilitatorName);
			}

			// Push the data to the displays. AsyncDataProvider will only update
			// displays that are within range of the data.
			updateRowData(start, newData);
		}
	}


	public CellList<String> loadModelPanel()
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
					String[] inviteID = selected.split("/");
					int count = 0;
					for(String hej: inviteID){
						System.out.println(hej +" : " + count);
						count++;
					}
					
					//Window.alert("You selected: " + modelID[1] + "ModelID: " + modelID[0]); 
					ActiveInvite.getInstance().setInviteID(Integer.parseInt(inviteID[0]));
					ActiveInvite.getInstance().setGroupID(Integer.parseInt(inviteID[1]));
				}
			}
		});
		cellList.setWidth("100%");

		return cellList;
	}

	public static List<WorkGroupInfo> getInviteList() {
		return inviteList;
	}

	public static void setInviteList(List<WorkGroupInfo> inviteList) {
		HandleGroupInvitesCellList.inviteList = inviteList;
	}
}

