package com.project.service;

import com.project.bean.Card;

public interface CardService {

	int storeCardDetails(Card passenger);
	//boolean getSwipeInStatus(int cardId)throws ClassNotFoundException, SQLException;
	int rechargeCard(int cardId,int amount);
	Card checkCard(int cardId);
	int updateFare(int cardId, int fare);
	int checkFare(int cardNumber, int i);
	int updateCardBalance(int cardNumber, int amount);
	int returnCardId();
}
