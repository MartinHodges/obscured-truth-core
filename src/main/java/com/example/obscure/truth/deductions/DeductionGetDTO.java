package com.example.obscure.truth.deductions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Provides information about a deduction")

public class DeductionGetDTO {

	@Schema(description = "UUID of this player", example = "1234-...-ASDF")
	private String uuid;

	@Schema(description = "Sequence number for fact", example = "2")
	private int sequence;
	
	@Schema(description = "Deduction - was it the truth?", example = "true")
	private boolean truth;
	
	public DeductionGetDTO(DeductionEntity deduction) {
		uuid = deduction.getId().toString();
		truth = deduction.isTruth();
		sequence = deduction.getSequence();
	}
}
