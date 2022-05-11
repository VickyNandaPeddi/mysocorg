package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.good.platform.entity.Sectors;
import com.good.platform.mapper.SectorsMapper;
import com.good.platform.repository.SectorsRepository;
import com.good.platform.request.dto.SectorsRequestDTO;
import com.good.platform.response.SectorsResponseDTO;
import com.good.platform.service.SectorsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SectorsServiceImpl implements SectorsService {

	private final SectorsRepository sectorsRepo;
	private final SectorsMapper sectorsMapper;

	@Override
	public List<SectorsResponseDTO> setSectors(List<SectorsRequestDTO> sectorsRequest) {
		List<SectorsResponseDTO> response = new ArrayList<>();
		for(SectorsRequestDTO request :sectorsRequest) {
		Sectors entity = sectorsMapper.toSectors(request);
		entity = sectorsRepo.save(entity);
		response.add(sectorsMapper.toSectorsResponse(entity));
		}
		return response;
	}

	@Override
	public List<SectorsResponseDTO> getSectors() {
		List<Sectors> languageList = sectorsRepo.findAll();
		List<SectorsResponseDTO> response = sectorsMapper.toListSectorsResponse(languageList);

		return response;
	}

}
