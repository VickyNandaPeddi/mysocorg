package com.good.platform.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DropdownDataModel is used to make the dropdown data model
 * @author Mohamedsuhail S
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropdownDataModel {
	
	private String id;
	private String value;

}
