package com.vamshi.securecard.securecard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vamshi.securecard.securecard.models.Query;

@Repository
public interface QueryRepo extends JpaRepository<Query, Integer> {

}
