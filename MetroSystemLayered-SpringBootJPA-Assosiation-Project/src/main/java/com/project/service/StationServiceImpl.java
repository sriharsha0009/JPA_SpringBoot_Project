package com.project.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bean.Card;
import com.project.bean.Station;
import com.project.persistence.StationDao;


@Service
public class StationServiceImpl implements StationService {

	@Autowired
	StationDao stationDao;
	@Autowired
	TransactionService transactionService ;
	@Autowired
	CardService cardService;


	@Override
	public Station checkStation(String stationName){
		Station station = stationDao.checkStation(stationName);
		return station;
		
	}

	@Override
	public int swipeOutCard(Card cardId, int stationId,int choice) {
		int[] details =  transactionService.swipeOut(cardId);
		//int[] priority = stationDao.fareCalculate(details[0], stationId);
		int priority[] = new int[2];
		priority[0] = stationDao.getPriority(details[0]);
		priority[1]=stationDao.getPriority(stationId);		
		int fare =(priority[0]>priority[1]) ? (priority[0]-priority[1])*5 : (priority[1]-priority[0])*5;
		if(details[0]==stationId)
			fare=0;
		int fareTotal = cardService.updateFare(cardId.getCardId(), fare);
		if(fareTotal>0 && choice==2) {				
			if(transactionService.swipeOutUpdate(stationId,details[1], fare))				
				return fareTotal;
		}
		return fareTotal;
	}

	@Override
	public ArrayList<String> getStations() {
		//return stationDao.getAllStations();
		List<Station> stations = stationDao.findAll();
		
		ArrayList<String> names=new ArrayList<String>();
		for(Station station:stations) {
			names.add(station.getStationName());
		}
		return names;
	}

}
