package com.good.platform.client.project.model;

import java.time.LocalDate;
import java.util.List;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDto extends BaseResponse{

	private String name;
	private String organisationId;
	private String primaryImpact;
	private String secondaryImpact1;
	private String secondaryImpact2;
	private String description;
	private LocalDate durationFrom;
	private LocalDate durationTo;
	private Integer duration;
	private String durationIn;
	private String logoUrl;
	private Integer minAge;
	private Integer maxAge;
	private Integer totalNumberOfBeneficiaries;
	private String problemFacedByTarget;
	private String uniqueProgramApproach;
	private String teamApprovalStatus;
	private String adminApprovalStatus;
	private List<String> typesOfBeneficiaries;
	private List<GeographyModel> geographyModel;
	private List<String> highlights;
	
}
