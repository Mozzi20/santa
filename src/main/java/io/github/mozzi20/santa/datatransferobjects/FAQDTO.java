package io.github.mozzi20.santa.datatransferobjects;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAQDTO {

	@Length(min=5, message="Frågan måste vara minst fem bokstäver")
	@Length(max=50, message="Frågan är för lång")
	private String question;
	
}
