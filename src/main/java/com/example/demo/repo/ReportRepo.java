package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.lab_report;

@Repository
public interface ReportRepo extends JpaRepository<lab_report, Long> {

	lab_report findByFileName(String fileName);

	List<lab_report> findByUsername(String username);

	
}
