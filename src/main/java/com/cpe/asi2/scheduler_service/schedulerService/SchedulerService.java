package com.cpe.asi2.scheduler_service.schedulerService;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.cpe.asi2.scheduler_service.schedulerRepository.SchedulerRepository;
import com.cpe.asi2.atelier1.dto.CardDTO;

@Service
public class SchedulerService {
	
	private final SchedulerRepository schedulerRepository;
	private final WebClient.Builder webClientBuilder;
	
	public SchedulerService (SchedulerRepository schedulerRepository, WebClient.Builder webClientBuilder) {
		this.schedulerRepository = schedulerRepository;
		this.webClientBuilder = webClientBuilder;
	}
	
	public Boolean verifyIfCardCompleted (Integer id) {
		return schedulerRepository.verifyIfCompleted(id);
	}
	
	public String putImage(String image_url, Integer id) {
		schedulerRepository.updateImage(image_url, id);
		System.out.println("The DB population request has been sent for the image");
		return "Ok";
	}
	
	public String putDescription(String description, Integer id) {
		schedulerRepository.updateDescription(description, id);
		System.out.println("The DB population request has been sent for the description");
		return "Ok";
	}
	
	public String putProperties(String properties, Integer id) {
		schedulerRepository.updateProperties(properties, id);
		System.out.println("The DB population request has been sent for the description");
		
		if (verifyIfCardCompleted(id)) {
			
			//TODO: Faire la requête pour créer la carte
			
			final CardDTO card = new CardDTO(); //TODO: Mettre les bons paramètres
			
			postCard(card);
			
			System.out.println("The card is completed and a request has been sent to add it into the inventory");
			
			deleteCard(id);
			System.out.println("The card has been removed from the incompleted cards");
		}
		
		return "Ok";
	}
	
	public String postCard(CardDTO requestBody) {
		//TODO: Mettre la bonne URL quand je la connaîtrai
		String url = "";
		
		String response = webClientBuilder.baseUrl(url)
				.build()
				.post()
				.uri("/createCard") // De tête, à vérifier
				.bodyValue(requestBody)
				.retrieve()
		        .bodyToMono(String.class)
		        .block();
		
		return response;
	}

	public void deleteCard(Integer id) {
		schedulerRepository.deleteById(id);
		System.out.println("The DB deletion request has been sent for the card with id" + id);
	}

}
