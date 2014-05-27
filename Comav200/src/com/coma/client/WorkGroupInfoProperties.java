package com.coma.client;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface WorkGroupInfoProperties extends PropertyAccess<WorkGroupInfo> {
	@Path("workGroupID")
	  ModelKeyProvider<WorkGroupInfo> key();
	   
	  @Path("workGroupName")
	  LabelProvider<WorkGroupInfo> nameLabel();
	 
	  ValueProvider<WorkGroupInfo, Integer> workGroupInviteID();
	  ValueProvider<WorkGroupInfo, Integer> workGroupID();
	  ValueProvider<WorkGroupInfo, String> workGroupName();
	  ValueProvider<WorkGroupInfo, Integer> workGroupFacilitator();
	  ValueProvider<WorkGroupInfo, String> facilitatorName();
	  
}
