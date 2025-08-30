package com.unogame.uno_backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.unogame.uno_backend.model.Player;

@Service
public class UserService {

    public Player registerUser(String playerName) {
        return new Player(UUID.randomUUID().toString(), playerName);
    }
}
