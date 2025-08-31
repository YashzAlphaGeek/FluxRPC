package com.unogame.uno_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unogame.uno_backend.model.Player;

public interface PlayerRepository extends JpaRepository<Player, String> {}
