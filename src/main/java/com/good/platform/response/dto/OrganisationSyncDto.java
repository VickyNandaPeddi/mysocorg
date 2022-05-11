package com.good.platform.response.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import com.good.platform.entity.OrganisationAddress;
import com.good.platform.entity.OrganisationBeneficiaryHistory;
import com.good.platform.entity.OrganisationBudgetHistory;
import com.good.platform.entity.OrganisationDonorHistory;
import com.good.platform.entity.OrganisationFinancialDetails;
import com.good.platform.entity.OrganisationMembers;
import com.good.platform.entity.OrganisationMissionStatements;
import com.good.platform.entity.OrganisationSector;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationSyncDto extends BaseResponse{
	
	private String name;
	private String type;
	private String yearFounded;
	private String noOfEmployees;
	private String emailId;
	private String website;
	private String about;
	private String headquarterCountry;
	private String creatorMemberId;
	private String fullName;
	private String mobile;
	private String idProof;
	private String gstNumber;
	private String approvalStatus;
	private LocalDateTime syncedAt;

}
