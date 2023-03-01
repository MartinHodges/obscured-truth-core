package com.example.obscure.truth.games;

import com.example.obscure.truth.player.PlayerPostDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Allows games to be created and updated")

public class GamePostDTO {

	@Schema(description = "Team name of this game", example = "Work event")
	private String name;

	@Schema(description = "Player registering the game", example = "{...}")
	private PlayerPostDTO player;
	
	public GameEntity toEntity() {
		GameEntity game = new GameEntity();
		game.setName(name.trim());
		return game;
	}
}
