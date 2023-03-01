package com.example.obscure.truth.games;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.obscure.truth.player.PlayerGetDTO;
import com.example.obscure.truth.player.PlayerPostDTO;

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
@Tag(name = "Games", description = "Operations that manage games")
public class GamesController {

	private final GamesService gamesService;
	
	@Operation(summary = "Register a new game")
	@PostMapping

	public ResponseEntity<PlayerGetDTO> registerGame(
			@Parameter(description = "Details of the game", required = true) @RequestBody GamePostDTO gameDto
			) {

		log.info("Register game {}", gameDto.getName());
		
		PlayerGetDTO response = gamesService.create(gameDto);
		
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Fetch existing game by UUID")
	@GetMapping("/uuid/{id}")

	public ResponseEntity<GameGetDTO> fetchGameByUUID(
			@Parameter(description = "The game UUID", required = true) @PathVariable("id") String uuid
			) {

//		log.info("Fetch game by UUID {}", uuid);
		
		GameGetDTO response = gamesService.getByUUID(uuid);
		
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Fetch existing game by pin")
	@GetMapping("/pin/{pin}")

	public ResponseEntity<GameGetDTO> fetchGameByPin(
			@Parameter(description = "The game pin", required = true) @PathVariable("pin") int pin
			) {

		log.info("Fetch game by pin {}", pin);
		
		GameGetDTO response = gamesService.getByPin(pin);
		
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "Register as player using pin")
	@PostMapping("/{pin}/players")

	public ResponseEntity<PlayerGetDTO> registerPlayer(
			@Parameter(description = "The game pin", required = true) @PathVariable("pin") int pin,
			@Parameter(description = "Details of the player", required = true) @RequestBody PlayerPostDTO playerDto
			) {

		log.info("Register player {}", playerDto.getName());
		
		PlayerGetDTO response = gamesService.registerPlayerByPin(pin, playerDto);
		
		return ResponseEntity.ok(response);
	}
	
	
	@Operation(summary = "Start game")
	@PostMapping("/{uuid}/start")

	public ResponseEntity<GameGetDTO> start(
			@Parameter(description = "The game UUID", required = true) @PathVariable("uuid") String uuid
			) {

		log.info("Start Game {}", uuid);
		
		GameGetDTO response = gamesService.start(uuid);
		
		return ResponseEntity.ok(response);
	}
}
