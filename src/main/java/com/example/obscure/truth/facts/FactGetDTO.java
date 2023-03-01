package com.example.obscure.truth.facts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Provides information about a game and its participants")

public class FactGetDTO {

	@Schema(description = "Unique Id of this fact", example = "1234-...-ASDF")
	private String uuid;
	
	@Schema(description = "The sequence number for the fact [1-3]", example = "3")
	private int sequence;
	
	@Schema(description = "A few words about the fact to identify it", example = "The one about the sunny day")
	private String description;
	
	public FactGetDTO(FactEntity fact) {
		uuid = fact.getId().toString();
		sequence = fact.getSequence();
		description = fact.getDescription();
	}
}
