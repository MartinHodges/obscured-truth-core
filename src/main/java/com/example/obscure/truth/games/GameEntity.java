package com.example.obscure.truth.games;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.obscure.truth.player.PlayerEntity;
import com.example.obscure.truth.rounds.RoundEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GAMES")
@Getter
@Setter
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "PIN", nullable = false)
    private int pin = 0;
	
    @Column(name = "STATE", nullable = false)
    private GameState state = GameState.WAITING;
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlayerEntity> players = new HashSet<>();
    
    public void addPlayer(PlayerEntity player) {
    	players.add(player);
    	player.setGame(this);
    }
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoundEntity> rounds = new HashSet<>();
    
    public void addRound(RoundEntity round) {
    	rounds.add(round);
    	round.setGame(this);
    }
}
