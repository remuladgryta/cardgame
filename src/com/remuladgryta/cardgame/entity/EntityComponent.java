package com.remuladgryta.cardgame.entity;

public abstract class EntityComponent {
	protected Entity entity;
	public abstract String getName();
	
	/**
	 * Only for use by Entity when attaching
	 * @param e
	 */
	void setEntity(Entity e){
		this.entity = e;
	}
}
