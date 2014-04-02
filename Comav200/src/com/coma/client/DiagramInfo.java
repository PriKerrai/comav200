package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DiagramInfo implements IsSerializable {

	//public int diagramID;
	private String diagramName;
	private String diagramCreationDate;
	private String diagramCreator;
	
	public DiagramInfo() {}
	
	//public int getDiagramID() {
    //    return diagramID;
    //}

    //public void setDiagramID(int diagramID) {
    //   this.diagramID = diagramID;
    //}
    
    public String getDiagramCreator() {
		return diagramCreator;
    }

    public void getDiagramCreator(String diagramCreator) {
       this.diagramCreator = diagramCreator;
    }
    
    public String getDiagramName() {
        return diagramName;
    }

    public void getDiagramName(String diagramName) {
       this.diagramName = diagramName;
    }
    
    public String getDiagramCreationDate() {
		return diagramCreationDate;
    }

    public void getDiagramCreationDate(String diagramCreationDate) {
       this.diagramCreationDate = diagramCreationDate;
    }
    
    public DiagramInfo(String diagramCreator, String diagramName, String diagramCreationDate) {
    	this.diagramCreator = diagramCreator;
    	this.diagramName = diagramName;
    	this.diagramCreationDate = diagramCreationDate;
    }
}
	
