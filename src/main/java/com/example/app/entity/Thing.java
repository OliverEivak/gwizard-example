package com.example.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some random JPA entity
 */
@Entity
@Data
@NoArgsConstructor
public class Thing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** */
	private String name;

	/** */
	public Thing(String name) {
		this.name = name;
	}
}
