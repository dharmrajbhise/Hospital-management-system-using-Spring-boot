package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Medicines;

@Repository
public interface MediRepo extends JpaRepository<Medicines, Long> {

    List<Medicines> findByUsername(String username);


}
