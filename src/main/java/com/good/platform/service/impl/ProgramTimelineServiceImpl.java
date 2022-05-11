package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.good.platform.model.ProgramTimelinesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.good.platform.entity.ProgramTimelines;
import com.good.platform.repository.ProgramTimelineRepository;
import com.good.platform.response.ProgramTimelineResponse;
import com.good.platform.service.ProgramTimelineService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProgramTimelineServiceImpl implements ProgramTimelineService {

    @Autowired
    private ProgramTimelineRepository programTimelineRepo;

    @Override
    @Transactional
    public ProgramTimelineResponse getProgramTimelines() {
        log.debug("Fetch ProgramTimeline Details Starts");
        ProgramTimelineResponse programTimelineResponse = new ProgramTimelineResponse();
        List<ProgramTimelinesModel> programTimelinesModelList = new ArrayList<>();
        try {

            List<ProgramTimelines> programTimelines = programTimelineRepo.findallProgramTimelineBySortorder();
            programTimelines.stream().forEach(s -> {
                programTimelinesModelList.add(new ProgramTimelinesModel(s.getId().toString(), s.getTimeline()));
                log.debug("Programtimelineserviceimpl  programstimelinesmodellist  " + programTimelinesModelList);
            });
            log.debug("ProgramTimelinesList {}" + programTimelines);
            programTimelineResponse.setTimelinesModels(programTimelinesModelList);
            log.debug("program timeline repsonse" + programTimelineResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        return programTimelineResponse;
    }

    @Override
    @Transactional
    public ProgramTimelineResponse addProgramTimeline(ProgramTimelines programTimelines) {
        log.debug("Program TImeline Service Add Programtimeline");
        ProgramTimelines savedProgramTImeline = programTimelineRepo.save(programTimelines);
        log.debug("Programtimeserviceimpl  saved " + savedProgramTImeline);
        return new ProgramTimelineResponse("Saved ProgramTimeline Sucessfully");
    }
}
