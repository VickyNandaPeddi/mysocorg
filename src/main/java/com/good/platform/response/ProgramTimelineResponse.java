package com.good.platform.response;

import java.util.List;

import com.good.platform.model.ProgramTimelinesModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramTimelineResponse {

	private List<ProgramTimelinesModel> timelinesModels;

	private String message;

	public ProgramTimelineResponse(String saved_programTimeline_sucessfully) {
	}
}
