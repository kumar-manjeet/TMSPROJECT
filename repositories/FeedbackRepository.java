package com.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tms.models.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
