package com.example.iticket.repository;

import com.example.iticket.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    Performance findByPtitle(String ptitle);
}
