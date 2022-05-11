package com.good.platform.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountResponse {
	private Long totalOrganisations;
	private Long totalProjects;
	private Long totalAgents;

}
