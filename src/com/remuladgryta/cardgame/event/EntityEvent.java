package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.entity.Entity;

public class EntityEvent extends AbstractEventImpl {
	protected Entity entity;
	
	public EntityEvent(Entity e){
		entity = e;
	}
	
	public Entity getEntity(){
		return entity;
	}

}
