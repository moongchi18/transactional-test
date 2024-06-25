package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Operation {
	@Id
	@GeneratedValue
	private Long id;
	private String operation;

	public Operation(String operation) {
		this.operation = operation;
	}
	
	public static Operation of(String operation) {
		return new Operation(operation);
	}
}