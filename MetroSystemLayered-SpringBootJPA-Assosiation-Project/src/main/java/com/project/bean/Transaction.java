package com.project.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "Transaction") 
public class Transaction {


	@Id
	@GeneratedValue
	private int transactionId;
	@ManyToOne
	@MapsId("CardId")
	@JoinColumn(name = "CardId")
	private Card card;

	@ManyToOne
	@MapsId("StationId")
	@JoinColumn(name = "StationId")
	private Station station;
	
	private int DestinationStationId=0;
	private String swipeInTime;
	private String swipeOutTime=null;
	private int fare;
	private int penality;
	public Transaction() {
		
	}
	
	public Transaction(int transactionId, Card card, Station station, int destinationStationId, String swipeInTime,
			String swipeOutTime, int fare, int penality) {
		super();
		this.transactionId = transactionId;
		this.card = card;
		this.station = station;
		DestinationStationId = destinationStationId;
		this.swipeInTime = swipeInTime;
		this.swipeOutTime = swipeOutTime;
		this.fare = fare;
		this.penality = penality;
	}

	public int getDestinationStationId() {
		return DestinationStationId;
	}
	public void setDestinationStationId(int destinationStationId) {
		DestinationStationId = destinationStationId;
	}
	public String getSwipeInTime() {
		return swipeInTime;
	}
	public void setSwipeInTime(String swipeInTime) {
		this.swipeInTime = swipeInTime;
	}
	public String getSwipeOutTime() {
		return swipeOutTime;
	}
	public void setSwipeOutTime(String swipeOutTime) {
		this.swipeOutTime = swipeOutTime;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public int getPenality() {
		return penality;
	}
	public void setPenality(int penality) {
		this.penality = penality;
	}
	
	public void setTransactionId(int transactionId) {
		this.transactionId=transactionId;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	@Override
	public String toString() {
		return "Transaction [transacationId=" + transactionId + ",DestinationStationId=" + DestinationStationId + ", swipeInTime=" + swipeInTime
				+ ", swipeOutTime=" + swipeOutTime + ", fare=" + fare + ", penality=" + penality + "]";
	}
	
	
	
}
