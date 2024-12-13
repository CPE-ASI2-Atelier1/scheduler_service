package com.cpe.asi2.scheduler_service.schedulerController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cpe.asi2.atelier1.dto.PublicCardDTO;
import com.cpe.asi2.scheduler_service.schedulerService.SchedulerService;


//@RequestMapping("/sch")
@RestController
public class SchedulerRestController {
	@Autowired
	SchedulerService schedulerService;
	
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
	@RequestMapping(method=RequestMethod.GET, value="/WipCard/{id}")
	public PublicCardDTO GetWipCardById(@PathVariable Integer id) {
		return schedulerService.getWipCardById(id);
	}
	
	// Front request
	@RequestMapping(method=RequestMethod.POST, value="/Props")
	public Integer GenerateProps(@RequestBody PublicCardDTO card) {
		return schedulerService.generateProps(card);
	}
	
	// Properties generation service request
	@RequestMapping(method=RequestMethod.POST, value="/receiveProperties")
	public void ReceiveProperties(@RequestBody CardProperties properties) { //TODO: Attention je ne get pas un DTO direect mais un json faut que je fasse un Map<String, String> (voir ce que raph a fait)
		schedulerService.receiveProperties(properties);
	}
	
	// Front request
	@RequestMapping(method=RequestMethod.POST, value="/updateWIP")
	public Integer UpdateWIP(@RequestBody PublicCardDTO card) {
		return schedulerService.updateWIP(card);
	}
}

// Pas de DTO car ca permet de transférer des objets à des gens or moi je popule juste une BDD

