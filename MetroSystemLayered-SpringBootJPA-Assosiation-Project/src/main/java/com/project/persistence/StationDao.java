package com.project.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.bean.Station;

@Repository
public interface StationDao  extends JpaRepository<Station, String>{

	@Query(value="select s from Station s where s.stationName =:name")
	Station checkStation(@Param("name")String stationName);

	@Query(value="select priority from Station where StationId =:sId")
	int getPriority(@Param("sId")int stationId);

}
