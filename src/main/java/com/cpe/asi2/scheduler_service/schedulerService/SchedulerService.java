package com.cpe.asi2.scheduler_service.schedulerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cpe.asi2.scheduler_service.common.tools.SchedulerMapper;
import com.cpe.asi2.scheduler_service.schedulerController.CardProperties;
import com.cpe.asi2.scheduler_service.schedulerEntity.SchedulerEntity;
import com.cpe.asi2.scheduler_service.schedulerRepository.SchedulerRepository;
import com.cpe.asi2.atelier1.dto.PublicCardDTO;

@Service
public class SchedulerService {
	
	private final SchedulerRepository schedulerRepository;
	private final WebClient.Builder webClientBuilder;
	
	public SchedulerService (SchedulerRepository schedulerRepository, WebClient.Builder webClientBuilder, SchedulerMapper schedulerMapper) {
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

	public void deleteCard(Integer id) {
		schedulerRepository.deleteById(id);
		System.out.println("The DB deletion request has been sent for the card with id" + id);
	}

	public List<PublicCardDTO> getWIPCards() {
		List<PublicCardDTO> wipCards = new ArrayList<PublicCardDTO>(); 
		List<SchedulerEntity> cardList = new ArrayList<SchedulerEntity>();
		
		schedulerRepository.findAll().forEach(cardList::add);
		cardList.forEach(card -> wipCards.add(SchedulerMapper.fromSchedulerEntityToPublicCardDto(card)));
		return wipCards;
	}
	
	public PublicCardDTO getWipCardById(Integer id) {
		SchedulerEntity existingCard = schedulerRepository.findWipById(id);
		PublicCardDTO card = SchedulerMapper.fromSchedulerEntityToPublicCardDto(existingCard);
		return card;
	}

	public Integer generateProps(PublicCardDTO card) {
		Optional<SchedulerEntity> existingCard = schedulerRepository.findById(card.getId());
		SchedulerEntity wipCard;
		
		if (existingCard.isPresent()) {
			wipCard = existingCard.get();
			// Check if the Image URL has changed => If so we have to generate again the properties
			if (wipCard.getImageUrl() != card.getImgUrl()) {
				wipCard.setIsPropOk(false);
				askForProperties(card.getImgUrl(), card.getId());
			}
		}
		else {
			wipCard = new SchedulerEntity();
			wipCard.setIsPropOk(false);
		}
		
		// Update or create the card
		
		//TODO: Peut être les initialiser à null si on part du principe qu'à la création de cartes il n'y a pas encore de propriétés
		//schEntity.setHp(card.getHp());
		//schEntity.setAttack(card.getAttack());
		//schEntity.setDefence(card.getDefence());
		//schEntity.setEnergy(card.getEnergy());
		
		wipCard.setAffinity(card.getAffinity());
		wipCard.setDescription(card.getDescription());
		wipCard.setFamily(card.getFamily());
		wipCard.setImageUrl(card.getImgUrl());
		wipCard.setName(card.getName());
		wipCard.setPrice(card.getPrice());
		wipCard.setSmallImageUrl(card.getImgUrl());
		wipCard.setUserId(card.getUserId());
		
		SchedulerEntity cardGenerated = schedulerRepository.save(wipCard);
		if (isCardCompeted(cardGenerated.getId())) {
			postCard(SchedulerMapper.fromSchedulerEntityToPublicCardDto(cardGenerated));
		}
		
		return cardGenerated.getId();
	}

	public void receiveProperties(CardProperties properties) {
		SchedulerEntity card = schedulerRepository.findWipById(properties.getId());
		card.setHp(properties.getHp());
		card.setAttack(properties.getAttack());
		card.setDefence(properties.getDefence());
		card.setEnergy(properties.getEnergy());
		card.setIsPropOk(false);
		schedulerRepository.save(card);
		
		if (isCardCompeted(properties.getId())) {
			postCard(SchedulerMapper.fromSchedulerEntityToPublicCardDto(card));
		}
	}

	public Integer updateWIP(PublicCardDTO card) {
		// TODO To implement
		SchedulerEntity wipCard = schedulerRepository.findWipById(card.getId());
		wipCard.setAffinity(card.getAffinity());
		wipCard.setDescription(card.getDescription());
		wipCard.setFamily(card.getFamily());
		wipCard.setImageUrl(card.getImgUrl());
		wipCard.setName(card.getName());
		wipCard.setPrice(card.getPrice());
		wipCard.setSmallImageUrl(card.getImgUrl());
		wipCard.setUserId(card.getUserId());
		SchedulerEntity cardGenerated = schedulerRepository.save(wipCard);
		return cardGenerated.getId();
	}
	
	//TODO: Envoyer une requête à raph pour générer les propriétés:
	public String askForProperties (String imgUrl, Integer id) {
		//TODO: Mettre la bonne URL quand je la connaîtrai
		String url = "";
		
		String response = webClientBuilder.baseUrl("{ \"url\": " + url + ", \"id\":" + id.toString() + "}")
				.build()
				.post()
				.uri("/") // TODO: Mettre la bonne URL quand je la connaitrai
				.bodyValue(imgUrl)
				.retrieve()
		        .bodyToMono(String.class)
		        .block();
		
		return response;
	}
	
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
	
	public boolean isCardCompeted(Integer id) {
		Optional<SchedulerEntity> optionnalWipCard = schedulerRepository.findById(id);
		
		if (optionnalWipCard.isEmpty()) {
			return false;
		}
		
		SchedulerEntity wipCard = optionnalWipCard.get();
		
		return wipCard.getName() != null && !wipCard.getName().isEmpty() &&
			   wipCard.getDescription() != null && !wipCard.getDescription().isEmpty() &&
			   wipCard.getImageUrl() != null && !wipCard.getImageUrl().isEmpty() &&
			   wipCard.getSmallImageUrl() != null && !wipCard.getSmallImageUrl().isEmpty() &&
			   wipCard.getAffinity() != null && !wipCard.getAffinity().isEmpty() &&
			   wipCard.getFamily() != null && !wipCard.getFamily().isEmpty() &&
			   wipCard.getIsPropOk();
			   
	}

}
