package com.project.service;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bean.Card;
import com.project.bean.Station;
import com.project.bean.Transaction;
import com.project.persistence.TransactionDao;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionDao transactionDao;
	

	@Override
	public int swipeInStatus(Card cardId){
		Transaction transaction= transactionDao.swipeInStatus(cardId);
		if(transaction!=null) 
			return transaction.getTransactionId();
		return 0;
	}
	
	@Override
	public boolean swipeInCard(Card cardId, Station stationId){
			
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
		String formatedDateTime = current.format(format);
		
//		Transaction transaction = new Transaction();		
//		transaction.setCard(cardId);
//		transaction.setStation(stationId);
//		transaction.setSwipeInTime(formatedDateTime);
//		transactionDao.save(transaction);
		int transactionId = transactionDao.getMax();
		int flag= transactionDao.insertSwipeIn(++transactionId,cardId, stationId,formatedDateTime);
		if(flag>0)
			return true;
		return false;
	}

	@Override
	public int[] swipeOut(Card cardId){
		
		Transaction transaction= transactionDao.swipeInStatus(cardId);
		//TransactionPK tr = transaction.getTransactionId();
		Station station = transaction.getStation();
		int[] details = new int[] {station.getStationId(),transaction.getTransactionId()};
		return details;
	}

	@Override
	public boolean swipeOutUpdate(int stationId, int transactionId, int fare){
		
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
		String currentDate = current.format(format);
		int flag= transactionDao.swipeOutUpdate(stationId,transactionId, fare, currentDate);
		if(flag>0)
			return true;
		return false;
	}

	@Override
	public int checkPenality(int transactionId){
		String timeDetails = transactionDao.checkPenality(transactionId);
		String[] swipeInTime =timeDetails.split(" ",5);
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
		String currentDate = current.format(format);
		String[] swipeOutTime =currentDate.split(" ",5);
		//System.out.println();
		if(swipeInTime[0].equals(swipeOutTime[0])) {
			String[] timeIn = swipeInTime[1].split(":",5);
			String[] timeOut = swipeOutTime[1].split(":",5);
			int hour = Integer.parseInt(timeOut[0])-Integer.parseInt(timeIn[0]);
			int min = Integer.parseInt(timeOut[1])-Integer.parseInt(timeIn[1]);
			//System.out.println(min+" "+hour);
			int penality = hour*60+min;
			penality = penality/90;
			//System.out.println(penality);
			return penality;
		}
		return 0;
		
	}

	@Override
	public boolean updatePenality(int transactionId, int amount){
		int flag= transactionDao.updatePenality(transactionId, amount);
		if(flag>0)
			return true;
		return false;
		
	}


	@Override
	public Transaction displayDetails(int transactionId){
		return transactionDao.displayDetails(transactionId);
	}
	
	
	
}
