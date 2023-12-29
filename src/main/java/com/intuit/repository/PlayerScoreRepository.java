package com.intuit.repository;

import com.intuit.entity.PlayerScore;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Repository
public interface PlayerScoreRepository  extends JpaRepository<PlayerScore, UUID> {
    Page<PlayerScore> findAll(Pageable pageable);
}
