package com.project.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Station") 
public class Station {

	@Id
	@Column(name = "StationId")
	private int StationId;
	private String stationName;
	private int priority;
	@OneToMany(mappedBy = "card")
	private Set<Transaction> CardId = new HashSet<Transaction>();
	public Station() {
		
	}
	public Station(int StationId, String stationName, int priority) {
		super();
		this.StationId = StationId;
		this.stationName = stationName;
		this.priority = priority;
	}
	public int getStationId() {
		return StationId;
	}
	public void setStationId(int stationId) {
		this.StationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	@Override
	public String toString() {
		return "Station [stationId=" + StationId + ", stationName=" + stationName + ", priority=" + priority + "]";
	}
	
	
}
