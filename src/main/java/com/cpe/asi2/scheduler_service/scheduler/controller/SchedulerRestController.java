package com.cpe.asi2.scheduler_service.scheduler.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpe.asi2.scheduler_service.scheduler.service.SchedulerService;

import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;

@RestController
public class SchedulerRestController {
	private final SchedulerService schedulerService;

	public SchedulerRestController (SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/schImage")
	public String putImage (@RequestBody String image_url) {
		return schedulerService.putImage(image_url);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/schDescription")
	public String putDescription(@RequestBody String description) {
		return schedulerService.putDescription(description);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/schProperties")
	public String putProperties(@RequestBody String properties) {
		return schedulerService.putProperties(properties);
	}
	
}


//TODO : Gérer la génération unique d'IDs

// Pas de DTO car ca permet de transférer des objets à des gens or moi je popule juste une BDD

