package com.cpe.asi2.scheduler_service.schedulerController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		
		if (schedulerService.verifyIfCardCompleted(id)) {
			
			//TODO: Faire la requête pour créer la carte
			
			// J'aimerais bien MAIS il n'y a pas de description et d'image dans le card DTO...
			// Idée --> Faire une completeCardDTO qui étend CardDTO et qui ajoute ces deux attributs
			// MAIS ça veut dire qu'on doit bouger la BDD d'origine et que les cartes legacy poseront problème ...
			
			System.out.println("The card is completed and has been added to the inventory");
			
			schedulerService.deleteCard(id);
			System.out.println("The card has been removed from the incompleted cards");
		}
	}
	
}

//TODO : Gérer la génération unique d'IDs

// Pas de DTO car ca permet de transférer des objets à des gens or moi je popule juste une BDD

