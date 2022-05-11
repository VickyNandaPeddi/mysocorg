package com.good.platform.response;

import java.util.List;

import com.good.platform.enums.InviteStatus;
import com.good.platform.model.ProjectModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCommonResponse {

	String userId;
	String firstName;
	String lastName;
	List<String> organisations;
	List<ProjectModel> projects;
	String email;
	String userRole;
	Boolean detailsUpdate;
	InviteStatus inviteStatus;

}
