package com.example.demo.repo;

import com.example.demo.entity.HealthChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRepo extends JpaRepository<HealthChart,Long> {

    List<HealthChart> findByUsername(String username);
}
