package com.good.platform.service;

import java.util.List;

import com.good.platform.request.dto.SectorsRequestDTO;
import com.good.platform.response.SectorsResponseDTO;



public interface SectorsService {
	public List<SectorsResponseDTO> setSectors(List<SectorsRequestDTO> sectorsRequest) ;
	public List<SectorsResponseDTO> getSectors();
}
