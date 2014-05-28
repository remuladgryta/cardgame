package com.remuladgryta.cardgame.entity;

import java.util.HashMap;

import com.remuladgryta.cardgame.GameEngine;
import com.remuladgryta.hex.CubeCoord;

public class Entity {
	HashMap<String,EntityComponent> components = new HashMap<String,EntityComponent>();
	private CubeCoord location;
	GameEngine engine;
	public Entity(GameEngine engine){
		this.engine = engine;
	}
	
	public void setLocation(CubeCoord loc){
		location = loc;
	}
	
	public CubeCoord getLocation(){
		return location;
	}
	
	public EntityComponent getComponent(String name){
		return components.get(name);
	}
	
	public Entity addComponent(EntityComponent component){
		components.put(component.getName(), component);
		component.setEntity(this);
		return this;
	}
	
	public boolean hasComponent(String name){
		return components.containsKey(name);
	}
}
