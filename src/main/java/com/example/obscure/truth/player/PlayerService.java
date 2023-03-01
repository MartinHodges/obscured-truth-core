package com.example.obscure.truth.player;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.obscure.truth.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlayerService {

	private final PlayerRepository playerRepo;
	
	public PlayerEntity getPlayerEntityByUuid(String uuid) {
		
		PlayerEntity player = playerRepo.findById(UUID.fromString(uuid))
				.orElseThrow(() -> new NotFoundException("Player not found"));
		
		return player;
	}
	
	public PlayerEntity updateEntity(PlayerEntity player) {
		return playerRepo.save(player);
	}
}
