package com.good.platform.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.good.platform.entity.Sectors;
import com.good.platform.request.dto.SectorsRequestDTO;
import com.good.platform.response.SectorsResponseDTO;

@Mapper(componentModel = "spring")
public interface SectorsMapper {

	Sectors toSectors(SectorsRequestDTO request);

	SectorsResponseDTO toSectorsResponse(Sectors entity);

	List<SectorsResponseDTO> toListSectorsResponse(List<Sectors> entity);

}
