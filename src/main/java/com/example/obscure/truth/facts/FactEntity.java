package com.example.obscure.truth.facts;

import java.util.UUID;

import com.example.obscure.truth.rounds.RoundEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FACTS")
@Getter
@Setter
public class FactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "SEQUENCE", nullable = false)
    private int sequence;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "IS_TRUTH", nullable = false)
    private boolean truth;

    @ManyToOne
    @JoinColumn(name = "ROUND")
    private RoundEntity round;
}
