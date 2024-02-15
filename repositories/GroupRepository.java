package com.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.models.Groups;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {

}
