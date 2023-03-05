package com.example.obscure.truth.rounds;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.obscure.truth.deductions.DeductionEntity;
import com.example.obscure.truth.deductions.DeductionPostDTO;
import com.example.obscure.truth.exceptions.BadStateException;
import com.example.obscure.truth.exceptions.NotFoundException;
import com.example.obscure.truth.facts.FactEntity;
import com.example.obscure.truth.facts.FactPostDTO;
import com.example.obscure.truth.games.GameEntity;
import com.example.obscure.truth.games.GameGetDTO;
import com.example.obscure.truth.games.GameState;
import com.example.obscure.truth.games.GamesService;
import com.example.obscure.truth.player.PlayerEntity;
import com.example.obscure.truth.player.PlayerService;
import com.example.obscure.truth.results.ResultsGetDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoundsService {

	private final GamesService gamesService;
	private final PlayerService playerService;
	
	// TODO
	
	public GameGetDTO start(String gamesId, String suspectId) {
		
		GameEntity game = gamesService.getEntityByUUID(gamesId);

		PlayerEntity suspect = playerService.getPlayerEntityByUuid(suspectId); 
		suspect.setAsSuspect(suspect.getAsSuspect() + 1);
		playerService.updateEntity(suspect);
		
		RoundEntity round = new RoundEntity();
		round.setSuspect(suspect);
		round.setStart(null);
		round.setGame(game);

		game.setRound(round);
		game.setState(GameState.SUSPECT_ASSIGNED);

		game = gamesService.updateEntity(game);
		
		return new GameGetDTO(game);
	}

	
	public GameGetDTO saveFacts(String gamesId, Set<FactPostDTO> factsDto) {
		GameEntity game = gamesService.getEntityByUUID(gamesId);

		RoundEntity round = game.getRound();
		if (round == null) {
			throw new NotFoundException("Round not found");
		}
		
		factsDto.forEach(factDto -> {
			FactEntity factEntity = new FactEntity();
			factEntity.setSequence(factDto.getSequence());
			factEntity.setDescription(factDto.getFact());
			factEntity.setTruth(factDto.isTruth());
			round.addFact(factEntity);
		});
		
		round.setStart(LocalDateTime.now());

		game.setState(GameState.FACTS_PROVIDED);
		
		game = gamesService.updateEntity(game);
		
		return new GameGetDTO(game);
	}

	
	public GameGetDTO enterDeduction(String gamesId, String playerId, Set<DeductionPostDTO> deductionsDto) {
		
		GameEntity game = gamesService.getEntityByUUID(gamesId);

		PlayerEntity player = playerService.getPlayerEntityByUuid(playerId);
		
		RoundEntity currentRound = game.getRound();
		if (currentRound == null) {
			throw new NotFoundException("Round not found");
		}

		if (Objects.equals(player, currentRound.getSuspect())) {
			return new GameGetDTO(game); // stop cheating!
		}
		
		final boolean isFirst;
		
		if (currentRound.getFirst() == null) {
			currentRound.setFirst(player);
			isFirst = true;
		} else {
			isFirst = false;
		}
		
		int numberDeductions = (currentRound.getDeductions().size() / 3) + 1;
		
		deductionsDto.forEach((deductionDto) -> {
			DeductionEntity deduction = new DeductionEntity();
			currentRound.addDeduction(deduction);
			deduction.setPlayer(player);
			deduction.setTruth(deductionDto.isTruth());
			FactEntity factEntity = currentRound.getFacts().stream()
					.filter(fact -> fact.getSequence() == deductionDto.getFactSequence())
					.findAny()
					.orElseThrow(() -> new BadStateException("Matching fact not found"));
			deduction.setFact(factEntity);
			deduction.setSequence(deductionDto.getFactSequence());
		});

		if (numberDeductions >= game.getPlayers().size() - 1) {
			game.setState(GameState.DEDUCTIONS_PROVIDED);
			PlayerEntity suspect = currentRound.getSuspect();

			// collect scores
			game.getPlayers().forEach(gamePlayer -> {
				int score = isFirst ? 20 : 10;
				log.info("{} deduced and was {} scoring {} an answer", gamePlayer.getName(), isFirst ? "first" : "not first", score);
				if (!gamePlayer.equals(suspect)) {
					Set<DeductionEntity> playerDeductions = currentRound.getDeductions().stream()
						.filter(deduction -> Objects.equals(gamePlayer.getId(), deduction.getPlayer().getId()))
						.collect(Collectors.toSet());
					for (int s = 1; s <= 3; s++) {
						if (checkDeduction(currentRound.getFacts(), playerDeductions, s)) {
							log.info("{} gets {} for {}", gamePlayer.getName(), score, s);
							gamePlayer.setScore(player.getScore() + score);
						} else {
							log.info("{} gets {} for {}", suspect.getName(), 10, s);
							suspect.setScore(suspect.getScore() + 10);;
						}
					}
					playerService.updateEntity(gamePlayer);
				}
			});
			playerService.updateEntity(suspect);
		}
		
		game = gamesService.updateEntity(game);
		
		return new GameGetDTO(game);
	}

	private boolean checkDeduction(Set<FactEntity> facts, Set<DeductionEntity> deductions, int index) {
		
		facts.stream().forEach(fact ->{
			log.info("Seq: {} fact: {} truth: {}", fact.getSequence(), fact.getDescription(), fact.isTruth());
		});
		deductions.stream().forEach(deduction ->{
			log.info("Seq: {} fact: {} truth: {}", deduction.getSequence(), deduction.isTruth());
		});
		
		boolean factTruth = facts.stream()
				.filter(fact -> fact.getSequence() == index)
				.map(fact -> fact.isTruth())
				.findFirst()
				.orElse(false);

		log.info("{} for fact {} by suspect", factTruth, index);

		boolean deductionTruth = deductions.stream()
				.filter(deduction -> deduction.getSequence() == index)
				.map(deduction -> deduction.isTruth())
				.findFirst()
				.orElse(false);
		
		log.info("{} for fact {} by detective", deductionTruth, index);

		return factTruth == deductionTruth;
	}
	
	public ResultsGetDTO getResults(String gamesId) {
		
		GameEntity game = gamesService.getEntityByUUID(gamesId);

		if (game.getState() != GameState.DEDUCTIONS_PROVIDED && game.getState() != GameState.READY) {
			throw new BadStateException("Not all deductions provided");
		}
		
		RoundEntity currentRound = game.getRound();
		if (currentRound == null) {
				throw new NotFoundException("Round not found");
		}

		game.setState(GameState.READY);
		
		game = gamesService.updateEntity(game);
		
		return new ResultsGetDTO(currentRound);
	}
}
