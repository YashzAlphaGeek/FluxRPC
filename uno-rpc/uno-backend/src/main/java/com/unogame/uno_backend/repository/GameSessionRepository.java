package com.unogame.uno_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unogame.uno_backend.model.GameSession;

public interface GameSessionRepository extends JpaRepository<GameSession, String> {}
