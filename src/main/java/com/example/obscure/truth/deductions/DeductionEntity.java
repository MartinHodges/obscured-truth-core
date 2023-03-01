package com.example.obscure.truth.deductions;

import java.util.UUID;

import com.example.obscure.truth.facts.FactEntity;
import com.example.obscure.truth.player.PlayerEntity;
import com.example.obscure.truth.rounds.RoundEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DEDUCTIONS")
@Getter
@Setter
public class DeductionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "PLAYER", nullable = false)
    private PlayerEntity player;

    @Column(name = "TRUTH")
    private boolean truth;
    
    @Column(name = "SEQUENCE")
    private int sequence = 0;
    
    @ManyToOne
    @JoinColumn(name = "FACT")
    private FactEntity fact;
    
    @ManyToOne
    @JoinColumn(name = "ROUND", nullable = false)
    private RoundEntity round;
}
