package com.example.obscure.truth.facts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Allows players to be describe a true (or false) fact about themselves")

public class FactPostDTO {

	@Schema(description = "Which fact is this - [1-3]", example = "1")
	private int sequence;

	@Schema(description = "A few words to describe the fact about the player", example = "I love icecream")
	private String fact;

	@Schema(description = "An indication as to whther it is the truth", example = "true")
	private boolean truth;
}
