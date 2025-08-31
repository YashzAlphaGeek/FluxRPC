package com.unogame.uno_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unogame.uno_backend.model.Player;
import com.unogame.uno_backend.repository.PlayerRepository;

@Service
public class UserService {

    private final PlayerRepository playerRepository;

    @Autowired
    public UserService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

   public Player registerUser(String playerName) {
    return playerRepository.findByName(playerName)
            .orElseGet(() -> {
                Player player = new Player(UUID.randomUUID().toString(), playerName);
                playerRepository.save(player);
                return player;
            });
}

}
