package com.good.platform.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.entity.ProgramTimelines;
import com.good.platform.request.dto.FinancialYearsRequestDto;
import com.good.platform.request.dto.SectorsRequestDTO;
import com.good.platform.response.ProgramTimelineResponse;
import com.good.platform.response.SectorsResponseDTO;
import com.good.platform.response.dto.CountriesResponseDto;
import com.good.platform.response.dto.FinancialYearsResponseDto;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.OrgPercentageCompletionResponse;
import com.good.platform.service.CountriesService;
import com.good.platform.service.FinancialYearsService;
import com.good.platform.service.OrganisationService;
import com.good.platform.service.ProgramTimelineService;
import com.good.platform.service.SectorsService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MiscDataController is used to get the drop down data
 * Data:
 * 1) Country list
 *
 * @author Mohamedsuhail S
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class MiscDataController {

    private final CountriesService countriesDataListService;
    private final FinancialYearsService financialYearsService;
    private final OrganisationService organisationService;
    private final SectorsService sectorsService;
    private final ProgramTimelineService programTimelineService;

    /**
     * getCountries is to get the list of countries.
     * (Countries list as provided by Java library)
     *
     * @return List<String> - list of countries data
     */
    @GetMapping("/countries")
    public GoodPlatformResponseVO<CountriesResponseDto> getCountries(
            @RequestHeader("Authorization") String token,
            @RequestHeader(name = "Access", required = false) String accessInfo) {
        log.debug("Request to get the countries list ");
        CountriesResponseDto countriesResponseDto = countriesDataListService.getCountriesList();
        return ResponseHelper.createResponse(new GoodPlatformResponseVO<CountriesResponseDto>(), countriesResponseDto,
                Constants.COUNTRIES_FETCH_SUCCESS, Constants.COUNTRIES_FETCH_FAIL);
    }

    @GetMapping("/financial-years")
    public GoodPlatformResponseVO<FinancialYearsResponseDto> getFinancialYears(
            @RequestHeader("Authorization") String token,
            @RequestHeader(name = "Access", required = false) String accessInfo) {
        log.debug("Request to get the financial years list ");
        FinancialYearsResponseDto financialYearsResponseDto = financialYearsService.getFinancialYears();
        return ResponseHelper.createResponse(new GoodPlatformResponseVO<CountriesResponseDto>(), financialYearsResponseDto,
                Constants.FINANCIAL_YEARS_FETCH_SUCCESS, Constants.FINANCIAL_YEARS_FETCH_FAIL);
    }

    @PostMapping("/financial-years")
    public GoodPlatformResponseVO<FinancialYearsResponseDto> addFinancialYears(
            @RequestHeader("Authorization") String token,
            @RequestHeader(name = "Access", required = false) String accessInfo,
            @RequestBody FinancialYearsRequestDto financialYears) {
        log.debug("Request to add the financial years list ");
        FinancialYearsResponseDto financialYearsResponseDto = financialYearsService.addFinancialYears(financialYears);
        return ResponseHelper.createResponse(new GoodPlatformResponseVO<CountriesResponseDto>(), financialYearsResponseDto,
                Constants.FINANCIAL_YEARS_FETCH_SUCCESS, Constants.FINANCIAL_YEARS_FETCH_FAIL);
    }

    @GetMapping("/percentage")
    public GoodPlatformResponseVO<List<Map<String, Long>>> getPercentage(
            //@RequestHeader("Authorization") String token,
            //@RequestHeader(name = "Access", required = false) String accessInfo,
            @RequestParam String organisationId) {
        log.debug("Request to get percentage ");
        OrgPercentageCompletionResponse percentages = organisationService.getPercentage(organisationId);
        return ResponseHelper.createResponse(new GoodPlatformResponseVO<CountriesResponseDto>(), percentages,
                Constants.PERCENTAGE_CALC_SUCCESS, Constants.PERCENTAGE_CALC_FAIL);
    }

    @PostMapping("sectors")
    public GoodPlatformResponseVO<List<SectorsResponseDTO>> setLanguages(
            @RequestBody List<SectorsRequestDTO> languageRequest) {
        List<SectorsResponseDTO> response = sectorsService.setSectors(languageRequest);
        return ResponseHelper.createResponse(new GoodPlatformResponseVO(), response, Constants.SECTORS_ADD_SUCCESS,
                Constants.SECTORS_ADD_FAIL);

    }

    @GetMapping("sectors")
    public GoodPlatformResponseVO<List<SectorsResponseDTO>> getLanguages() {
        List<SectorsResponseDTO> response = sectorsService.getSectors();
        return ResponseHelper.createResponse(new GoodPlatformResponseVO(), response, Constants.SECTORS_FETCH_SUCCESS,
                Constants.SECTORS_FETCH_FAIL);

    }


    @GetMapping("/timeStamp")
    public GoodPlatformResponseVO getServerTimeStamp() {

        //LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return ResponseHelper.createResponse(new GoodPlatformResponseVO(), LocalDateTime.now(
                ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                Constants.TIMESTAMP_FETCH_SUCCESS, Constants.TIMESTAMP_FETCH_FAIL);
    }


    @GetMapping("/program-timelines")
    public ProgramTimelineResponse getProgramTimelines() {
        log.debug("Program Timelines Controller");
        return programTimelineService.getProgramTimelines();
    }

    @PostMapping("/program-timelines")
    public ProgramTimelineResponse saveProgramTimelines(@RequestBody ProgramTimelines programTimelines) {
        log.debug("Program Timelines Controller -saveProgramTimeline" + programTimelines);
        return programTimelineService.addProgramTimeline(programTimelines);
    }

}
