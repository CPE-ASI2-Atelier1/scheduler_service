package com.cpe.asi2.scheduler_service.schedulerController;

public class CardProperties {
	private float energy;
    private float hp;
    private float defence;
    private float attack;
    
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
   
}
