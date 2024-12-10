package com.cpe.asi2.scheduler_service.common.tools;

import com.cpe.asi2.atelier1.dto.PublicCardDTO;
import com.cpe.asi2.scheduler_service.schedulerEntity.SchedulerEntity;

public class SchedulerMapper {

	
	public static PublicCardDTO fromSchedulerEntityToPublicCardDto(SchedulerEntity cE) {
		PublicCardDTO cDto = new PublicCardDTO();
		cDto.setAffinity(cE.getAffinity());
		cDto.setAttack(cE.getAttack());
		cDto.setDefence(cE.getDefence());
		cDto.setDescription(cE.getDescription());
		cDto.setEnergy(cE.getEnergy());
		cDto.setFamily(cE.getFamily());
		cDto.setHp(cE.getHp());
		cDto.setId(cE.getId());
		cDto.setImgUrl(cE.getImageUrl());
		cDto.setName(cE.getName());
		cDto.setPrice(cE.getPrice());
		cDto.setSmallImgUrl(cE.getSmallImageUrl());
		cDto.setUserId(cE.getUserId());
		return cDto;
	}
}
