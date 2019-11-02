package io.github.mozzi20.santa.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	@OneToMany(mappedBy="event", fetch=FetchType.LAZY)
	private Set<Wishlist> wishlists;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@OrderBy("id ASC")
	private Set<Question> questions;
		
	private Date startDate;
	
	private Date wishlistDeadlineDate;
	
	private Date endDate;
	
	@CreationTimestamp
	private Date createdDate;
}
