package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Referral;

@Repository
public interface ReferralRepo extends JpaRepository<Referral, Long> {

	List<Referral> findByUsername(String username);

}
