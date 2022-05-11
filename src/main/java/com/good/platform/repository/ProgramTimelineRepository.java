package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.ProgramTimelines;

import java.util.List;

@Repository
public interface ProgramTimelineRepository extends JpaRepository<ProgramTimelines, String> {

    //    @Query("SELECT pt.id, from ProgramTimelines pt order by  pt.sortorder")
//    List<ProgramTimelines> findallProgramTimelineBySortorder();
    @Query("select  pt  from program_timelines  pt order by  pt.sortorder")
    List<ProgramTimelines> findallProgramTimelineBySortorder();

}
