package com.project.service;

import java.util.ArrayList;

import com.project.bean.Card;
import com.project.bean.Station;

public interface StationService {

	//boolean swipeInCard(int passengerID, int stationId) throws SQLException, ClassNotFoundException;
	Station checkStation(String stationName);
	int swipeOutCard(Card passengerId, int stationId, int choice);
	
	ArrayList<String> getStations();
}
