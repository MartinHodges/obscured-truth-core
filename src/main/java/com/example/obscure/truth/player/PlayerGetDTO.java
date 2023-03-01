package com.example.obscure.truth.player;

import com.example.obscure.truth.games.GameEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Provides information about a game and its participants")

public class PlayerGetDTO {

	@Schema(description = "Unique ID of this game", example = "1234-ASDF")
	private String gameId;

	@Schema(description = "Unique ID of this player", example = "1234-ASDF")
	private String playerId;

	@Schema(description = "Name of this player", example = "Bobby")
	private String name;

	@Schema(description = "The total score for the player", example = "1234")
	private int score;
	
	@Schema(description = "The total number of times the player has been the suspect", example = "1")
	private int asSuspect;
	
	public PlayerGetDTO(GameEntity gameEntity, PlayerEntity player) {
		gameId = gameEntity.getId().toString();
		playerId = player.getId().toString();
		name = player.getName();
		score = player.getScore();
		asSuspect = player.getAsSuspect();
	}
}
