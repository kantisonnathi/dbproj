package com.dbproj.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BookTimeslotRepositry extends JpaRepository<Booking,Integer> {
    List<Booking> findByYearAndMonthAndDay(Integer year, Integer month, Integer day);
}
