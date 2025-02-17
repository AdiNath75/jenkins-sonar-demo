package com.nath.counsellors_portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nath.counsellors_portal.entity.Counsellor;

@Repository
public interface CounsellorRepository extends JpaRepository<Counsellor, Long> {
	
	public Counsellor findByEmail(String email);
	
	public Counsellor findByEmailAndPassword(String email, String pwd);

}
