package com.cpe.asi2.scheduler_service.schedulerController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cpe.asi2.atelier1.dto.CardDTO;

import com.cpe.asi2.scheduler_service.schedulerService.SchedulerService;

import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/sch")
public class SchedulerRestController {
	private final SchedulerService schedulerService;

	public SchedulerRestController (SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/Image")
	public void putImage (@RequestBody String image_url, @RequestBody Integer id) {
		schedulerService.putImage(image_url, id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/Description")
	public void putDescription(@RequestBody String description, @RequestBody Integer id) {
		schedulerService.putDescription(description, id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/Properties")
	public void putProperties(@RequestBody String properties, @RequestBody Integer id) {
		schedulerService.putProperties(properties, id);
	}
	
}

//TODO : Gérer la génération unique d'IDs

// Pas de DTO car ca permet de transférer des objets à des gens or moi je popule juste une BDD

