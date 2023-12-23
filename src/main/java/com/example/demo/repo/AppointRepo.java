package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Appoinments;

@Repository
public interface AppointRepo extends JpaRepository<Appoinments, Long> {

	List<Appoinments> findByName(String name);

	List<Appoinments> findByDate(String date);

	List<Appoinments> findByDoctor(String doctor);

}
