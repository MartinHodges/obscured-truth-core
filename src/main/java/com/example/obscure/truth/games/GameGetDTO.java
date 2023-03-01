package com.example.obscure.truth.games;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.obscure.truth.player.PlayerGetDTO;
import com.example.obscure.truth.rounds.RoundGetDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Provides information about a game and its participants")

public class GameGetDTO {

	@Schema(description = "Name of this game", example = "Work event")
	private String name;

	@Schema(description = "Unique ID of this game", example = "1234-...-AC79")
	private String uuid;

	@Schema(description = "Pin to allow sign up for this game", example = "123456")
	private int pin;
	
	@Schema(description = "Game state", example = "READY")
	private GameState state;
	
	@Schema(description = "The list of players signed up for the game", example = "[...]")
	private Set<PlayerGetDTO> players;
	
	@Schema(description = "The current rounds in the game", example = "[...]")
	private RoundGetDTO round;
	
	
	public GameGetDTO(GameEntity game) {
		name = game.getName();
		uuid = game.getId().toString();
		pin = game.getPin();
		state = game.getState();
		
		players = game.getPlayers().stream()
				.map(player -> new PlayerGetDTO(game, player))
				.collect(Collectors.toSet());
		
		round = game.getRounds().stream()
				.filter(round -> round.isActive())
				.map(round -> new RoundGetDTO(round))
				.findFirst()
				.orElse(null);
	}
}
