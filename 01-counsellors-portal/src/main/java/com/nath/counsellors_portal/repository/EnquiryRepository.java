package com.nath.counsellors_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nath.counsellors_portal.entity.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
	
	@Query(value = "select * from enquiries where counsellor_Id=:counsId", nativeQuery = true)
	public List<Enquiry> getEnquriesByCounsellorId(Long counsId);
}
