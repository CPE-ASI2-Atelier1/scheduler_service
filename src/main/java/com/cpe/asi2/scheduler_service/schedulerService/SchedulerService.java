package com.cpe.asi2.scheduler_service.schedulerService;

import org.springframework.stereotype.Service;

import com.cpe.asi2.scheduler_service.schedulerRepository.SchedulerRepository;

@Service
public class SchedulerService {
	
	private final SchedulerRepository schedulerRepository;
	
	public SchedulerService (SchedulerRepository schedulerRepository) {
		this.schedulerRepository = schedulerRepository;
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
		return "Ok";
	}

	public void deleteCard(Integer id) {
		schedulerRepository.deleteById(id);
		System.out.println("The DB deletion request has been sent for the card with id" + id);
	}

}
