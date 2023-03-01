package com.example.obscure.truth.games;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.obscure.truth.exceptions.NotFoundException;
import com.example.obscure.truth.player.PlayerEntity;
import com.example.obscure.truth.player.PlayerGetDTO;
import com.example.obscure.truth.player.PlayerPostDTO;
import com.example.obscure.truth.player.PlayerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GamesService {

	private final GamesRepository gamesRepo;
	private final PlayerRepository playerRepo;

	public GameEntity getEntityByUUID(String uuid) {
		
		GameEntity game = gamesRepo.findById(UUID.fromString(uuid))
				.orElseThrow(() -> new NotFoundException("Game not found"));
		
		return game;
	}
	
	public GameEntity updateEntity(GameEntity game) {

		return gamesRepo.save(game);
	}
	
	public GameGetDTO start(String uuid) {

		GameEntity game = getEntityByUUID(uuid);

		game.setState(GameState.READY);
		
		game = gamesRepo.save(game);
		
		return new GameGetDTO(game);
	}
	
	public GameGetDTO getByUUID(String uuid) {
		
		GameEntity game = getEntityByUUID(uuid);
		
		return new GameGetDTO(game);
	}
	
	public GameGetDTO getByPin(int pin) {
		
		GameEntity game = gamesRepo.findByPin(pin)
				.orElseThrow(() -> new NotFoundException("Game not found"));
		
		return new GameGetDTO(game);
	}
	
	public PlayerGetDTO create(GamePostDTO gameDTO) {
		
		GameEntity newGame = gameDTO.toEntity();
		
		int pin = (int)(Math.random() * 100000);
		
		while (gamesRepo.findByPin(pin).isPresent()) {
			pin++;
		}
		
		newGame.setPin(pin);
		
		PlayerEntity player = gameDTO.getPlayer().toEntity();
		newGame.addPlayer(player);
		
		newGame = updateEntity(newGame);
				
		return new PlayerGetDTO(newGame, player);
	}
	
	public PlayerGetDTO registerPlayerByPin(int pin, PlayerPostDTO playerDto) {
		
		GameEntity game = gamesRepo.findByPin(pin)
				.orElseThrow(() -> new NotFoundException("Game not found"));

		PlayerEntity player = new PlayerEntity();
		player.setName(playerDto.getName());
		player = playerRepo.save(player);
		game.addPlayer(player);

		game = updateEntity(game);
		
		return new PlayerGetDTO(game, player);
	}
}
