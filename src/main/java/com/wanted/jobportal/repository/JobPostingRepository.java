package com.wanted.jobportal.repository;

import com.wanted.jobportal.domain.Company;
import com.wanted.jobportal.domain.JobPosting;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

}
