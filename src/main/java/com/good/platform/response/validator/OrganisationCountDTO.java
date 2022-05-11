package com.good.platform.response.validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationCountDTO {
	private Long reviewCount ;
	private Long approvedCount ;
	private Long rejectedCount ;
}
