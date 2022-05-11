package com.good.platform.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgPercentageCompletionResponse {

	private long overview;
	private long leadership;
	private long location;
	private long purpose;
	private long trackRecord;
	private long documents;
}
