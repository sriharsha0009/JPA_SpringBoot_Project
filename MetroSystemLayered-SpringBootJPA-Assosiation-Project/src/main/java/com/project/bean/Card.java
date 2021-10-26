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
@Table(name = "Card")
public class Card {

	@Id
	@Column(name = "CardId")
	private int CardId;
	private int balance;
	private String creationDate, holderName;
	@OneToMany(mappedBy = "station")
    private Set<Transaction> StationId = new HashSet<Transaction>();
	public Card() {
		
	}
	public Card(int CardId,String holderName, int balance, String creationDate) {
		super();
		this.CardId = CardId;
		this.holderName=holderName;
		this.balance = balance;
		this.creationDate = creationDate;
	}
	public int getCardId() {
		return CardId;
	}
	public void setCardId(int cardId) {
		this.CardId = cardId;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	@Override
	public String toString() {
		return "Card [cardId=" + CardId + ", balance=" + balance + ", creationDate=" + creationDate + ", holderName="
				+ holderName + "]";
	}
	
}
