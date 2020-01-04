package dev.movitz.santa.datatransferobjects;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQuestionDTO {
	
	@NotEmpty(message="Du måste ange en fråga")
	private String question;
	
}
