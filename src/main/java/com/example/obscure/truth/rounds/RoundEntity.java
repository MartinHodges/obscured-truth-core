package com.example.obscure.truth.rounds;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.obscure.truth.deductions.DeductionEntity;
import com.example.obscure.truth.facts.FactEntity;
import com.example.obscure.truth.games.GameEntity;
import com.example.obscure.truth.player.PlayerEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ROUNDS")
@Getter
@Setter
public class RoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "SUSPECT", nullable = false)
    private PlayerEntity suspect;

    @ManyToOne
    @JoinColumn(name = "FIRST", nullable = true)
    private PlayerEntity first;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FactEntity> facts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DeductionEntity> deductions = new HashSet<>();

    public void addDeduction(DeductionEntity deduction) {
    	deductions.add(deduction);
    	deduction.setRound(this);
    }

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = false;

    public void addFact(FactEntity fact) {
    	facts.add(fact);
    	fact.setRound(this);
    }
    
    @ManyToOne
    @JoinColumn(name = "GAME")
    private GameEntity game;
}
