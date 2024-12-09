package com.cpe.asi2.scheduler_service.schedulerRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.cpe.asi2.scheduler_service.schedulerController.CardProperties;
import com.cpe.asi2.scheduler_service.schedulerEntity.SchedulerEntity;

import jakarta.transaction.Transactional;

@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerEntity, Integer> {

    // Met à jour l'image pour un ID spécifique
    @Modifying
    @Transactional
    @Query(value = "UPDATE Scheduler SET image_url = :image_url WHERE id = :id", nativeQuery = true)
    void updateImage(@Param("image_url") String imageUrl, @Param("id") Integer id);

    // Met à jour la description pour un ID spécifique
    @Modifying
    @Transactional
    @Query(value = "UPDATE Scheduler SET description = :description WHERE id = :id", nativeQuery = true)
    void updateDescription(@Param("description") String description, @Param("id") Integer id);

    // Met à jour les propriétés pour un ID spécifique
    @Modifying
    @Transactional
    @Query(value = "UPDATE Scheduler SET properties = :properties WHERE id = :id", nativeQuery = true)
    void updateProperties(@Param("properties") CardProperties properties, @Param("id") Integer id);

    // Vérifie si une carte est complétée
    @Query(value = "SELECT complete FROM Scheduler WHERE id = :id", nativeQuery = true)
    Boolean verifyIfCompleted(@Param("id") Integer id);

    // Récupère la description d'une carte avec un ID donné
	String getDescription(Integer id);

	// Récupère l'URL de l'image d'une carte avec un ID donné
	String getImageUrl(Integer id);

	// Récupère l'id du user d'une carte avec un ID donné
	Integer getUserId(Integer id);

	String getName(Integer id);

	String getFamily(Integer id);

	String getAffinity(Integer id);

	String getSmallImageUrl(Integer id);
}
