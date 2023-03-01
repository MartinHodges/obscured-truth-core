package com.example.obscure.truth.rounds;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.obscure.truth.deductions.DeductionPostDTO;
import com.example.obscure.truth.facts.FactPostDTO;
import com.example.obscure.truth.games.GameGetDTO;
import com.example.obscure.truth.results.ResultsGetDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@Transactional
@RequestMapping(path = "/api/v1/games",  produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Rounds", description = "Operations that manage rounds")
public class RoundController {

	private final RoundsService roundsService;
	
	@Operation(summary = "Start a new round")
	@PostMapping("/{gameId}/rounds/suspects/{suspectId}")

	public ResponseEntity<GameGetDTO> startRound(
			@Parameter(description = "The game UUID", required = true) @PathVariable("gameId") String gameId,
			@Parameter(description = "The suspect player UUID", required = true) @PathVariable("suspectId") String suspectId
			) {

		log.info("Start round for game {} with suspect {}", gameId, suspectId);
		
		GameGetDTO response = roundsService.start(gameId, suspectId);
		
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Set facts for round")
	@PostMapping("/{gameId}/rounds")

	public ResponseEntity<GameGetDTO> saveFacts(
			@Parameter(description = "The game UUID", required = true) @PathVariable("gameId") String gameId,
			@Parameter(description = "Details of the round", required = false) @RequestBody Set<FactPostDTO> factsDto
			) {

		log.info("Save facts for game {}", gameId);
		
		GameGetDTO response = roundsService.saveFacts(gameId, factsDto);
		
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Enter deduction")
	@PostMapping("/{gameId}/rounds/players/{playerId}")

	public ResponseEntity<GameGetDTO> enterDeduction(
			@Parameter(description = "The game UUID", required = true) @PathVariable("gameId") String gameId,
			@Parameter(description = "The player UUID", required = true) @PathVariable("playerId") String playerId,
			@Parameter(description = "Details of the deduction", required = true) @RequestBody Set<DeductionPostDTO> deductionsDto
			) {

		log.info("Deduction for game {} by player {}", gameId, playerId);
		
		GameGetDTO response = roundsService.enterDeduction(gameId, playerId, deductionsDto);
		
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Fetch results of round")
	@GetMapping("/{gameId}/rounds/results")

	public ResponseEntity<ResultsGetDTO> fetchResults(
			@Parameter(description = "The game UUID", required = true) @PathVariable("gameId") String gameId
			) {

		log.info("Fetch results for game {}", gameId);
		
		ResultsGetDTO response = roundsService.getResults(gameId);
		
		return ResponseEntity.ok(response);
	}
}
