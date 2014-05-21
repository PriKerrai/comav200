
/**
 * Sencha GXT 3.1.0 - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProposalAvgVote  implements IsSerializable{

	private String name;
	private double avgVote;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAvgVote() {
		return avgVote;
	}
	public void setAvgVote(double avgVote) {
		this.avgVote = avgVote;
	}
	
	
}