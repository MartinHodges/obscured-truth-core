package com.example.obscure.truth.results;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Allows players to provide facts")

public class ResultPostDTO {

	@Schema(description = "is the fact the truth", example = "true")
	private boolean truth;
}
