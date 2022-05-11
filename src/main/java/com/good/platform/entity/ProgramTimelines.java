package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "program_timelines")
public class ProgramTimelines extends BaseId{
	@Column(name = "timeline")	
	private String timeline;
	@Column(name = "sort_order")
	private int sortorder;

}
