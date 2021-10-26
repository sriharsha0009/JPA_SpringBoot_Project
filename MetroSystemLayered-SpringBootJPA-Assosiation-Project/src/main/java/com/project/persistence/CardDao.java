package com.project.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.bean.Card;

@Repository
public interface CardDao  extends JpaRepository<Card, String> {

	@Query(value="select max(CardId) from Card")
	public int returnCardId();
	
	@Query(value="select c from Card c where CardId =:cid")
	public Card getCard(@Param("cid") int cardId);

	@Query(value="select balance from Card where CardId =:cid")
	public int rechargeCard(@Param("cid") int cardId);

	@Query(value="SELECT c FROM Card c WHERE c.CardId =:cid")
	public Card checkCardDetails(@Param("cid")int cardId);

	@Query(value="SELECT balance FROM Card WHERE CardId =:cid")
	public int updateFare(@Param("cid")int cardId);

	@Transactional
	@Modifying
	@Query(value="UPDATE Card SET balance =:bal WHERE CardId =:cid")
	public int updateCardBalance(@Param("cid")int cardNumber, @Param("bal")int amount);

}
