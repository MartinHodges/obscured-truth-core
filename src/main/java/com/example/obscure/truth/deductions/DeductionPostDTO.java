package com.example.obscure.truth.deductions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Allows players to be created for a game")

public class DeductionPostDTO {

	@Schema(description = "The sequence number for the fact [1-3]", example = "1")
	private int factSequence;

	@Schema(description = "Was it deduced that the fact was the truth?", example = "true")
	private boolean truth;
}
