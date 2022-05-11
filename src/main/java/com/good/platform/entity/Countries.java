package com.good.platform.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
@Entity(name = "countries")
@Deprecated
public class Countries extends DocumentId{
	
	private String name; 

}
