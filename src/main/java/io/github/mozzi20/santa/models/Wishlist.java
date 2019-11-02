package io.github.mozzi20.santa.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity	
public class Wishlist {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User santa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Event event;
	
	@ElementCollection
	@CollectionTable(
			name = "answers", 
			joinColumns = @JoinColumn(name="wishlist_id")
	)
	@MapKeyJoinColumn(name = "question_id")
	@Column(name = "answer")
	private Map<Question, String> answers = new HashMap<Question, String>();

	@CreationTimestamp
	private Date createdDate;
	
}
