package com.example.demo.repo;

import com.example.demo.entity.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DctorsRepo extends JpaRepository<Doctors,Long> {

}
