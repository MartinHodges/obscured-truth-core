package com.example.obscure.truth.player;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<PlayerEntity, UUID> {

	Optional<PlayerEntity> findById(UUID uuid);
	
}
