package com.api.enrollee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.enrollee.model.Enrollee;

public interface EnrolleeRepository extends JpaRepository<Enrollee, Long>{

}
