package com.good.platform.service;

import com.good.platform.entity.ProgramTimelines;
import com.good.platform.response.ProgramTimelineResponse;

public interface ProgramTimelineService {
	ProgramTimelineResponse getProgramTimelines();

	ProgramTimelineResponse addProgramTimeline(ProgramTimelines programTimelines);

}
