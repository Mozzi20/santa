package io.github.mozzi20.santa.datatransferobjects;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistDTO {
	
	private Map<Integer, String> answers;
	
}
