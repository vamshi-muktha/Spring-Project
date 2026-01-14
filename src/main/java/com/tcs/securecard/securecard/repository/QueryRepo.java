package com.tcs.securecard.securecard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.securecard.securecard.models.Query;

@Repository
public interface QueryRepo extends JpaRepository<Query, Integer> {

}
