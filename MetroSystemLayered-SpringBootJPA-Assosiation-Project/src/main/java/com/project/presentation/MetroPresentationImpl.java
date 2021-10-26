package com.project.presentation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import com.project.bean.Card;
import com.project.bean.Station;
import com.project.bean.Transaction;
import com.project.exception.CardNotFoundException;
import com.project.exception.MinimumBalance;
import com.project.exception.NegativeBalanceException;
import com.project.exception.NotAddedException;
import com.project.exception.NotFoundException;
import com.project.service.CardService;
import com.project.service.StationService;
import com.project.service.TransactionService;


@Component
public class MetroPresentationImpl implements MetroPresentation {
	
	@Autowired
	CardService cardService ;
	@Autowired
	StationService stationService;
	@Autowired
	TransactionService transactionService;	

	@Override
	public void showMenu() {
		System.out.println("1. Add New Card");
		System.out.println("2. SwipeIn/Out");
		System.out.println("3. Recharge your Card");
		System.out.println("4. Exit");
	}

	@Override
	public void performMenu(int choice) {
		Scanner sc = new Scanner(System.in);
		switch(choice) {
		case 1:
			System.out.print("Enter Card Holder Name : ");
			String name = sc.next();
			
			LocalDateTime current = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
			String creationDate = current.format(format);
			System.out.print("Enter Balance to Load : ");
			try {
				int balance = sc.nextInt();
				if(balance<=0)
					throw new MinimumBalance("Balance should be greater than 0");	
			int cardID = cardService.returnCardId();			
			Card card = new Card(++cardID,name, balance,creationDate);
				int flag = cardService.storeCardDetails(card);
				if(flag!=0)
					System.out.println("Added Successfully and your card number is "+cardID);
				else
					throw new NotAddedException("Not Added");			
			
			}
			catch(MinimumBalance e) {
				System.out.println("Balance should be greater than 0");
			}
			catch(NotAddedException e) {
				System.out.println("Not Added");
			}catch(InputMismatchException e) {
				System.out.println("Input not Matched Exception.");
			}
			System.out.println("=======================");
			System.out.println();
		break;
		
		case 2:
			
			try {
			perfromCard();
			}
			catch(MinimumBalance m) {
				System.out.println("Balance is below 20.");
			}
			catch(CardNotFoundException e) {
				System.out.println("Card Not Found with given number.");
			}
			catch(InputMismatchException e) {
				System.out.println("Input Not Matched Exception");
			}
			catch(NotFoundException e) {
				System.out.println("Station Not Found with given name.");
			}
			System.out.println("=======================");
			System.out.println();
			break;		
		case 3:
			
			try {
				System.out.print("Enter Card number : ");
				int cardId = sc.nextInt();
				Card flag = cardService.checkCard(cardId);
				if(flag==null)
					throw new CardNotFoundException("No card with this number");
				rechargeCard(cardId);
				
			}catch(CardNotFoundException e) {
				System.out.println("Card Not Found with given Number.");
			}catch(InputMismatchException e) {
				System.out.println("Input Not Matched Exception");
			}catch(NotAddedException e) {
				System.out.println("Not Added");
			}catch(NegativeBalanceException e) {
				System.out.println("Balance should be greater than 0");
			}
			System.out.println("=======================");
			System.out.println();
			break;
		case 4:
			System.out.println();
			System.out.println("Thanks...");
			System.out.println();
			System.exit(0);
		default:
			System.out.println();
			System.out.println("Enter Valid Choice");
			System.out.println("=======================");
			System.out.println();
			break;
		}

	}
	@Override
	public void perfromCard(){
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your Card Number : ");
	
		int swipeIn=-1;
		int cardNumber = sc.nextInt();
		Card card=cardService.checkCard(cardNumber);
		if(card==null)
			throw new CardNotFoundException("No card with this number");
		if(card.getBalance()<20)
			throw new MinimumBalance("Balance is below 20.");
		if(card!=null)
			swipeIn = transactionService.swipeInStatus(card);
		ArrayList<String> stations = stationService.getStations();
		System.out.print("\nStations are ");
		for (int i = 0; i < stations.size(); i++)
            System.out.print(stations.get(i) + "  ");
		System.out.println();
		if(swipeIn<=0) {
			
			System.out.print("Enter Station Name to Swipe In : ");
			String stationName = sc.next();
			
			Station station = stationService.checkStation(stationName);
			if(station==null)
				throw new NotFoundException("No station with the name");
			else {
				boolean flag = transactionService.swipeInCard(card, station);
				if(flag)
					System.out.println("\nYou have successfully swiped in at the station "+stationName);
				else
					System.out.println("Some Error");
				}
			
			}
		
		else {
			
				int balance2;
				boolean flag;
				
				int penality = transactionService.checkPenality(swipeIn);
				//System.out.println(penality);
				int balance=0;
				penality=penality<=-1?0:penality;
				if(penality>0) {
					System.out.println("Penality of "+penality*20+" Rs for "+penality*90+" mins.");
					
					balance = cardService.checkFare(cardNumber, penality*20);	
				}
				System.out.print("Enter Station Name to Swipe Out : ");
				String destinationStation = sc.next();
				Station stationId = stationService.checkStation(destinationStation);
				if(stationId==null)
					throw new NotFoundException("No Station with the Name");
					//System.out.println("No station with the name.");
				else {
					
					Card card1= cardService.checkCard(cardNumber);
					int remainingBalance = stationService.swipeOutCard(card1, stationId.getStationId(),1);
					remainingBalance=remainingBalance-penality*20;
					if(remainingBalance<0)
						
						System.out.println("No sufficient funds found, add "+Math.abs(remainingBalance)+" to continue.");
					else {
						if(penality!=0)
							flag= transactionService.updatePenality(swipeIn,penality*20);
						balance2=stationService.swipeOutCard(card1, stationId.getStationId(), 2);
						balance2 = cardService.updateCardBalance(cardNumber, remainingBalance);
						Transaction transaction = new Transaction();
						transaction=transactionService.displayDetails(swipeIn);
						System.out.println("\nYour Travel details :\nFrom L"+" : "+transaction.getSwipeInTime()+"\nTo   L"+transaction.getDestinationStationId()+" : "+transaction.getSwipeOutTime());
						System.out.println("\nYour Total Fare is "+transaction.getFare()+" with penality of "+penality*20);
					System.out.println("\nYou have successfully Swiped Out with card balance as "+remainingBalance);
				}
			}
		}
		
	}

	@Override
	public void rechargeCard(int cardId){
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter Recharge Amount : ");
		int amount = sc.nextInt();
		if(amount<=0)
			throw new NegativeBalanceException("Negative Balance cannot be added.");
		int amount1 = cardService.rechargeCard(cardId,amount);
		int allAmount =cardService.updateCardBalance(cardId, amount1);
		if(allAmount>0)
			System.out.println("Final amount after adding is "+allAmount);
		else
			throw new NotAddedException("Not Added");
			//System.out.println("Not Added");
	}
}
			


