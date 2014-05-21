package com.coma.client;

import java.util.ArrayList;
import java.util.List;

public class ProposalAvgVotesData {
	private static List<String> modelCreatorName = new ArrayList<String>();
	private List<Double> modelAvgVotes = new ArrayList<Double>();
	private int listSize;
	
	private static List<ProposalAvgVote> proposalAvgVotes;
	

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public ProposalAvgVotesData(){}

	public ProposalAvgVotesData(List<ProposalAvgVote> proposalAvgVotes){
		ProposalAvgVotesData.proposalAvgVotes = proposalAvgVotes;
		setUpBarChart(proposalAvgVotes);
		
	}
	
	
	public static List<ProposalAvgVote> getData(){
		return proposalAvgVotes;
	}
	
	public void setUpBarChart(List<ProposalAvgVote> result) {
		for(ProposalAvgVote avgVotes: result) {
			modelCreatorName.add(avgVotes.getName());
			modelAvgVotes.add(avgVotes.getAvgVote());
		}
	}


}
