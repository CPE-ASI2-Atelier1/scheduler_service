package com.cpe.asi2.scheduler_service.schedulerService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cpe.asi2.scheduler_service.schedulerController.CardProperties;
import com.cpe.asi2.scheduler_service.schedulerRepository.SchedulerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cpe.asi2.atelier1.dto.PublicCardDTO;

@Service
public class SchedulerService {
	
	private final SchedulerRepository schedulerRepository;
	private final WebClient.Builder webClientBuilder;
	
	public SchedulerService (SchedulerRepository schedulerRepository, WebClient.Builder webClientBuilder) {
		this.schedulerRepository = schedulerRepository;
		this.webClientBuilder = webClientBuilder;
	}
	
//	public Boolean verifyIfCardCompleted (Integer id) {
//		return schedulerRepository.verifyIfCompleted(id);
//	}
//	
//	public String putImage(String image_url, Integer id) {
//		schedulerRepository.updateImage(image_url, id);
//		System.out.println("The DB population request has been sent for the image");
//		return "Ok";
//	}
//	
//	public String putDescription(String description, Integer id) {
//		schedulerRepository.updateDescription(description, id);
//		System.out.println("The DB population request has been sent for the description");
//		return "Ok";
//	}
//	
//	public String putProperties(CardProperties properties, Integer id) {
//		schedulerRepository.updateProperties(properties, id); //TODO : faire le nécessaire pour les mettre dans la BDD
//		System.out.println("The DB population request has been sent for the description");
//		
//		if (verifyIfCardCompleted(id)) {
//			
//			//TODO: Faire la requête pour créer la carte
//			final PublicCardDTO card = new PublicCardDTO(id,
//											properties.getEnergy(),
//											properties.getHp(),
//											properties.getDefence(),
//											properties.getAttack(),
//											properties.getPrice(),
//											schedulerRepository.getUserId(id),
//											schedulerRepository.getName(id),
//											schedulerRepository.getDescription(id),
//											schedulerRepository.getFamily(id),
//											schedulerRepository.getAffinity(id),
//											schedulerRepository.getImageUrl(id),
//											schedulerRepository.getSmallImageUrl(id)
//											);
//			
//			postCard(card);
//			System.out.println("The card is completed and a request has been sent to add it into the inventory");
//			
//			deleteCard(id);
//			System.out.println("The card has been removed from the incompleted cards");
//		}
//		
//		return "Ok";
//	}
	
	public String postCard(PublicCardDTO requestBody) {
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

	public List<PublicCardDTO> getWIPCards() {
		// TODO To implement
		List<PublicCardDTO> wipCards; 
		
		
		
		
		return wipCards;
	}
	
	public PublicCardDTO getWipCardById(String id) {
		// TODO To implement
		PublicCardDTO card;
		
		
		
		return card;
	}

	public Integer generateProps(PublicCardDTO card) {
		// TODO To implement
		Integer cardId;
		
		
		

		return cardId;
	}

	public void receiveProperties(CardProperties properties) {
		// TODO To implement
		
		
		
	}

	public void updateWIP(PublicCardDTO card) {
		// TODO To implement
		
		
		
		
	}
	
	//TODO: Envoyer une requête à raph pour générer les propriétés:
	public String askForProperties (String imgUrl, Integer id) {
		//TODO: Mettre la bonne URL quand je la connaîtrai
		String url = "";
		
		String response = webClientBuilder.baseUrl("{" + url + "," + id.toString() + "}")
				.build()
				.post()
				.uri("/") // TODO: Mettre la bonne URL quand je la connaitrai
				.bodyValue(imgUrl)
				.retrieve()
		        .bodyToMono(String.class)
		        .block();
		
		return response;
	}

}
