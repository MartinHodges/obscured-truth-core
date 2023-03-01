package com.example.obscure.truth.results;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.obscure.truth.facts.FactWithTruthGetDTO;
import com.example.obscure.truth.rounds.RoundEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Provides information about the results of a round for a player")

public class ResultsGetDTO {

	@Schema(description = "List of facts with truth indicator", example = "[...]")
	private Set<FactWithTruthGetDTO> facts;
	
	@Schema(description = "List of players", example = "[...]")
	private Set<PlayerResultsGetDTO> players;
	
	public ResultsGetDTO(RoundEntity round) {
		facts = round.getFacts().stream()
				.map(fact -> new FactWithTruthGetDTO(fact))
				.collect(Collectors.toSet());
		players = round.getGame().getPlayers().stream()
				.map(player -> new PlayerResultsGetDTO(round, player))
				.collect(Collectors.toSet());
	}
}
