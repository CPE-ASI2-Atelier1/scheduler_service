package com.cpe.asi2.scheduler_service.schedulerEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="Scheduler")
public class SchedulerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="energy")
	private float energy;
	
	@Column(name="hp")
	private float hp;
	
	@Column(name="defence")
	private float defence;
	
	@Column(name="attack")
	private float attack;
	
	@Column(name="price")
	private float price;
	
	@Column(name="name")
	private String name;
	
	@Column(name="family")
	private String family;
	
	@Column(name="affinity")
	private String affinity;

	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="small_image_url")
	private String smallImageUrl;
	
	@Column(name="description")
	private String description;
	
	@Column(name="is_prop_ok")
	private Boolean isPropOk;
	
	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public float getEnergy() {
		return energy;
	}

	public float getHp() {
		return hp;
	}

	public float getDefence() {
		return defence;
	}

	public float getAttack() {
		return attack;
	}

	public float getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public String getFamily() {
		return family;
	}

	public String getAffinity() {
		return affinity;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getIsPropOk() {
		return isPropOk;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public void setDefence(float defence) {
		this.defence = defence;
	}

	public void setAttack(float attack) {
		this.attack = attack;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public void setAffinity(String affinity) {
		this.affinity = affinity;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIsPropOk(Boolean complete) {
		this.isPropOk = complete;
	}
}