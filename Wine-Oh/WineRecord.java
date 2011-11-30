package com.hotuba.WineSnob;

public class WineRecord {
	
	private String WineName;
	private String notes;
	
	public WineRecord(String name, String notes){
		this.notes = notes;
		this.WineName = name;
	}
	
	public void SetName(String name){
		this.WineName = name;
	}
	
	public void SetNotes(String notes){
		this.notes = notes;
	}
	
	public String GetName(){
		return this.WineName;
	}
	
	public String GetNotes(){
		return this.notes;
	}

}
