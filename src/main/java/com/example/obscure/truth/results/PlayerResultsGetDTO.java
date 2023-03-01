package com.example.obscure.truth.results;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.obscure.truth.deductions.DeductionGetDTO;
import com.example.obscure.truth.player.PlayerEntity;
import com.example.obscure.truth.rounds.RoundEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Provides information about the results of a round for a player")

public class PlayerResultsGetDTO {

	@Schema(description = "The unique Id for the player", example = "1234-...-ASDF")
	private String playerId;
	
	@Schema(description = "Name of Player", example = "Bob")
	private String playerName;
	
	@Schema(description = "Player was first", example = "true")
	private boolean wasFirst;
	
	@Schema(description = "The deductions by the player", example = "[...]")
	private Set<DeductionGetDTO> deductions;

	public PlayerResultsGetDTO(RoundEntity round, PlayerEntity player) {
		playerId = player.getId().toString();
		playerName = player.getName();
		wasFirst = Objects.equals(round.getFirst(), player);
		deductions = round.getDeductions().stream()
				.filter(deduction -> Objects.equals(player.getId(), deduction.getPlayer().getId()))
				.map(deduction -> new DeductionGetDTO(deduction))
				.collect(Collectors.toSet());
	}
}
