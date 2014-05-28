package com.remuladgryta.cardgame.entity;

import com.remuladgryta.cardgame.event.EntityDeadEvent;

public class ComponentHealth extends EntityComponent{
	private int maxHealth, curHealth;
	
	public ComponentHealth setHealth(int curHealth) {
		this.curHealth = curHealth;
		return this;
	}
	public ComponentHealth setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		return this;
	}
	public void damage(int amount){
		curHealth-=amount;
		if(curHealth <= 0){
			onDead();
		}
	}
	public void heal(int amount){
		curHealth+=amount;
		if(curHealth>maxHealth){
			curHealth=maxHealth;
		}
	}
	
	private void onDead(){
		entity.engine.getEventDispatch().dispatch(new EntityDeadEvent(entity));
	}
	
	public int getHealth(){
		return curHealth;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	@Override
	public String getName() {
		return "health";
	}
}
