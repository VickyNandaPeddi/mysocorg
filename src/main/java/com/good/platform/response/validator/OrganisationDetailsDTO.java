package com.good.platform.response.validator;

import java.time.LocalDate;

import com.good.platform.projection.OrganisationDetailsProjection;
import com.good.platform.utility.DateTimeUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrganisationDetailsDTO {
	
	private String id;
	
	private String name; // search and filter
	
	private String approvalStatus; //filter
	
	private String approvalStatusComment;
	
	private String createdBy;
	
	private LocalDate createdOn; // filter

	public OrganisationDetailsDTO(String id, String name, String approvalStatus, String approvalStatusComment,
			String createdBy, Long createdOn) {
		this.id = id;
		this.name = name;
		this.approvalStatus = approvalStatus;
		this.approvalStatusComment = approvalStatusComment;
		this.createdBy = createdBy;
		this.createdOn = DateTimeUtils.convertUnixToLocalDate(createdOn);
	}
	
	public OrganisationDetailsDTO(OrganisationDetailsProjection projection) {
		this.id = projection.getId();
		this.name = projection.getName();
		this.approvalStatus = projection.getApprovalStatus();
		this.approvalStatusComment = projection.getApprovalStatusComment();
		this.createdBy = projection.getCreatedBy();
		this.createdOn = DateTimeUtils.convertUnixToLocalDate(projection.getCreatedOn());
	}
}
