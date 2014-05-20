
/**
 * Sencha GXT 3.1.0 - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.coma.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProposalAvgVotes  implements IsSerializable{

	private String name;
	private double avgVotes;
	
	
	private static List<String> modelCreatorName;
	private List<Double> modelAvgVotes;
	private int listSize;

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public ProposalAvgVotes(){}

	public ProposalAvgVotes(String name, int data1) {
		super();
		this.name = name;
		this.setAvgVotes(data1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<ProposalAvgVotes> getData(int size, double min, double scale) {	  
		List<ProposalAvgVotes> data = new ArrayList<ProposalAvgVotes>();

		for (int i = 0; i < size; i++) {
			data.add(new ProposalAvgVotes(modelCreatorName.get(i), (int)Math.floor(Math.max(Math.random() * scale, min))));
			
		}
		return data;
	}

	public void setUpBarChart(List<ProposalAvgVotes> result) {
		for(ProposalAvgVotes avgVotes: result) {
			modelCreatorName.add(avgVotes.getName());
			modelAvgVotes.add(avgVotes.getAvgVotes());
			
		}
	}

	public double getAvgVotes() {
		return avgVotes;
	}

	public void setAvgVotes(double avgVotes) {
		this.avgVotes = avgVotes;
	}
}
