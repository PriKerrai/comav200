package com.coma.client;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ModelInfoProperties extends PropertyAccess<ModelInfo> {
	 @Path("modelID")
	  ModelKeyProvider<ModelInfo> key();
	   
	  @Path("name")
	  LabelProvider<ModelInfo> nameLabel();
	 
	  ValueProvider<ModelInfo, Integer> modelID();
	   
	  ValueProvider<ModelInfo, Integer> modelGroupID();
	  
	  ValueProvider<ModelInfo, String> modelGroupName();
	   
	  ValueProvider<ModelInfo, Integer> modelCreatorID();
	  
	  ValueProvider<ModelInfo, String> modelCreatorName();
	   
	  ValueProvider<ModelInfo, Integer> modelType();
	   
	  ValueProvider<ModelInfo, String> modelString();
	   
	  ValueProvider<ModelInfo, String> modelName();
	   
	  ValueProvider<ModelInfo, String> modelCreationDate();
	  
	  ValueProvider<ModelInfo, Integer> isProposal();

	}

