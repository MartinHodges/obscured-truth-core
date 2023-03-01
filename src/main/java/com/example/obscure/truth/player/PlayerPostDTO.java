package com.example.obscure.truth.player;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Allows players to be created for a game")

public class PlayerPostDTO {

	@Schema(description = "Name of this player", example = "Bobby")
	private String name;
	
	public PlayerEntity toEntity() {
		
		PlayerEntity player = new PlayerEntity();
		player.setName(name.trim());
		
		return player;
	}
}
