package com.etl.system.repository;

import com.etl.system.entity.ProcessedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedDataRepository extends JpaRepository<ProcessedData, Long> {
}