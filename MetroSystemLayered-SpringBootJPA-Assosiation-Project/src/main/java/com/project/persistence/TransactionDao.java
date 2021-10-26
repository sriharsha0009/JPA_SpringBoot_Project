package com.project.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.bean.Card;
import com.project.bean.Station;
import com.project.bean.Transaction;

@Repository
public interface TransactionDao  extends JpaRepository<Transaction, String>{

	@Query(value="select t from Transaction t where t.card =:cId and t.DestinationStationId=0")
	Transaction swipeInStatus(@Param("cId")Card cardId);

	@Transactional
	@Modifying
	@Query(value="insert into Transaction(transactionId,cardId,StationId,swipeInTime,DestinationStationId,fare,penality) values(:tid,:cid,:sid,:date,0,-1,-1)", nativeQuery = true)
	int insertSwipeIn(@Param("tid")int tId,@Param("cid")Card cardId,@Param("sid") Station stationId, @Param("date")String formatedDateTime);


//	@Query(value="SELECT t FROM Transaction t WHERE t.cardId =:cid AND t.DestinationStationId=0")
//	Transaction swipeOut(@Param("cid")int cardId);

	@Transactional
	@Modifying
	@Query("UPDATE Transaction SET DestinationStationId =:did,swipeOutTime=:time,fare=:fare WHERE transactionId =:tid")
	int swipeOutUpdate(@Param("did")int stationId,@Param("tid") int transactionId,@Param("fare") int fare, @Param("time")String currentDate);

	@Query(value="SELECT swipeInTime FROM Transaction where transactionId =:tid")
	String checkPenality(@Param("tid")int transactionId);

	@Transactional
	@Modifying
	@Query("UPDATE Transaction SET penality =:amount WHERE transactionId =:tid")
	int updatePenality(@Param("tid")int transactionId,@Param("amount") int amount);

	@Query("SELECT t FROM Transaction t WHERE t.transactionId=:tid")
	Transaction displayDetails(@Param("tid")int transactionId);

	@Query("SELECT max(transactionId) FROM Transaction")
	int getMax();

//	@Transactional
//	@Modifying
//	@Query("insert into Transaction(cardId,SourceStationId,swipeInTime) values(:cid,:sid,:date)")
//	int insertSwipeIn(int cardId, int stationId, String formatedDateTime);

}
