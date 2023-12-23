package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Certificates;

@Repository
public interface CertifyRepo extends JpaRepository<Certificates, Long> {

	List<Certificates> findByUsername(String username);

}
