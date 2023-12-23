package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Receipt;

@Repository
public interface ReceiptRepo extends JpaRepository<Receipt, Long> {

	List<Receipt> findByUsername(String username);
	
	

}
