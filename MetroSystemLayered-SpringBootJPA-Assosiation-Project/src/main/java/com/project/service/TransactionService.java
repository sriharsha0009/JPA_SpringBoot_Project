package com.project.service;

import com.project.bean.Card;
import com.project.bean.Station;
import com.project.bean.Transaction;

public interface TransactionService {

	int swipeInStatus(Card cardId);
	boolean swipeInCard(Card cardId, Station stationId);
	int[] swipeOut(Card cardId);
	boolean swipeOutUpdate(int stationId,int transactionId, int fare);
	int checkPenality(int transactionId);
	boolean updatePenality(int swipeIn, int i);
	Transaction displayDetails(int transactionId);
}
