package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.entity.Entity;

public class EntityDeadEvent extends EntityEvent {

	public EntityDeadEvent(Entity e) {
		super(e);
	}

}
