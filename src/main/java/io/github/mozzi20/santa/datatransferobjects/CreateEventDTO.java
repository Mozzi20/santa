package io.github.mozzi20.santa.datatransferobjects;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import io.github.mozzi20.santa.validators.CreateEventDTOConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CreateEventDTOConstraint
public class CreateEventDTO {
	
	@NotEmpty(message="Ett namn måste anges")
	private String name;
	
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") // works with chrome
	@NotNull(message="Ett startdatum måste anges")
	private Date startDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	@NotNull(message="Ett datum för önskelist-deadline måste anges")
	private Date wishlistDeadlineDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	@NotNull(message="Ett slutdatum måste anges")
	private Date endDate;

}
