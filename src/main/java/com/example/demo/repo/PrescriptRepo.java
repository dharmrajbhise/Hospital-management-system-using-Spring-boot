package com.example.demo.repo;

import com.example.demo.entity.Prescriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptRepo extends JpaRepository<Prescriptions, Long> {

	List<Prescriptions> findByUsername(String username);


    List<Prescriptions> findByDate(LocalDate date);

}
