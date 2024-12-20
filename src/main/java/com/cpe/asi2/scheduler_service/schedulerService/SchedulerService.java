package com.cpe.asi2.scheduler_service.schedulerService;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cpe.asi2.scheduler_service.common.tools.SchedulerMapper;
import com.cpe.asi2.scheduler_service.schedulerController.CardProperties;
import com.cpe.asi2.scheduler_service.schedulerEntity.SchedulerEntity;
import com.cpe.asi2.scheduler_service.schedulerRepository.SchedulerRepository;
import com.cpe.asi2.atelier1.dto.PublicCardDTO;

@Service
public class SchedulerService {

	@Value("${property.service.url}")
	private String propertyServiceUrl;
	
	private final SchedulerRepository schedulerRepository;
	private final WebClient.Builder webClientBuilder;
	
	public SchedulerService (SchedulerRepository schedulerRepository, WebClient.Builder webClientBuilder, SchedulerMapper schedulerMapper) {
		this.schedulerRepository = schedulerRepository;
		this.webClientBuilder = webClientBuilder;
	}

	public void deleteCard(Integer id) {
		schedulerRepository.deleteById(id);
		System.out.println("The DB deletion request has been sent for the card with id" + id);
	}

	public List<PublicCardDTO> getWIPCards() {
		List<PublicCardDTO> wipCards = new ArrayList<>();

        List<SchedulerEntity> cardList = new ArrayList<>(schedulerRepository.findAll());
		cardList.forEach(card -> wipCards.add(SchedulerMapper.fromSchedulerEntityToPublicCardDto(card)));
		return wipCards;
	}
	
	public List<PublicCardDTO> getWIPCardsByUserId(Integer id) {
		List<PublicCardDTO> wipCards = new ArrayList<>();

        List<SchedulerEntity> cardList = new ArrayList<>(schedulerRepository.findByUserId(id));
		cardList.forEach(card -> wipCards.add(SchedulerMapper.fromSchedulerEntityToPublicCardDto(card)));
		return wipCards;
	}
	
	public PublicCardDTO getWipCardById(Integer id) {
		SchedulerEntity existingCard = schedulerRepository.findWipById(id);
		if (existingCard == null) {
			return null;
		}
        return SchedulerMapper.fromSchedulerEntityToPublicCardDto(existingCard);
	}

	public Integer generateProps(PublicCardDTO card) {
		SchedulerEntity wipCard;
		String image = card.getImgUrl();
		Integer id = card.getId();
		boolean redoProps;

		// On a reçu un Id de carte : on la cherche en DB
		if (id != null && id != 0) {
			Optional<SchedulerEntity> entity = schedulerRepository.findById(id);
			// Erreur : on essaye de toucher une carte qui n'existe plus / pas
			if (entity.isEmpty()){
				return -1;
			}
			wipCard = entity.get();
			String wipImage = wipCard.getImageUrl();
			redoProps = wipImage == null || !wipImage.equals(image);
		}
		// On a pas reçu d'ID : on crée la carte
		else {
			wipCard = new SchedulerEntity();
			wipCard.setIsPropOk(false);
			wipCard = schedulerRepository.save(wipCard);
			id = wipCard.getId();
			redoProps = true;
		}

		if (redoProps){
			wipCard.setIsPropOk(false);
			askForProperties(image, id);
		}

		// Update if necessary
		wipCard.setAffinity(card.getAffinity());
		wipCard.setDescription(card.getDescription());
		wipCard.setFamily(card.getFamily());
		wipCard.setImageUrl(card.getImgUrl());
		wipCard.setName(card.getName());
		wipCard.setPrice(card.getPrice());
		wipCard.setSmallImageUrl(card.getImgUrl());
		wipCard.setUserId(card.getUserId());
		schedulerRepository.save(wipCard);

		return wipCard.getId();
	}

	public void receiveProperties(CardProperties properties) {
		SchedulerEntity card = schedulerRepository.findWipById(properties.getCardid());
		card.setHp(properties.getHp());
		card.setAttack(properties.getAttack());
		card.setDefence(properties.getDefence());
		card.setEnergy(properties.getEnergy());
		card.setIsPropOk(false);
		schedulerRepository.save(card);
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
		
//		if (isCardCompeted(cardGenerated.getId())) {
//			postCard(SchedulerMapper.fromSchedulerEntityToPublicCardDto(cardGenerated));
//			deleteCard(cardGenerated.getId());
//		}
		return cardGenerated.getId();
	}
	
	public String askForProperties (String imgUrl, Integer id) {
		//String url = "http://localhost:8082"; // TODO : get from env file

		// Construire un objet pour le corps de la requête
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("url", imgUrl);
		requestBody.put("cardid", id);

		String response = webClientBuilder.baseUrl(this.propertyServiceUrl)
				.build()
				.post()
				.uri("/properties")
				.bodyValue(requestBody) // Passer l'objet dans le corps de la requête
				.retrieve()
				.bodyToMono(String.class)
				.block(); // Bloquer jusqu'à ce qu'une réponse soit reçue

		return response;
	}
	
//	public String postCard(PublicCardDTO requestBody) {
//
//		String url = celle du monolith
//
//		String response = webClientBuilder.baseUrl(url)
//				.build()
//				.post()
//				.uri("/createCard") // De tête, à vérifier
//				.bodyValue(requestBody)
//				.retrieve()
//		        .bodyToMono(String.class)
//		        .block();
//
//		return response;
//	}
	
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
