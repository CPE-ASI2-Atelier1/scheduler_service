package com.cpe.asi2.scheduler_service.schedulerController;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cpe.asi2.atelier1.dto.PublicCardDTO;
import com.cpe.asi2.scheduler_service.schedulerService.SchedulerService;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/sch")
public class SchedulerRestController {
	private final SchedulerService schedulerService;

	public SchedulerRestController (SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
	
//	@RequestMapping(method=RequestMethod.POST, value="/Image")
//	public void putImage (@RequestParam String image_url, @RequestParam Integer id) {
//		schedulerService.putImage(image_url, id);
//	}
//	
//	@RequestMapping(method=RequestMethod.POST,value="/Description")
//	public void putDescription(@RequestParam String description, @RequestParam Integer id) {
//		schedulerService.putDescription(description, id);
//	}
//	
//	@RequestMapping(method=RequestMethod.POST,value="/Properties")
//	public void putProperties(@RequestBody CardProperties properties, @RequestParam Integer id) {
//		schedulerService.putProperties(properties, id);
//	}
	
	// Front request
	@RequestMapping(method=RequestMethod.GET, value="/wipCards")
	public List<PublicCardDTO> GetWIPCards() {
		return schedulerService.getWIPCards();
	}
	
	// Front request
	@RequestMapping(method=RequestMethod.GET, value="/getWipCard/{id}")
	public PublicCardDTO GetWipCardById(@PathVariable Integer id) {
		return schedulerService.getWipCardById(id);
	}
	
	// Front request
	@RequestMapping(method=RequestMethod.POST, value="/generateProps")
	public Integer GenerateProps(PublicCardDTO card) {
		return schedulerService.generateProps(card);
	}
	
	// Properties generation service request
	@RequestMapping(method=RequestMethod.POST, value="/receiveProperties")
	public void ReceiveProperties(CardProperties properties) {
		schedulerService.receiveProperties(properties);
	}
	
	// Front request
	@RequestMapping(method=RequestMethod.POST, value="/updateWIP")
	public Integer UpdateWIP(PublicCardDTO card) {
		return schedulerService.updateWIP(card);
	}
}

// Pas de DTO car ca permet de transférer des objets à des gens or moi je popule juste une BDD

