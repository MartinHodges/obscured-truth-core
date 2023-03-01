package com.example.obscure.truth.games;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface GamesRepository extends CrudRepository<GameEntity, UUID>{

	Optional<GameEntity> findByPin(int pin);
	
}
